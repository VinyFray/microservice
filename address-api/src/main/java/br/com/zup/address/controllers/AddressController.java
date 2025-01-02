package br.com.zup.address.controllers;

import br.com.zup.address.controllers.dtos.AddressRequestDTO;
import br.com.zup.address.controllers.dtos.AddressResponseDTO;
import br.com.zup.address.models.Address;
import br.com.zup.address.services.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j; //automatização do logger

import java.util.List;

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
        AddressResponseDTO createdAddress = addressService.createAddress(address);
        log.info("Finish address register flow");
        return ResponseEntity.ok(createdAddress);
    }

    @GetMapping
    public ResponseEntity<List<AddressResponseDTO>> getAllAddresses() {
        List<AddressResponseDTO> responseDTOs = addressService.getAllAddresses();

        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> getAddressById(@PathVariable String id) {
        AddressResponseDTO responseDTO = addressService.getAddressById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> updateAddress(@PathVariable String id, @Valid @RequestBody AddressRequestDTO requestDTO) {
        Address updatedAddress = toEntity(requestDTO);
        AddressResponseDTO responseDTO = addressService.updateAddress(id, updatedAddress);
        return ResponseEntity.ok(responseDTO);
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

}
