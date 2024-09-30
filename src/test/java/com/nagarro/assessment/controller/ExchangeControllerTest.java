package com.nagarro.assessment.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarro.assessment.constants.CommonConstants;
import com.nagarro.assessment.dto.BillRequestDTO;
import com.nagarro.assessment.dto.BillResponseDTO;
import com.nagarro.assessment.model.enums.ItemType;
import com.nagarro.assessment.model.enums.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class ExchangeControllerTest {


    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;


    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void testCalculateEmployee3yrs() throws Exception {
        // Given
        BillRequestDTO billRequest = new BillRequestDTO();
        billRequest.setCustomerTenure(3);
        billRequest.setOriginalCurrency("USD");
        billRequest.setTargetCurrency("EUR");
        billRequest.setUserType(UserType.EMPLOYEE);
        billRequest.setItems(List.of(
                new BillRequestDTO.Item("Item1", ItemType.ELECTRONICS, 150.0),
                new BillRequestDTO.Item("Item2", ItemType.GROCERY, 50.0)
        ));

        BillResponseDTO billResponse = new BillResponseDTO(134.355, "EUR");

        String jsonBody = objectMapper.writeValueAsString(billRequest);

        ArgumentCaptor<BillRequestDTO> billRequestCaptor = ArgumentCaptor.forClass(BillRequestDTO.class);

        mockMvc.perform(post(CommonConstants.CALCULATE_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
               /* .andExpect(jsonPath("$.amount").value(billResponse.getAmount()))*/
                .andExpect(jsonPath("$.currency").value(billResponse.getCurrency()));

    }
    @Test
    void testCalculate1year() throws Exception {
        // Given
        BillRequestDTO billRequest = new BillRequestDTO();
        billRequest.setCustomerTenure(1);
        billRequest.setOriginalCurrency("USD");
        billRequest.setTargetCurrency("EUR");
        billRequest.setUserType(UserType.NA);
        billRequest.setItems(List.of(
                new BillRequestDTO.Item("Item1", ItemType.ELECTRONICS, 150.0),
                new BillRequestDTO.Item("Item2", ItemType.GROCERY, 50.0)
        ));

        BillResponseDTO billResponse = new BillResponseDTO(170.183, "EUR");

        String jsonBody = objectMapper.writeValueAsString(billRequest);


        mockMvc.perform(post(CommonConstants.CALCULATE_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
             /*   .andExpect(jsonPath("$.amount").value(billResponse.getAmount()))*/
                .andExpect(jsonPath("$.currency").value(billResponse.getCurrency()));

    }

    @Test
    void testCalculateEmployee() throws Exception {
        // Given
        BillRequestDTO billRequest = new BillRequestDTO();
        billRequest.setCustomerTenure(0);
        billRequest.setOriginalCurrency("USD");
        billRequest.setTargetCurrency("EUR");
        billRequest.setUserType(UserType.EMPLOYEE);
        billRequest.setItems(List.of(
                new BillRequestDTO.Item("Item1", ItemType.ELECTRONICS, 150.0),
                new BillRequestDTO.Item("Item2", ItemType.GROCERY, 0.0)
        ));

        BillResponseDTO billResponse = new BillResponseDTO(89.57, "EUR");


        mockMvc.perform(post(CommonConstants.CALCULATE_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(billRequest))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
              /*  .andExpect(jsonPath("$.amount").value(billResponse.getAmount()))*/
                .andExpect(jsonPath("$.currency").value(billResponse.getCurrency()));
    }

    @Test
    void testCalculate_BadRequest_wrongOriginalCurr() throws Exception {
        BillRequestDTO invalidBillRequest = new BillRequestDTO();
        invalidBillRequest.setCustomerTenure(0);
        invalidBillRequest.setOriginalCurrency("AAA");
        invalidBillRequest.setTargetCurrency("EUR");
        invalidBillRequest.setUserType(UserType.EMPLOYEE);
        invalidBillRequest.setItems(List.of(
                new BillRequestDTO.Item("Item1", ItemType.ELECTRONICS, 150.0),
                new BillRequestDTO.Item("Item2", ItemType.GROCERY, 0.0)
        ));

        MvcResult result = mockMvc.perform(post(CommonConstants.CALCULATE_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidBillRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").exists())
                .andReturn();
    }


    @Test
    void testCalculate_BadRequest_wrongTargetCurr() throws Exception {
        BillRequestDTO invalidBillRequest = new BillRequestDTO();
        invalidBillRequest.setCustomerTenure(0);
        invalidBillRequest.setOriginalCurrency("AAA");
        invalidBillRequest.setTargetCurrency("WWW");
        invalidBillRequest.setUserType(UserType.EMPLOYEE);
        invalidBillRequest.setItems(List.of(
                new BillRequestDTO.Item("Item1", ItemType.ELECTRONICS, 150.0),
                new BillRequestDTO.Item("Item2", ItemType.GROCERY, 0.0)
        ));

        MvcResult result = mockMvc.perform(post(CommonConstants.CALCULATE_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidBillRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").exists())
                .andReturn();
    }
}
