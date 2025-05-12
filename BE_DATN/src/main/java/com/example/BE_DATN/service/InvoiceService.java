package com.example.BE_DATN.service;

import com.example.BE_DATN.dto.respone.InvoiceDayRespone;
import com.example.BE_DATN.dto.respone.InvoiceRespone;
import com.example.BE_DATN.dto.respone.ListInvoiceRespone;
import com.example.BE_DATN.entity.Invoice;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceService {
    public Invoice createInvoice(LocalDate day, Long idStadium,
                                 Long idUser, Long idBooking,
                                 double totalPrice);
    public List<InvoiceRespone> getInvoiceByMonth(Long idStadium);
    public InvoiceDayRespone getInvoiceDay(LocalDate day, Long idStadium);
    public List<ListInvoiceRespone> getInvoiceDayByIdStadium(Long idStadium, LocalDate day);
}
