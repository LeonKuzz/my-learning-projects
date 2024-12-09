package com.skillbox.fibonacci;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FibonacciControllerTest extends PostgresTestContainerInitializer {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("given zero index when getNumber then Exception")
    public void givenZeroIndex_whenGetNumber_thenException() throws Exception {
        mockMvc.perform(
                        get("/fibonacci/0"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("Index should be greater or equal to 1"));
    }

    @Test
    @DisplayName("given true index when getNumber then true number")
    public void givenTrueIndex_whenGetNumber_thenNumber () throws Exception {
        mockMvc.perform(
                        get("/fibonacci/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.index").value("1"))
                .andExpect(jsonPath("$.value").value("1"));
    }
}
