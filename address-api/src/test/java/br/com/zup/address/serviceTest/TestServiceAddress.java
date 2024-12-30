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
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAddress() {
        Address address =  new Address("1", "Rua A", "City A", "12345-678", "ST", "Consumer123");
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        AddressResponseDTO response = addressService.createAddress(address);

        assertEquals("1", response.getId());
        assertEquals("Rua A", response.getStreet());
        assertEquals("City A", response.getCity());
        assertEquals("12345-678", response.getZipCode());
        assertEquals("ST", response.getState());
        assertEquals("Consumer123", response.getConsumerId());
        verify(addressRepository, times(1)).save(any(Address.class));
    }

    @Test
    public void testGetAddressById() {
        Address address = new Address("1", "Rua A", "City A", "12345-678", "ST", "Consumer123");
        when(addressRepository.findById("1")).thenReturn(Optional.of(address));

        AddressResponseDTO response = addressService.getAddressById("1");

        assertEquals("1", response.getId());
        assertEquals("Rua A", response.getStreet());
        assertEquals("City A", response.getCity());
        assertEquals("12345-678", response.getZipCode());
        assertEquals("ST", response.getState());
        assertEquals("Consumer123", response.getConsumerId());
        verify(addressRepository, times(1)).findById("1");
    }

    @Test
    public void testGetAddressByIdNotFound() {
        when(addressRepository.findById("1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(AddressNotFoundException.class, () -> {
            addressService.getAddressById("1");
        });

        assertEquals("Address not found with id 1", exception.getMessage());
        verify(addressRepository, times(1)).findById("1");
    }

    @Test
    public void testUpdateAddress() {
        Address existingAddress = new Address("1", "Rua A", "City A", "12345-678", "ST", "Consumer123");
        Address updatedAddress = new Address("1", "Rua B", "City B", "98765-432", "SP", "Consumer456");
        when(addressRepository.findById("1")).thenReturn(Optional.of(existingAddress));
        when(addressRepository.save(any(Address.class))).thenReturn(updatedAddress);

        AddressResponseDTO response = addressService.updateAddress("1", updatedAddress);

        assertEquals("1", response.getId());
        assertEquals("Rua B", response.getStreet());
        assertEquals("City B", response.getCity());
        assertEquals("98765-432", response.getZipCode());
        assertEquals("SP", response.getState());
        assertEquals("Consumer456", response.getConsumerId());
        verify(addressRepository, times(1)).findById("1");
        verify(addressRepository, times(1)).save(any(Address.class));
    }

    @Test
    public void testDeleteAddress() {
        when(addressRepository.existsById("1")).thenReturn(true);
        doNothing().when(addressRepository).deleteById("1");

        addressService.deleteAddress("1");

        verify(addressRepository, times(1)).existsById("1");
        verify(addressRepository, times(1)).deleteById("1");
    }

    @Test
    public void testDeleteAddressNotFound() {
        when(addressRepository.existsById("1")).thenReturn(false);

        Exception exception = assertThrows(AddressNotFoundException.class, () -> {
            addressService.deleteAddress("1");
        });

        assertEquals("Address not found with id 1", exception.getMessage());
        verify(addressRepository, times(1)).existsById("1");
    }
}
