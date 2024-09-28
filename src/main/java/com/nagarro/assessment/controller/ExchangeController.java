package com.nagarro.assessment.controller;

import com.nagarro.assessment.dto.BillRequestDTO;
import com.nagarro.assessment.dto.BillResponseDTO;
import com.nagarro.assessment.service.ExchangeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Validated
public class ExchangeController {

    @Autowired
    ExchangeService service;

    @PostMapping("/calculate")
    public BillResponseDTO calculate(@Valid @RequestBody BillRequestDTO bill){

            return service.calculateFinalBill(bill);
    }

}
