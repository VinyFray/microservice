package br.com.zup.gateway.controllers;

import br.com.zup.gateway.controllers.dtos.ConsumerAddressRegisterDTO;
import br.com.zup.gateway.controllers.dtos.ConsumerAddressResponseDTO;
import br.com.zup.gateway.services.ConsumerAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/consumer-address")
@Slf4j
public class ConsumerAddressController {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerAddressController.class);

    @Autowired
    private ConsumerAddressService consumerAddressService;

    @PostMapping
    public ResponseEntity<ConsumerAddressResponseDTO> register(@Valid @RequestBody ConsumerAddressRegisterDTO registerDTO) {
        logger.info("Received request to register consumer and address.");
        ConsumerAddressResponseDTO response = consumerAddressService.registerConsumerAddress(registerDTO);
        logger.info("Successfully registered consumer and address. Consumer ID: {}", response.getConsumer().getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{consumerId}")
    public ResponseEntity<ConsumerAddressResponseDTO> getConsumerAddress(@PathVariable String consumerId) {
        logger.info("Received request to fetch consumer and address for ID: {}", consumerId);
        ConsumerAddressResponseDTO response = consumerAddressService.getConsumerAddress(consumerId);
        logger.info("Successfully fetched consumer and address for ID: {}", consumerId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{consumerId}")
    public ResponseEntity<ConsumerAddressResponseDTO> updateConsumerAddress(
            @PathVariable String consumerId,
            @Valid @RequestBody ConsumerAddressRegisterDTO registerDTO) {
        logger.info("Received request to update consumer and address for ID: {}", consumerId);
        ConsumerAddressResponseDTO response = consumerAddressService.updateConsumerAddress(consumerId, registerDTO);
        logger.info("Successfully updated consumer and address for ID: {}", consumerId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{consumerId}")
    public ResponseEntity<Void> deleteConsumerAddress(@PathVariable String consumerId) {
        logger.info("Received request to delete consumer and address for ID: {}", consumerId);
        consumerAddressService.deleteConsumerAddress(consumerId);
        logger.info("Successfully deleted consumer and address for ID: {}", consumerId);
        return ResponseEntity.noContent().build();
    }
}