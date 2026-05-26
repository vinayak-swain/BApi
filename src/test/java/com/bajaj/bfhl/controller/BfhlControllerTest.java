package com.bajaj.bfhl.controller;

import com.bajaj.bfhl.dto.BfhlResponse;
import com.bajaj.bfhl.service.BfhlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BfhlController.class)
class BfhlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BfhlService bfhlService;

    @Test
    void testValidRequest() throws Exception {
        BfhlResponse mockResponse = BfhlResponse.builder()
                .isSuccess(true)
                .userId("vinayak_swain_18092004")
                .email("vinayakswain230934@acropolis.in")
                .rollNumber("0827AL231145")
                .oddNumbers(List.of("1"))
                .evenNumbers(List.of("334", "4"))
                .alphabets(List.of("A", "R"))
                .specialCharacters(List.of("$"))
                .sum("339")
                .concatString("Ra")
                .build();

        when(bfhlService.processData(any())).thenReturn(mockResponse);

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"data\":[\"a\",\"1\",\"334\",\"4\",\"R\",\"$\"]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.user_id").value("vinayak_swain_18092004"));
    }

    @Test
    void testMissingDataField() throws Exception {
        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testEmptyDataArray() throws Exception {
        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"data\":[]}"))
                .andExpect(status().isBadRequest());
    }
}
