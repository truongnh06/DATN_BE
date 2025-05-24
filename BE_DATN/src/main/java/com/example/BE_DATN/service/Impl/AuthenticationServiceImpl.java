package com.example.BE_DATN.service.Impl;

import com.example.BE_DATN.dto.respone.AuthenticationRespone;
import com.example.BE_DATN.entity.Role;
import com.example.BE_DATN.entity.Token;
import com.example.BE_DATN.entity.User;
import com.example.BE_DATN.enums.StatusChangePwd;
import com.example.BE_DATN.exception.AppException;
import com.example.BE_DATN.exception.ErrorCode;
import com.example.BE_DATN.exception.JwtAuthenticationException;
import com.example.BE_DATN.repository.RoleRepository;
import com.example.BE_DATN.repository.TokenRepository;
import com.example.BE_DATN.repository.UserRepository;
import com.example.BE_DATN.service.AuthenticationService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    TokenRepository tokenRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value(("${jwt.refreshable-duration}"))
    protected long REFRESHABLE_DURATION;

    @Override
    public String buildScope(User user) {
        return "ROLE_" + roleRepository.getNameRole(user.getIdRole());
    }

    @Override
    public String generateToken(User user) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getName())
                .claim("idUser", user.getIdUser())
                .issuer("Hatruong")
                .issueTime(new Date())
                .jwtID(UUID.randomUUID().toString().substring(0,8))
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()))
                .claim("scope",buildScope(user))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e){
            log.error("Error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public AuthenticationRespone LogIn(String name, String password) {
       User user = userRepository.GetUserLogin(name,password)
               .orElseThrow(() -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION));
        String role = roleRepository.getNameRole(user.getIdRole());
        return  AuthenticationRespone.builder()
                .valid(StatusChangePwd.TRUE.name())
                .token(generateToken(user))
                .idUser(user.getIdUser())
                .changePwd(user.getChangePassword())
                .role(role)
                .build();
    }

    @Override
    public SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);
        if(expiryTime == null || expiryTime.before(new Date())){
            throw new JwtAuthenticationException("Token expired");
        }
        if(tokenRepository.existsTokenById(signedJWT.getJWTClaimsSet().getJWTID())){
            throw new JwtAuthenticationException("Token revoked");
        }
        return signedJWT;
    }

    @Override
    public boolean introspect(String token) throws JOSEException, ParseException{
        boolean valid = true;
        try{
            verifyToken(token);
        } catch (AppException e){
            valid = false;
            e.printStackTrace();
        }
        return valid;
    }

    @Override
    public Token logout(String token) throws ParseException, JOSEException {
        try{
            var signToken = verifyRefreshToken(token);
            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();
            Token saveToken = Token.builder()
                    .idToken(jit)
                    .token(token)
                    .exp(expiryTime)
                    .build();
            return tokenRepository.save(saveToken);
        } catch (AppException e){
            throw new AppException(ErrorCode.BOOKING_DAY_INVALID);
        }
    }

    @Override
    public SignedJWT verifyRefreshToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        boolean verified = signedJWT.verify(verifier);
        if (!verified) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

        Instant issueInstant = signedJWT.getJWTClaimsSet().getIssueTime().toInstant();
        Instant refreshExpiry = issueInstant.plus(REFRESHABLE_DURATION, ChronoUnit.HOURS);
        if (refreshExpiry.isBefore(Instant.now())) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

        String jwtId = signedJWT.getJWTClaimsSet().getJWTID();
        if (tokenRepository.existsTokenById(jwtId)) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

        return signedJWT;
    }

    @Override
    public AuthenticationRespone refreshToken(String token) throws ParseException, JOSEException {
        var signedJWT = verifyRefreshToken(token);
        var jit = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        Token saveToke = Token.builder()
                .idToken(jit)
                .token(token)
                .exp(expiryTime)
                .build();
        tokenRepository.save(saveToke);
        String username = signedJWT.getJWTClaimsSet().getSubject();
        User user = userRepository.FindUserByName(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        var savaToken = generateToken(user);
        return AuthenticationRespone.builder()
                .valid("true")
                .token(savaToken)
                .build();
    }

    @Override
    public Long extractUserId(String token) throws JOSEException, ParseException{
        SignedJWT signedJWT = SignedJWT.parse(token);
        return (Long) signedJWT.getJWTClaimsSet().getClaim("idUser");
    }

    @Override
    public String extractRole(String token) throws ParseException{
        SignedJWT signedJWT = SignedJWT.parse(token);
        return (String) signedJWT.getJWTClaimsSet().getClaim("scope");
    }

}
