package br.com.zup.gateway.serviceTest;

import br.com.zup.gateway.controllers.dtos.ConsumerAddressRegisterDTO;
import br.com.zup.gateway.controllers.dtos.ConsumerAddressResponseDTO;
import br.com.zup.gateway.controllers.dtos.AddressDTO;
import br.com.zup.gateway.infra.clients.address.AddressClient;
import br.com.zup.gateway.infra.clients.address.dtos.AddressRegisterDTO;
import br.com.zup.gateway.infra.clients.address.dtos.AddressResponseDTO;
import br.com.zup.gateway.infra.clients.consumer.ConsumerClient;
import br.com.zup.gateway.infra.clients.consumer.dtos.ConsumerRegisterDTO;
import br.com.zup.gateway.infra.clients.consumer.dtos.ConsumerResponseDTO;
import br.com.zup.gateway.services.ConsumerAddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class TestServiceConsumerAddress {

    @Mock
    private ConsumerClient consumerClient;

    @Mock
    private AddressClient addressClient;

    @InjectMocks
    private ConsumerAddressService consumerAddressService;

    private ConsumerResponseDTO consumerResponseDTO;
    private AddressResponseDTO addressResponseDTO;
    private ConsumerAddressRegisterDTO consumerAddressRegisterDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        consumerResponseDTO = new ConsumerResponseDTO();
        consumerResponseDTO.setId("dcf95dd-f223-4903-9e66-8f524e415286");
        consumerResponseDTO.setName("Nimus");
        consumerResponseDTO.setAge("35");
        consumerResponseDTO.setEmail("abdelwahad4756@uorak.com");

        addressResponseDTO = new AddressResponseDTO();
        addressResponseDTO.setId("f8dcf17d-7808-46c8-9265-7b62310dcf5e");
        addressResponseDTO.setStreet("Rua Mizael Nogueira 195");
        addressResponseDTO.setCity("Abadia dos Dourados");
        addressResponseDTO.setZipCode("38540-970");
        addressResponseDTO.setState("MG");

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet("Rua Mizael Nogueira 195");
        addressDTO.setCity("Abadia dos Dourados");
        addressDTO.setZipCode("38540-970");
        addressDTO.setState("MG");

        consumerAddressRegisterDTO = new ConsumerAddressRegisterDTO();
        consumerAddressRegisterDTO.setName("Nimus");
        consumerAddressRegisterDTO.setAge("35");
        consumerAddressRegisterDTO.setEmail("abdelwahad4756@uorak.com");
        consumerAddressRegisterDTO.setAddress(addressDTO);
    }

    @Test
    public void testRegisterConsumerAddress() {
        when(consumerClient.registerConsumerClient(any(ConsumerRegisterDTO.class))).thenReturn(consumerResponseDTO);
        when(addressClient.registeAddress(any(AddressRegisterDTO.class))).thenReturn(addressResponseDTO);

        ConsumerAddressResponseDTO response = consumerAddressService.registerConsumerAddress(consumerAddressRegisterDTO);

        assertEquals("dcf95dd-f223-4903-9e66-8f524e415286", response.getConsumer().getId());
        assertEquals("Nimus", response.getConsumer().getName());
        assertEquals("35", response.getConsumer().getAge());
        assertEquals("abdelwahad4756@uorak.com", response.getConsumer().getEmail());

        assertEquals("f8dcf17d-7808-46c8-9265-7b62310dcf5e", response.getAddress().getId());
        assertEquals("Rua Mizael Nogueira 195", response.getAddress().getStreet());
        assertEquals("Abadia dos Dourados", response.getAddress().getCity());
        assertEquals("38540-970", response.getAddress().getZipCode());
        assertEquals("MG", response.getAddress().getState());

        verify(consumerClient, times(1)).registerConsumerClient(any(ConsumerRegisterDTO.class));
        verify(addressClient, times(1)).registeAddress(any(AddressRegisterDTO.class));
    }

    @Test
    public void getConsumerAddress_ShouldReturnConsumerAndAddress() {
        when(consumerClient.getConsumer("1")).thenReturn(consumerResponseDTO);
        when(addressClient.getAddressByConsumerId("1")).thenReturn(addressResponseDTO);

        ConsumerAddressResponseDTO result = consumerAddressService.getConsumerAddress("1");

        assertNotNull(result.getConsumer());
        assertNotNull(result.getAddress());
        assertEquals("dcf95dd-f223-4903-9e66-8f524e415286", result.getConsumer().getId());
        assertEquals("f8dcf17d-7808-46c8-9265-7b62310dcf5e", result.getAddress().getId());
    }


    @Test
    public void updateConsumerAddress_ShouldUpdateSuccessfully() {
        when(consumerClient.updateConsumer(eq("1"), any(ConsumerRegisterDTO.class))).thenReturn(consumerResponseDTO);
        when(addressClient.updateAddress(eq("1"), any(AddressRegisterDTO.class))).thenReturn(addressResponseDTO);

        ConsumerAddressResponseDTO result = consumerAddressService.updateConsumerAddress("1", consumerAddressRegisterDTO);

        assertNotNull(result.getConsumer());
        assertNotNull(result.getAddress());
        assertEquals("dcf95dd-f223-4903-9e66-8f524e415286", result.getConsumer().getId());
        assertEquals("f8dcf17d-7808-46c8-9265-7b62310dcf5e", result.getAddress().getId());
    }


    @Test
    public void deleteConsumerAddress_ShouldDeleteSuccessfully() {
        doNothing().when(consumerClient).deleteConsumerById("dcf95dd-f223-4903-9e66-8f524e415286");
        doNothing().when(addressClient).deleteAddressByConsumerId("f8dcf17d-7808-46c8-9265-7b62310dcf5e");

        consumerAddressService.deleteConsumerAddress("dcf95dd-f223-4903-9e66-8f524e415286");

        verify(consumerClient, times(1)).deleteConsumerById("dcf95dd-f223-4903-9e66-8f524e415286");
    }
}
