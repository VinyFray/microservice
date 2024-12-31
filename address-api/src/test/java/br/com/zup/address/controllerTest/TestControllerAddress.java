package br.com.zup.address.controllerTest;

import br.com.zup.address.controllers.AddressController;
import br.com.zup.address.controllers.dtos.AddressRequestDTO;
import br.com.zup.address.controllers.dtos.AddressResponseDTO;
import br.com.zup.address.models.Address;
import br.com.zup.address.services.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TestControllerAddress {

    @InjectMocks
    private AddressController addressController;

    @Mock
    private AddressService addressService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAddress() {
        AddressRequestDTO requestDTO = new AddressRequestDTO();
        requestDTO.setStreet("Rua Mizael Nogueira 195");
        requestDTO.setCity("Abadia dos Dourados");
        requestDTO.setZipCode("38540-970");
        requestDTO.setState("MG");
        requestDTO.setConsumerId("Consumer 1");

        AddressResponseDTO responseDTO = new AddressResponseDTO();
        responseDTO.setId("1");
        responseDTO.setStreet("Street 1");
        responseDTO.setCity("City 1");
        responseDTO.setZipCode("12345");
        responseDTO.setState("State 1");
        responseDTO.setConsumerId("f8dcf17d-7808-46c8-9265-7b62310dcf5e");

        when(addressService.createAddress(any(Address.class))).thenReturn(responseDTO);

        ResponseEntity<AddressResponseDTO> response = addressController.createAddress(requestDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseDTO, response.getBody());
        verify(addressService, times(1)).createAddress(any(Address.class));
    }

    @Test
    public void testGetAllAddresses() {
        AddressResponseDTO responseDTO1 = new AddressResponseDTO();
        responseDTO1.setId("dcf95dd-f223-4903-9e66-8f524e415286");
        responseDTO1.setStreet("Rua Mizael Nogueira 195");

        AddressResponseDTO responseDTO2 = new AddressResponseDTO();
        responseDTO2.setId("f3a78e1f-568a-47a2-98e7-1ac00573f5f4");
        responseDTO2.setStreet("Rua Principal");

        List<AddressResponseDTO> responseDTOs = Arrays.asList(responseDTO1, responseDTO2);

        when(addressService.getAllAddresses()).thenReturn(responseDTOs);

        ResponseEntity<List<AddressResponseDTO>> response = addressController.getAllAddresses();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseDTOs, response.getBody());
        verify(addressService, times(1)).getAllAddresses();
    }

    @Test
    public void testGetAddressById() {
        String id = "dcf95dd-f223-4903-9e66-8f524e415286";
        AddressResponseDTO responseDTO = new AddressResponseDTO();
        responseDTO.setId(id);
        responseDTO.setStreet("Rua Mizael Nogueira 195");

        when(addressService.getAddressById(id)).thenReturn(responseDTO);

        ResponseEntity<AddressResponseDTO> response = addressController.getAddressById(id);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseDTO, response.getBody());
        verify(addressService, times(1)).getAddressById(id);
    }

    @Test
    public void testUpdateAddress() {
        String id = "f3a78e1f-568a-47a2-98e7-1ac00573f5f4";
        AddressRequestDTO requestDTO = new AddressRequestDTO();
        requestDTO.setStreet("Rua XV de Novembro 681");
        requestDTO.setCity("Agronômica");
        requestDTO.setZipCode("89188-970");
        requestDTO.setState("SC");
        requestDTO.setConsumerId("07709e68-6e4c-4801-8017-cc46828f94b6");

        AddressResponseDTO responseDTO = new AddressResponseDTO();
        responseDTO.setId(id);
        responseDTO.setStreet("Rua XV de Novembro 681");
        responseDTO.setCity("Updated City");
        responseDTO.setZipCode("89188-970");
        responseDTO.setState("Agronômica");
        responseDTO.setConsumerId("SC");

        when(addressService.updateAddress(eq(id), any(Address.class))).thenReturn(responseDTO);

        ResponseEntity<AddressResponseDTO> response = addressController.updateAddress(id, requestDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseDTO, response.getBody());
        verify(addressService, times(1)).updateAddress(eq(id), any(Address.class));
    }

    @Test
    public void testDeleteAddress() {
        String id = "f3a78e1f-568a-47a2-98e7-1ac00573f5f4";

        doNothing().when(addressService).deleteAddress(id);

        ResponseEntity<Void> response = addressController.deleteAddress(id);

        assertEquals(204, response.getStatusCodeValue());
        verify(addressService, times(1)).deleteAddress(id);
    }
}