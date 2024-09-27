package com.nagarro.assessment.controller;

import com.nagarro.assessment.dto.BillRequestDTO;
import com.nagarro.assessment.dto.BillResponseDTO;
import com.nagarro.assessment.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ExchangeController {

    @Autowired
    ExchangeService service;

    @PostMapping("/calculate")
    public BillResponseDTO calculate(@RequestBody BillRequestDTO bill){

        //input validation pending

        return service.calculateFinalBill(bill);
    }

}
