package br.com.zup.address.controllers;

import br.com.zup.address.controllers.dtos.AddressRequestDTO;
import br.com.zup.address.controllers.dtos.AddressResponseDTO;
import br.com.zup.address.controllers.infra.AddressNotFoundException;
import br.com.zup.address.models.Address;
import br.com.zup.address.services.AddressService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/addresses")
@Slf4j
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<AddressResponseDTO> createAddress(@Valid @RequestBody AddressRequestDTO requestDTO) {
        log.info("Start address register flow");
        Address address = toEntity(requestDTO);
        Address createdAddress = addressService.createAddress(address);
        log.info("Finish address register flow");
        return ResponseEntity.ok(toResponseDTO(createdAddress));
    }

    @GetMapping
    public ResponseEntity<List<AddressResponseDTO>> getAllAddresses() {
        List<Address> addresses = addressService.getAllAddresses();
        List<AddressResponseDTO> responseDTOs = addresses.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> getAddressById(@PathVariable String id) {
        Address address = addressService.getAddressById(id)
                .orElseThrow(() -> new AddressNotFoundException("Address not found with id " + id));
        return ResponseEntity.ok(toResponseDTO(address));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> updateAddress(@PathVariable String id, @Valid @RequestBody AddressRequestDTO requestDTO) {
        Address updatedAddress = toEntity(requestDTO);
        Address address = addressService.updateAddress(id, updatedAddress);
        return ResponseEntity.ok(toResponseDTO(address));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable String id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }

    private Address toEntity(AddressRequestDTO requestDTO) {
        Address address = new Address();
        address.setStreet(requestDTO.getStreet());
        address.setCity(requestDTO.getCity());
        address.setZipCode(requestDTO.getZipCode());
        address.setState(requestDTO.getState());
        address.setConsumerId(requestDTO.getConsumerId());
        return address;
    }

    private AddressResponseDTO toResponseDTO(Address address) {
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