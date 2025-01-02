package br.com.zup.gateway.controllerTest;

import br.com.zup.gateway.controllers.ConsumerAddressController;
import br.com.zup.gateway.controllers.dtos.AddressDTO;
import br.com.zup.gateway.controllers.dtos.ConsumerAddressRegisterDTO;
import br.com.zup.gateway.controllers.dtos.ConsumerAddressResponseDTO;
import br.com.zup.gateway.services.ConsumerAddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestControllerConsumerAddress {

    @Mock
    private ConsumerAddressService consumerAddressService;

    @InjectMocks
    private ConsumerAddressController consumerAddressController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister() {
        ConsumerAddressRegisterDTO registerDTO = new ConsumerAddressRegisterDTO();
        registerDTO.setName("Nimus");
        registerDTO.setAge("30");
        registerDTO.setEmail("abdelwahad4756@uorak.com");

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet("Rua Mizael Nogueira 195");
        addressDTO.setCity("Abadia dos Dourados");
        addressDTO.setZipCode("38540-970");
        addressDTO.setState("MG");
        registerDTO.setAddress(addressDTO);

        ConsumerAddressResponseDTO responseDTO = new ConsumerAddressResponseDTO();
        responseDTO.setConsumer(new ConsumerAddressResponseDTO.ConsumerDTO("123",
                "Nimus",
                "30",
                "abdelwahad4756@uorak.com"));
        responseDTO.setAddress(new ConsumerAddressResponseDTO.AddressDTO("456",
                "123",
                "Rua Mizael Nogueira 195",
                "Abadia dos Dourados",
                "MG",
                "38540-970"));

        when(consumerAddressService.registerConsumerAddress(registerDTO)).thenReturn(responseDTO);

        ResponseEntity<ConsumerAddressResponseDTO> response = consumerAddressController.register(registerDTO);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("123", response.getBody().getConsumer().getId());
        verify(consumerAddressService, times(1)).registerConsumerAddress(registerDTO);
    }

    @Test
    public void testGetConsumerAddress() {
        String consumerId = "123";

        ConsumerAddressResponseDTO responseDTO = new ConsumerAddressResponseDTO();
        responseDTO.setConsumer(new ConsumerAddressResponseDTO.ConsumerDTO("123",
                "Nimus",
                "30",
                "abdelwahad4756@uorak.com"));
        responseDTO.setAddress(new ConsumerAddressResponseDTO.AddressDTO("456",
                "123",
                "123 Main St",
                "Springfield",
                "IL",
                "62704"));

        when(consumerAddressService.getConsumerAddress(consumerId)).thenReturn(responseDTO);

        ResponseEntity<ConsumerAddressResponseDTO> response = consumerAddressController.getConsumerAddress(consumerId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("123", response.getBody().getConsumer().getId());
        verify(consumerAddressService, times(1)).getConsumerAddress(consumerId);
    }

    @Test
    public void testUpdateConsumerAddress() {
        String consumerId = "123";

        ConsumerAddressRegisterDTO registerDTO = new ConsumerAddressRegisterDTO();
        registerDTO.setName("John Doe Updated");
        registerDTO.setAge("35");
        registerDTO.setEmail("john.doe.updated@example.com");

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet("456 Updated St");
        addressDTO.setCity("Updated City");
        addressDTO.setState("CA");
        addressDTO.setZipCode("90001");
        registerDTO.setAddress(addressDTO);

        ConsumerAddressResponseDTO responseDTO = new ConsumerAddressResponseDTO();
        responseDTO.setConsumer(new ConsumerAddressResponseDTO.ConsumerDTO("123",
                "John Doe Updated",
                "35",
                "john.doe.updated@example.com"));
        responseDTO.setAddress(new ConsumerAddressResponseDTO.AddressDTO("789",
                "123",
                "456 Updated St",
                "Updated City",
                "CA",
                "90001"));

        when(consumerAddressService.updateConsumerAddress(eq(consumerId), any())).thenReturn(responseDTO);

        ResponseEntity<ConsumerAddressResponseDTO> response = consumerAddressController.updateConsumerAddress(consumerId, registerDTO);

        System.out.println("Response: " + response);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getConsumer());
        assertEquals("123", response.getBody().getConsumer().getId());
        verify(consumerAddressService, times(1)).updateConsumerAddress(eq(consumerId), any());
    }


    @Test
    public void testDeleteConsumerAddress() {
        String consumerId = "123";

        doNothing().when(consumerAddressService).deleteConsumerAddress(consumerId);

        ResponseEntity<Void> response = consumerAddressController.deleteConsumerAddress(consumerId);

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(consumerAddressService, times(1)).deleteConsumerAddress(consumerId);
    }
}
