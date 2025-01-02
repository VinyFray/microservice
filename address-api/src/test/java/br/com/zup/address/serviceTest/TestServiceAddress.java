package br.com.zup.address.serviceTest;

import br.com.zup.address.controllers.dtos.AddressResponseDTO;
import br.com.zup.address.controllers.infra.AddressNotFoundException;
import br.com.zup.address.models.Address;
import br.com.zup.address.repositories.AddressRepository;
import br.com.zup.address.services.AddressService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestServiceAddress {

    @InjectMocks
    private AddressService addressService;

    @Mock
    private AddressRepository addressRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAddress() {
        Address address = new Address("dcf95dd-f223-4903-9e66-8f524e415286", "Rua Mizael Nogueira 195", "Abadia dos Dourados", "38540-970", "MG", "f8dcf17d-7808-46c8-9265-7b62310dcf5e");
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        AddressResponseDTO response = addressService.createAddress(address);

        assertEquals("dcf95dd-f223-4903-9e66-8f524e415286", response.getId());
        assertEquals("Rua Mizael Nogueira 195", response.getStreet());
        assertEquals("Abadia dos Dourados", response.getCity());
        assertEquals("38540-970", response.getZipCode());
        assertEquals("MG", response.getState());
        assertEquals("f8dcf17d-7808-46c8-9265-7b62310dcf5e", response.getConsumerId());
        verify(addressRepository, times(1)).save(any(Address.class));
    }

    @Test
    public void testGetAddressById() {
        Address address = new Address("f3a78e1f-568a-47a2-98e7-1ac00573f5f4", "Rua Principal", "Alto Alegre", "89666-970", "SC", "07709e68-6e4c-4801-8017-cc46828f94b6");
        when(addressRepository.findById("f3a78e1f-568a-47a2-98e7-1ac00573f5f4")).thenReturn(Optional.of(address));

        AddressResponseDTO response = addressService.getAddressById("f3a78e1f-568a-47a2-98e7-1ac00573f5f4");

        assertEquals("f3a78e1f-568a-47a2-98e7-1ac00573f5f4", response.getId());
        assertEquals("Rua Principal", response.getStreet());
        assertEquals("Alto Alegre", response.getCity());
        assertEquals("89666-970", response.getZipCode());
        assertEquals("SC", response.getState());
        assertEquals("07709e68-6e4c-4801-8017-cc46828f94b6", response.getConsumerId());
        verify(addressRepository, times(1)).findById("f3a78e1f-568a-47a2-98e7-1ac00573f5f4");
    }

    @Test
    public void testGetAddressByIdNotFound() {
        when(addressRepository.findById("dcf95dd-f223-4903-9e66-8f524e415286")).thenReturn(Optional.empty());

        Exception exception = assertThrows(AddressNotFoundException.class, () -> {
            addressService.getAddressById("dcf95dd-f223-4903-9e66-8f524e415286");
        });

        assertEquals("Address not found with id dcf95dd-f223-4903-9e66-8f524e415286", exception.getMessage());
        verify(addressRepository, times(1)).findById("dcf95dd-f223-4903-9e66-8f524e415286");
    }

    @Test
    public void testUpdateAddress() {
        Address existingAddress = new Address("dcf95dd-f223-4903-9e66-8f524e415286", "Rua Mizael Nogueira 195", "Alto Alegre", "89666-970", "SC", "07709e68-6e4c-4801-8017-cc46828f94b6");
        Address updatedAddress = new Address("dcf95dd-f223-4903-9e66-8f524e415286", "Rua Mizael Nogueira 195", "Alto Alegre", "89666-970", "SP", "07709e68-6e4c-4801-8017-cc46828f94b6");
        when(addressRepository.findById("dcf95dd-f223-4903-9e66-8f524e415286")).thenReturn(Optional.of(existingAddress));
        when(addressRepository.save(any(Address.class))).thenReturn(updatedAddress);

        AddressResponseDTO response = addressService.updateAddress("dcf95dd-f223-4903-9e66-8f524e415286", updatedAddress);

        assertEquals("dcf95dd-f223-4903-9e66-8f524e415286", response.getId());
        assertEquals("Rua Mizael Nogueira 195", response.getStreet());
        assertEquals("Alto Alegre", response.getCity());
        assertEquals("89666-970", response.getZipCode());
        assertEquals("SP", response.getState());
        assertEquals("07709e68-6e4c-4801-8017-cc46828f94b6", response.getConsumerId());
        verify(addressRepository, times(1)).findById("dcf95dd-f223-4903-9e66-8f524e415286");
        verify(addressRepository, times(1)).save(any(Address.class));
    }

    @Test
    public void testDeleteAddress() {
        when(addressRepository.existsById("dcf95dd-f223-4903-9e66-8f524e415286")).thenReturn(true);
        doNothing().when(addressRepository).deleteById("dcf95dd-f223-4903-9e66-8f524e415286");

        addressService.deleteAddress("dcf95dd-f223-4903-9e66-8f524e415286");

        verify(addressRepository, times(1)).existsById("dcf95dd-f223-4903-9e66-8f524e415286");
        verify(addressRepository, times(1)).deleteById("dcf95dd-f223-4903-9e66-8f524e415286");
    }

    @Test
    public void testDeleteAddressNotFound() {
        when(addressRepository.existsById("dcf95dd-f223-4903-9e66-8f524e415286")).thenReturn(false);

        Exception exception = assertThrows(AddressNotFoundException.class, () -> {
            addressService.deleteAddress("dcf95dd-f223-4903-9e66-8f524e415286");
        });

        assertEquals("Address not found with id dcf95dd-f223-4903-9e66-8f524e415286", exception.getMessage());
        verify(addressRepository, times(1)).existsById("dcf95dd-f223-4903-9e66-8f524e415286");
    }
}