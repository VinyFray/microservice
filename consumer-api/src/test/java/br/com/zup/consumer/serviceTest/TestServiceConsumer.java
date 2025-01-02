package br.com.zup.consumer.serviceTest;

import br.com.zup.consumer.controllers.dtos.ConsumerResponseDTO;
import br.com.zup.consumer.controllers.infra.ConsumerNotFoundException;
import br.com.zup.consumer.models.Consumer;
import br.com.zup.consumer.repositories.ConsumerRepository;
import br.com.zup.consumer.services.ConsumerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestServiceConsumer {

    @Mock
    private ConsumerRepository consumerRepository;

    @InjectMocks
    private ConsumerService consumerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateConsumer() {
        Consumer consumer = new Consumer();
        consumer.setId("2f246ea2-e010-4ef3-9902-ffa42446ad76");
        consumer.setName("Nimus");
        consumer.setAge("30");
        consumer.setEmail("arselina8189@uorak.com");

        when(consumerRepository.save(consumer)).thenReturn(consumer);

        ConsumerResponseDTO createdConsumer = consumerService.createConsumer(consumer);

        assertNotNull(createdConsumer);
        assertEquals("Nimus", createdConsumer.getName());
        verify(consumerRepository, times(1)).save(consumer);
    }


    @Test
    public void testGetAllConsumers() {
        Consumer consumer1 = new Consumer();
        consumer1.setId("2f246ea2-e010-4ef3-9902-ffa42446ad76");
        consumer1.setName("Nimus");
        consumer1.setAge("30");
        consumer1.setEmail("arselina8189@uorak.com");

        Consumer consumer2 = new Consumer();
        consumer2.setId("2a0ea765-752e-472c-a873-d14c5173f190");
        consumer2.setName("Sounborn");
        consumer2.setAge("25");
        consumer2.setEmail("abdelwahad4756@uorak.com");

        when(consumerRepository.findAll()).thenReturn(Arrays.asList(consumer1, consumer2));

        List<ConsumerResponseDTO> consumers = consumerService.getAllConsumers();

        assertNotNull(consumers);
        assertEquals(2, consumers.size());
        verify(consumerRepository, times(1)).findAll();
    }

    @Test
    public void testGetConsumerById() {
        Consumer consumer = new Consumer();
        consumer.setId("2f246ea2-e010-4ef3-9902-ffa42446ad76");
        consumer.setName("Nimus");
        consumer.setAge("22");
        consumer.setEmail("abdelwahad4756@uorak.com");

        when(consumerRepository.findById("2f246ea2-e010-4ef3-9902-ffa42446ad76")).thenReturn(Optional.of(consumer));

        ConsumerResponseDTO foundConsumer = consumerService.getConsumerById("2f246ea2-e010-4ef3-9902-ffa42446ad76");

        assertNotNull(foundConsumer);
        assertEquals("Nimus", foundConsumer.getName());
        verify(consumerRepository, times(1)).findById("2f246ea2-e010-4ef3-9902-ffa42446ad76");
    }

    @Test
    public void testGetConsumerById_NotFound() {
        when(consumerRepository.findById("2f246ea2-e010-4ef3-9902-ffa42446ad76")).thenReturn(Optional.empty());

        assertThrows(ConsumerNotFoundException.class, () -> consumerService.getConsumerById("2f246ea2-e010-4ef3-9902-ffa42446ad76"));
        verify(consumerRepository, times(1)).findById("2f246ea2-e010-4ef3-9902-ffa42446ad76");
    }

    @Test
    public void testUpdateConsumer() {
        Consumer existingConsumer = new Consumer();
        existingConsumer.setId("2f246ea2-e010-4ef3-9902-ffa42446ad76");
        existingConsumer.setName("Nimus");
        existingConsumer.setAge("30");
        existingConsumer.setEmail("abdelwahad4756@uorak.com");

        Consumer updatedConsumer = new Consumer();
        updatedConsumer.setId("2f246ea2-e010-4ef3-9902-ffa42446ad76");
        updatedConsumer.setName("Nimus");
        updatedConsumer.setAge("35");
        updatedConsumer.setEmail("abdelwahad4756@uorak.com");

        when(consumerRepository.findById("2f246ea2-e010-4ef3-9902-ffa42446ad76")).thenReturn(Optional.of(existingConsumer));
        when(consumerRepository.save(existingConsumer)).thenReturn(existingConsumer);

        ConsumerResponseDTO result = consumerService.updateConsumer("2f246ea2-e010-4ef3-9902-ffa42446ad76", updatedConsumer);

        assertNotNull(result);
        assertEquals("Nimus", result.getName());
        verify(consumerRepository, times(1)).findById("2f246ea2-e010-4ef3-9902-ffa42446ad76");
        verify(consumerRepository, times(1)).save(existingConsumer);
    }

    @Test
    public void testUpdateConsumer_NotFound() {
        Consumer updatedConsumer = new Consumer();
        updatedConsumer.setId("2f246ea2-e010-4ef3-9902-ffa42446ad7");
        updatedConsumer.setName("Nimus");
        updatedConsumer.setAge("35");
        updatedConsumer.setEmail("abdelwahad4756@uorak.com");

        when(consumerRepository.findById("2f246ea2-e010-4ef3-9902-ffa42446ad7")).thenReturn(Optional.empty());

        assertThrows(ConsumerNotFoundException.class, () -> consumerService.updateConsumer("2f246ea2-e010-4ef3-9902-ffa42446ad7", updatedConsumer));
        verify(consumerRepository, times(1)).findById("2f246ea2-e010-4ef3-9902-ffa42446ad7");
    }

    @Test
    public void testDeleteConsumer() {
        when(consumerRepository.existsById("2f246ea2-e010-4ef3-9902-ffa42446ad7")).thenReturn(true);

        consumerService.deleteConsumer("2f246ea2-e010-4ef3-9902-ffa42446ad7");

        verify(consumerRepository, times(1)).existsById("2f246ea2-e010-4ef3-9902-ffa42446ad7");
        verify(consumerRepository, times(1)).deleteById("2f246ea2-e010-4ef3-9902-ffa42446ad7");
    }

    @Test
    public void testDeleteConsumer_NotFound() {
        when(consumerRepository.existsById("2f246ea2-e010-4ef3-9902-ffa42446ad7")).thenReturn(false);

        assertThrows(ConsumerNotFoundException.class, () -> consumerService.deleteConsumer("2f246ea2-e010-4ef3-9902-ffa42446ad7"));
        verify(consumerRepository, times(1)).existsById("2f246ea2-e010-4ef3-9902-ffa42446ad7");
    }

}
