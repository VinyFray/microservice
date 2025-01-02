package br.com.zup.consumer.controllers;

import br.com.zup.consumer.controllers.dtos.ConsumerRequestDTO;
import br.com.zup.consumer.controllers.dtos.ConsumerResponseDTO;
import br.com.zup.consumer.services.ConsumerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consumers")
@Slf4j
public class ConsumerController {
    private final ConsumerService consumerService;

    public ConsumerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @PostMapping
    public ResponseEntity<ConsumerResponseDTO> createConsumer(@Valid @RequestBody ConsumerRequestDTO consumerRequestDTO) {
        log.info("Received request to create a consumer");
        ConsumerResponseDTO response = consumerService.createConsumer(consumerRequestDTO.toEntity());
        log.info("Consumer created successfully with id: {}", response.getId());
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ConsumerResponseDTO>> getAllConsumers() {
        log.info("Received request to fetch all consumers");
        List<ConsumerResponseDTO> response = consumerService.getAllConsumers();
        log.info("Fetched {} consumers", response.size());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsumerResponseDTO> getConsumerById(@PathVariable String id) {
        log.info("Received request to fetch consumer with id: {}", id);
        ConsumerResponseDTO response = consumerService.getConsumerById(id);
        log.info("Consumer with id: {} fetched successfully", id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsumerResponseDTO> updateConsumer(@PathVariable String id, @Valid @RequestBody ConsumerRequestDTO consumerRequestDTO) {
        log.info("Received request to update consumer with id: {}", id);
        ConsumerResponseDTO response = consumerService.updateConsumer(id, consumerRequestDTO.toEntity());
        log.info("Consumer with id: {} updated successfully", id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsumer(@PathVariable String id) {
        log.info("Received request to delete consumer with id: {}", id);
        consumerService.deleteConsumer(id);
        log.info("Consumer with id: {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }
}