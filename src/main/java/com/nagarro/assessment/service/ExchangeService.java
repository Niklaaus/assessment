package com.nagarro.assessment.service;

import com.nagarro.assessment.dto.BillRequestDTO;
import com.nagarro.assessment.dto.BillResponseDTO;

public interface ExchangeService {

    public BillResponseDTO calculateFinalBill(BillRequestDTO billRequestDTO);
}
