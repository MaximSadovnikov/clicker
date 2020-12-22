package ru.maxim.clicker.web;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.maxim.clicker.domain.Count;
import ru.maxim.clicker.domain.CounterService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
class ClickerRestTest {

    @MockBean
    CounterService service;

    @Autowired
    private MockMvc restCounterMockMvc;


    @Test
    void getCountSuccess() throws Exception {
        when(service.getNumOfClicks()).thenReturn(new Count(1));
        restCounterMockMvc.perform(get("/count")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.value").value(1));
    }

    @Test
    void getClickSuccess() throws Exception {
        when(service.getNumOfClicks()).thenReturn(new Count(1));
        restCounterMockMvc.perform(get("/click")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.value").value(1));
    }

}