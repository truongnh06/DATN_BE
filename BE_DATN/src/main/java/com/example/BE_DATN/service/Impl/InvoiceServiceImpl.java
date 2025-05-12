package com.example.BE_DATN.service.Impl;

import com.example.BE_DATN.dto.respone.InvoiceDayRespone;
import com.example.BE_DATN.dto.respone.InvoiceRespone;
import com.example.BE_DATN.dto.respone.ListInvoiceRespone;
import com.example.BE_DATN.entity.Invoice;
import com.example.BE_DATN.exception.AppException;
import com.example.BE_DATN.exception.ErrorCode;
import com.example.BE_DATN.repository.BookingRepository;
import com.example.BE_DATN.repository.InvoiceRepository;
import com.example.BE_DATN.repository.UserRepository;
import com.example.BE_DATN.service.InvoiceService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public Invoice createInvoice(LocalDate day, Long idStadium, Long idUser, Long idBooking, double totalPrice) {
        if(!bookingRepository.existsBookingByIdBooking(idBooking)){
            throw new AppException(ErrorCode.NOT_FOUND_BOOKING);
        }
        if(invoiceRepository.existsBookingByIdBooking(idBooking)){
            throw new AppException(ErrorCode.BOOKING_EXISTS);
        }
        if(!userRepository.existsByIdUser(idUser)){
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        if(day.isAfter(LocalDate.now())){
            throw new AppException(ErrorCode.BOOKING_DAY_INVALID);
        }
        Invoice invoice = Invoice.builder()
                .day(day)
                .idStadium(idStadium)
                .idUser(idUser)
                .idBooking(idBooking)
                .totalPrice(totalPrice)
                .build();
        return  invoiceRepository.save(invoice);
    }

    @Override
    public List<InvoiceRespone> getInvoiceByMonth(Long idStadium) {
        List<Object[]> list = invoiceRepository.getListInvoiceByMonth(idStadium);
        List<InvoiceRespone> InvoiceRespone = new ArrayList<>();
        for(Object[] row : list){
            int month = ((Number) row[0]).intValue();
            int year = ((Number) row[1]).intValue();
            int total = ((Number) row[2]).intValue();
            double revenue = ((Number) row[3]).doubleValue();

            InvoiceRespone.add(new InvoiceRespone(month, year, total, revenue));
        }
        return InvoiceRespone;
    }

    @Override
    public InvoiceDayRespone getInvoiceDay(LocalDate day, Long idStadium) {
        return invoiceRepository.getInvoiceDay(day, idStadium)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
    }

    @Override
    public List<ListInvoiceRespone> getInvoiceDayByIdStadium(Long idStadium, LocalDate day) {
        return invoiceRepository.getListInvoiceDayByIdStadium(idStadium, day);
    }
}
