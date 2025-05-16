package com.example.BE_DATN.controller;

import com.example.BE_DATN.dto.request.ServiceOrderRequest;
import com.example.BE_DATN.dto.request.ServiceOrderUpdate;
import com.example.BE_DATN.dto.respone.ApiRespone;
import com.example.BE_DATN.dto.respone.ServiceOrderDtoRespone;
import com.example.BE_DATN.dto.respone.ServiceOrderRespone;
import com.example.BE_DATN.entity.ServiceOrder;
import com.example.BE_DATN.service.ServiceOrderService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/serviceOrder")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceOrderController {
    @Autowired
    ServiceOrderService serviceOrderService;
    @PostMapping
    public ApiRespone<ServiceOrder> createServiceOrder(@ModelAttribute ServiceOrderRequest request){
        return ApiRespone.<ServiceOrder>builder()
                .code(200)
                .message("Success")
                .result(serviceOrderService.createServiceOrder(request))
                .build();
    }

    @GetMapping("/{idBooking}")
    public ApiRespone<List<ServiceOrderRespone>> getServiceByIdBooking(@PathVariable("idBooking") Long idBooking){
        return ApiRespone.<List<ServiceOrderRespone>>builder()
                .code(200)
                .message("Success")
                .result(serviceOrderService.findServicesByIdBooking(idBooking))
                .build();
    }

    @GetMapping("/{idBooking}/serviceOrder")
    public ApiRespone<List<ServiceOrder>> getServiceOrderByIdBooking(@PathVariable("idBooking") Long idBooking){
        return ApiRespone.<List<ServiceOrder>>builder()
                .code(200)
                .message("Success")
                .result(serviceOrderService.findByIdBooking(idBooking))
                .build();
    }
    @GetMapping("/{idType}/{idStadium}")
    public ApiRespone<List<ServiceOrderDtoRespone>> getServices(@PathVariable("idType") Long idType,
                                                                          @PathVariable("idStadium") Long idStadium){
        return ApiRespone.<List<ServiceOrderDtoRespone>>builder()
                .code(200)
                .message("Success")
                .result(serviceOrderService.getServiceOrderByIdTypeAndIdStadium(idType,idStadium))
                .build();
    }
    @DeleteMapping("/{idServiceOrder}")
    public  ApiRespone<Void> deleteServiceOrder(@PathVariable("idServiceOrder") Long idServiceOrder){
        serviceOrderService.deleteServiceOrder(idServiceOrder);
        return ApiRespone.<Void>builder()
                .code(200)
                .message("Success")
                .result(null)
                .build();
    }

    @PutMapping("/{idServiceOrder}")
    ApiRespone<ServiceOrder> updateServiceOrder(@PathVariable("idServiceOrder") Long idServiceOrder,
                                                @ModelAttribute ServiceOrderUpdate serviceOrderUpdate){
        return ApiRespone.<ServiceOrder>builder()
                .code(200)
                .message("Success")
                .result(serviceOrderService.updateQuantityServiceOrder(idServiceOrder, serviceOrderUpdate))
                .build();
    }

    @GetMapping("/{idType}/{idStadium}/{idUser}")
    ApiRespone<List<ServiceOrderDtoRespone>> getServiceOrderByIdUser(@PathVariable("idType") Long idType,
                                                                  @PathVariable("idStadium") Long idStadium,
                                                                  @PathVariable("idUser") Long idUser){
        return ApiRespone.<List<ServiceOrderDtoRespone>>builder()
                .code(200)
                .message("Success")
                .result(serviceOrderService.getServiceOrderByIdUser(idType,idStadium,idUser))
                .build();
    }
}
