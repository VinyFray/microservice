package br.com.zup.consumer.services;

import br.com.zup.consumer.controllers.dtos.ConsumerResponseDTO;
import br.com.zup.consumer.controllers.infra.ConsumerNotFoundException;
import br.com.zup.consumer.models.Consumer;
import br.com.zup.consumer.repositories.ConsumerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumerService {
    private static final Logger log = LoggerFactory.getLogger(ConsumerService.class);
    private final ConsumerRepository consumerRepository;

    public ConsumerService(ConsumerRepository consumerRepository) {
        this.consumerRepository = consumerRepository;
    }

    public ConsumerResponseDTO createConsumer(Consumer consumer) {
        log.info("Start create consumer flow");
        validateConsumer(consumer);
        Consumer savedConsumer = consumerRepository.save(consumer);
        log.info("Consumer created successfully with id: {}", savedConsumer.getId());
        return ConsumerResponseDTO.fromEntity(savedConsumer);
    }

    public List<ConsumerResponseDTO> getAllConsumers() {
        log.info("Fetching all consumers");
        return consumerRepository.findAll()
                .stream()
                .map(ConsumerResponseDTO::fromEntity)
                .toList();
    }

    public ConsumerResponseDTO getConsumerById(String id) {
        log.info("Fetching consumer with id: {}", id);
        Consumer consumer = consumerRepository.findById(id)
                .orElseThrow(() -> new ConsumerNotFoundException("Consumer not found with id: " + id));
        return ConsumerResponseDTO.fromEntity(consumer);
    }

    public ConsumerResponseDTO updateConsumer(String id, Consumer updatedConsumer) {
        log.info("Updating consumer with id: {}", id);
        validateConsumer(updatedConsumer);
        return consumerRepository.findById(id).map(consumer -> {
            consumer.setName(updatedConsumer.getName());
            consumer.setAge(updatedConsumer.getAge());
            consumer.setEmail(updatedConsumer.getEmail());
            Consumer savedConsumer = consumerRepository.save(consumer);
            log.info("Consumer updated successfully with id: {}", savedConsumer.getId());
            return ConsumerResponseDTO.fromEntity(savedConsumer);
        }).orElseThrow(() -> new ConsumerNotFoundException("Consumer not found with id: " + id));
    }

    public void deleteConsumer(String id) {
        log.info("Deleting consumer with id: {}", id);
        if (consumerRepository.existsById(id)) {
            consumerRepository.deleteById(id);
            log.info("Consumer deleted successfully with id: {}", id);
        } else {
            log.error("Delete consumer blocked: consumer not found with id: {}", id);
            throw new ConsumerNotFoundException("Consumer not found with id: " + id);
        }
    }

    private void validateConsumer(Consumer consumer) {
        if (consumer.getName() == null || consumer.getName().isEmpty()) {
            throw new IllegalArgumentException("The 'name' field cannot be empty.");
        }
        if (consumer.getEmail() == null || !consumer.getEmail().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
    }
}