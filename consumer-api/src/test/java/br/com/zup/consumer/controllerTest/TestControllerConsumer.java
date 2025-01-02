package br.com.zup.consumer.controllerTest;

import br.com.zup.consumer.controllers.ConsumerController;
import br.com.zup.consumer.controllers.dtos.ConsumerRequestDTO;
import br.com.zup.consumer.controllers.dtos.ConsumerResponseDTO;
import br.com.zup.consumer.services.ConsumerService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class TestControllerConsumer {

    @Mock
    private ConsumerService consumerService;

    @InjectMocks
    private ConsumerController consumerController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(consumerController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateConsumer() throws Exception {
        ConsumerRequestDTO requestDTO = new ConsumerRequestDTO("Nimus",
                "30",
                "arselina8189@uorak.com");
        ConsumerResponseDTO responseDTO = new ConsumerResponseDTO("2f246ea2-e010-4ef3-9902-ffa42446ad76",
                "Nimus",
                "30",
                "arselina8189@uorak.com");

        when(consumerService.createConsumer(any())).thenReturn(responseDTO);

        mockMvc.perform(post("/consumers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("2f246ea2-e010-4ef3-9902-ffa42446ad76"))
                .andExpect(jsonPath("$.name").value("Nimus"));

        verify(consumerService, times(1)).createConsumer(any());
    }

    @Test
    public void testGetAllConsumers() throws Exception {
        List<ConsumerResponseDTO> responseDTOs = Arrays.asList(
                new ConsumerResponseDTO("2f246ea2-e010-4ef3-9902-ffa42446ad76",
                        "Nimus",
                        "30",
                        "arselina8189@uorak.com"),
                new ConsumerResponseDTO("2a0ea765-752e-472c-a873-d14c5173f190",
                        "Sounborn",
                        "25",
                        "abdelwahad4756@uorak.com")
        );

        when(consumerService.getAllConsumers()).thenReturn(responseDTOs);

        mockMvc.perform(get("/consumers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value("2f246ea2-e010-4ef3-9902-ffa42446ad76"))
                .andExpect(jsonPath("$[1].id").value("2a0ea765-752e-472c-a873-d14c5173f190"));

        verify(consumerService, times(1)).getAllConsumers();
    }

    @Test
    public void testGetConsumerById() throws Exception {
        ConsumerResponseDTO responseDTO = new ConsumerResponseDTO("2f246ea2-e010-4ef3-9902-ffa42446ad76",
                "Nimus",
                "30",
                "arselina8189@uorak.com");

        when(consumerService.getConsumerById("2f246ea2-e010-4ef3-9902-ffa42446ad76")).thenReturn(responseDTO);

        mockMvc.perform(get("/consumers/2f246ea2-e010-4ef3-9902-ffa42446ad76"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("2f246ea2-e010-4ef3-9902-ffa42446ad76"))
                .andExpect(jsonPath("$.name").value("Nimus"));

        verify(consumerService, times(1)).getConsumerById("2f246ea2-e010-4ef3-9902-ffa42446ad76");
    }

    @Test
    public void testUpdateConsumer() throws Exception {
        ConsumerRequestDTO requestDTO = new ConsumerRequestDTO("Nimus",
                "35",
                "arselina8189@uorak.com");
        ConsumerResponseDTO responseDTO = new ConsumerResponseDTO("2f246ea2-e010-4ef3-9902-ffa42446ad76",
                "Nimus",
                "36",
                "arselina8189@uorak.com");

        when(consumerService.updateConsumer(eq("2f246ea2-e010-4ef3-9902-ffa42446ad76"), any())).thenReturn(responseDTO);

        mockMvc.perform(put("/consumers/2f246ea2-e010-4ef3-9902-ffa42446ad76")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("2f246ea2-e010-4ef3-9902-ffa42446ad76"))
                .andExpect(jsonPath("$.name").value("Nimus"));

        verify(consumerService, times(1)).updateConsumer(eq("2f246ea2-e010-4ef3-9902-ffa42446ad76"), any());
    }

    @Test
    public void testDeleteConsumer() throws Exception {
        doNothing().when(consumerService).deleteConsumer("2f246ea2-e010-4ef3-9902-ffa42446ad76");

        mockMvc.perform(delete("/consumers/2f246ea2-e010-4ef3-9902-ffa42446ad76"))
                .andExpect(status().isNoContent());

        verify(consumerService, times(1)).deleteConsumer("2f246ea2-e010-4ef3-9902-ffa42446ad76");
    }
}
