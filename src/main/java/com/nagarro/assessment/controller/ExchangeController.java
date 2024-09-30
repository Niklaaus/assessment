package com.nagarro.assessment.controller;

import com.nagarro.assessment.constants.CommonConstants;
import com.nagarro.assessment.dto.BillRequestDTO;
import com.nagarro.assessment.dto.BillResponseDTO;
import com.nagarro.assessment.model.service.ExchangeService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CommonConstants.API_BASE_URL)
@Validated
public class ExchangeController {

    @Autowired
    ExchangeService service;

    private static final Logger logger = LogManager.getLogger(ExchangeController.class);

    @PostMapping(CommonConstants.CALCULATE_API)
    public BillResponseDTO calculate(@Valid @RequestBody BillRequestDTO bill){
            logger.debug(bill);
            BillResponseDTO responseDto=service.calculateFinalBill(bill);
            logger.debug(responseDto);
            return responseDto;
    }

}
