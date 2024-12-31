package br.com.zup.consumer.controllers;

import br.com.zup.consumer.controllers.dtos.ConsumerRequestDTO;
import br.com.zup.consumer.controllers.dtos.ConsumerResponseDTO;
import br.com.zup.consumer.models.Consumer;
import br.com.zup.consumer.services.ConsumerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/consumers")
public class ConsumerController {

    private final ConsumerService consumerService;

    public ConsumerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @PostMapping
    public ResponseEntity<ConsumerResponseDTO> createConsumer(@Valid @RequestBody ConsumerRequestDTO consumerRequestDTO) {
        Consumer consumer = consumerService.createConsumer(consumerRequestDTO.toEntity());
        return ResponseEntity.status(201).body(ConsumerResponseDTO.fromEntity(consumer));
    }

    @GetMapping
    public ResponseEntity<List<ConsumerResponseDTO>> getAllConsumers() {
        List<Consumer> consumers = consumerService.getAllConsumers();
        List<ConsumerResponseDTO> response = consumers.stream()
                .map(ConsumerResponseDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsumerResponseDTO> getConsumerById(@PathVariable String id) {
        Consumer consumer = consumerService.getConsumerById(id);
        return ResponseEntity.ok(ConsumerResponseDTO.fromEntity(consumer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsumerResponseDTO> updateConsumer(@PathVariable String id, @Valid @RequestBody ConsumerRequestDTO consumerRequestDTO) {
        Consumer updatedConsumer = consumerService.updateConsumer(id, consumerRequestDTO.toEntity());
        return ResponseEntity.ok(ConsumerResponseDTO.fromEntity(updatedConsumer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsumer(@PathVariable String id) {
        consumerService.deleteConsumer(id);
        return ResponseEntity.noContent().build();
    }
}