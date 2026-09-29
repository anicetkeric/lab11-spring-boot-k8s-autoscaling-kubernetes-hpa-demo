package com.bootlabs.hpademo.controller;

import com.bootlabs.hpademo.dto.LoadResponse;
import com.bootlabs.hpademo.service.PrimeWorkloadService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoadController.class)
class LoadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PrimeWorkloadService workloadService;

    @Test
    void clampsUpperBoundBelowConfiguredMinimum() throws Exception {
        given(workloadService.countPrimesBelow(eq(1_000)))
                .willReturn(new LoadResponse("pod-a", 1_000, 168, 3));

        mockMvc.perform(get("/api/cpu/burn").param("upperBound", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.upperBound").value(1_000))
                .andExpect(jsonPath("$.pod").value("pod-a"));
    }

    @Test
    void usesDefaultUpperBoundWhenParamMissing() throws Exception {
        given(workloadService.countPrimesBelow(eq(200_000)))
                .willReturn(new LoadResponse("pod-a", 200_000, 17_984, 42));

        mockMvc.perform(get("/api/cpu/burn"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.primesFound").value(17_984));
    }

    @Test
    void returnsPodNameFromStatusEndpoint() throws Exception {
        given(workloadService.podName()).willReturn("hpa-demo-7d9f6c8b4d-x2z9k");

        mockMvc.perform(get("/api/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pod").value("hpa-demo-7d9f6c8b4d-x2z9k"));
    }
}
