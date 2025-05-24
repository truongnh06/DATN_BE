package com.example.BE_DATN.controller;

import com.example.BE_DATN.dto.request.MatchingRequest;
import com.example.BE_DATN.dto.respone.ApiRespone;
import com.example.BE_DATN.dto.respone.MatchingRespone;
import com.example.BE_DATN.entity.Matching;
import com.example.BE_DATN.service.MatchingService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matching")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MatchingController {
    @Autowired
    MatchingService matchingService;

    @PostMapping
    public ApiRespone<Matching> createMatching(@ModelAttribute MatchingRequest request){
        return ApiRespone.<Matching>builder()
                .code(200)
                .message("Success")
                .result(matchingService.createMatching(request))
                .build();
    }

    @GetMapping("/{idStadium}")
    public ApiRespone<List<MatchingRespone>> getMatchingByIdStadium(@PathVariable("idStadium") Long idStadium){
        return ApiRespone.<List<MatchingRespone>>builder()
                .code(200)
                .message("Success")
                .result(matchingService.getMatchingByIdStadium(idStadium))
                .build();
    }

    @PutMapping("/{idMatching}")
    public ApiRespone<Matching> AcceptMatching(@PathVariable("idMatching") Long idMatching,
                                                @RequestParam Long idUserB){
        return ApiRespone.<Matching>builder()
                .code(200)
                .message("Success")
                .result(matchingService.AcceptMatching(idMatching, idUserB))
                .build();
    }
    @GetMapping("/{idStadium}/match")
    public ApiRespone<List<MatchingRespone>> getMatchByIdStadium(@PathVariable("idStadium") Long idStadium){
        return ApiRespone.<List<MatchingRespone>>builder()
                .code(200)
                .message("Success")
                .result(matchingService.getMatchByIdStadium(idStadium))
                .build();
    }

    //dành cho user , đính thân người đặt đối hủy
    @PutMapping("/{idMatching}/enable/user")
    public ApiRespone<Matching> unableMatching(@PathVariable("idMatching") Long idMatching,
                                               @RequestParam Long idUser){
        return ApiRespone.<Matching>builder()
                .code(200)
                .message("Success")
                .result(matchingService.unableMatching(idMatching,idUser))
                .build();
    }

    @PutMapping("/{idMatching}/cancelMatching")
    public ApiRespone<Matching> cancelMatching(@PathVariable("idMatching") Long idMatching,
                                               @RequestParam Long idUser, @RequestParam String reason){
        return ApiRespone.<Matching>builder()
                .code(200)
                .message("Success")
                .result(matchingService.CancelMatching(idMatching,idUser,reason))
                .build();
    }

    @GetMapping("/{idStadium}/{idUser}/match")
    public ApiRespone<List<MatchingRespone>> getMatchByIdStadiumAndIdUser(@PathVariable("idStadium") Long idStadium,
                                                                          @PathVariable("idUser") Long idUser){
        return ApiRespone.<List<MatchingRespone>>builder()
                .code(200)
                .message("Success")
                .result(matchingService.getMatchByIdStadiumAndIdUser(idStadium,idUser))
                .build();
    }

}
