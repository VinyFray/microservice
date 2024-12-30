package br.com.zup.address.services;

import br.com.zup.address.controllers.dtos.AddressResponseDTO;
import br.com.zup.address.controllers.infra.AddressNotFoundException;
import br.com.zup.address.models.Address;
import br.com.zup.address.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {

    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);

    @Autowired
    private AddressRepository addressRepository;

    public AddressResponseDTO createAddress(Address address) {
        logger.info("Creating a new address: {}", address);
        Address savedAddress = addressRepository.save(address);
        return mapToDTO(savedAddress);
    }

    public List<AddressResponseDTO> getAllAddresses() {
        logger.info("Fetching all addresses");
        return addressRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public AddressResponseDTO getAddressById(String id) {
        logger.info("Fetching address with id: {}", id);
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException("Address not found with id " + id));
        return mapToDTO(address);
    }

    public AddressResponseDTO updateAddress(String id, Address updatedAddress) {
        logger.info("Updating address with id: {}", id);
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException("Address not found with id " + id));
        address.setStreet(updatedAddress.getStreet());
        address.setCity(updatedAddress.getCity());
        address.setState(updatedAddress.getState());
        address.setZipCode(updatedAddress.getZipCode());
        address.setConsumerId(updatedAddress.getConsumerId());
        Address savedAddress = addressRepository.save(address);
        return mapToDTO(savedAddress);
    }

    public void deleteAddress(String id) {
        logger.info("Deleting address with id: {}", id);
        if (!addressRepository.existsById(id)) {
            logger.error("Address not found with id: {}", id);
            throw new AddressNotFoundException("Address not found with id " + id);
        }
        addressRepository.deleteById(id);
        logger.info("Address deleted with id: {}", id);
    }

    private AddressResponseDTO mapToDTO(Address address) {
        AddressResponseDTO dto = new AddressResponseDTO();
        dto.setId(address.getId());
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setZipCode(address.getZipCode());
        dto.setState(address.getState());
        dto.setConsumerId(address.getConsumerId());
        return dto;
    }
}
