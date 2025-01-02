package br.com.zup.gateway.services;

import br.com.zup.gateway.controllers.dtos.ConsumerAddressRegisterDTO;
import br.com.zup.gateway.controllers.dtos.ConsumerAddressResponseDTO;
import br.com.zup.gateway.infra.clients.address.AddressClient;
import br.com.zup.gateway.infra.clients.address.dtos.AddressRegisterDTO;
import br.com.zup.gateway.infra.clients.address.dtos.AddressResponseDTO;
import br.com.zup.gateway.infra.clients.consumer.ConsumerClient;
import br.com.zup.gateway.infra.clients.consumer.dtos.ConsumerRegisterDTO;
import br.com.zup.gateway.infra.clients.consumer.dtos.ConsumerResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ConsumerAddressService {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerAddressService.class);

    private final ConsumerClient consumerClient;
    private final AddressClient addressClient;

    public ConsumerAddressService(ConsumerClient consumerClient, AddressClient addressClient) {
        this.consumerClient = consumerClient;
        this.addressClient = addressClient;
    }

    public ConsumerAddressResponseDTO registerConsumerAddress(ConsumerAddressRegisterDTO consumerAddressRegisterDTO) {
        Assert.notNull(consumerAddressRegisterDTO, "ConsumerAddressRegisterDTO cannot be null");

        logger.info("Initiating the registration of the consumer and address.");

        ConsumerResponseDTO consumerResponseDTO = registerConsumer(consumerAddressRegisterDTO);
        logger.info("Successfully registered consumer. ID: {}", consumerResponseDTO.getId());

        AddressResponseDTO addressResponseDTO = registerAddress(consumerAddressRegisterDTO, consumerResponseDTO.getId());
        logger.info("Successfully registered address for the consumer ID: {}", consumerResponseDTO.getId());

        logger.info("Consumer registration and address successfully completed.");
        return new ConsumerAddressResponseDTO(consumerResponseDTO, addressResponseDTO);
    }

    public ConsumerAddressResponseDTO getConsumerAddress(String consumerId) {
        Assert.hasText(consumerId, "Consumer ID cannot be blank");
        logger.info("Fetching consumer and address for ID: {}", consumerId);

        ConsumerResponseDTO consumerResponseDTO = consumerClient.getConsumer(consumerId);
        AddressResponseDTO addressResponseDTO = addressClient.getAddressByConsumerId(consumerId);

        return new ConsumerAddressResponseDTO(consumerResponseDTO, addressResponseDTO);
    }

    public ConsumerAddressResponseDTO updateConsumerAddress(String consumerId, ConsumerAddressRegisterDTO consumerAddressRegisterDTO) {
        Assert.hasText(consumerId, "Consumer ID cannot be blank");
        Assert.notNull(consumerAddressRegisterDTO, "ConsumerAddressRegisterDTO cannot be null");
        logger.info("Updating consumer and address for ID: {}", consumerId);

        ConsumerRegisterDTO consumerRegisterDTO = mapToConsumerRegisterDTO(consumerAddressRegisterDTO);
        ConsumerResponseDTO updatedConsumer = consumerClient.updateConsumer(consumerId, consumerRegisterDTO);

        AddressRegisterDTO addressRegisterDTO = mapToAddressRegisterDTO(consumerAddressRegisterDTO, consumerId);
        AddressResponseDTO updatedAddress = addressClient.updateAddress(consumerId, addressRegisterDTO);

        return new ConsumerAddressResponseDTO(updatedConsumer, updatedAddress);
    }

    public void deleteConsumerAddress(String consumerId) {
        Assert.hasText(consumerId, "Consumer ID cannot be blank");
        logger.info("Deleting consumer and address for ID: {}", consumerId);

        addressClient.deleteAddressByConsumerId(consumerId);
        consumerClient.deleteConsumerById(consumerId);

        logger.info("Successfully deleted consumer and address for ID: {}", consumerId);
    }

    private ConsumerResponseDTO registerConsumer(ConsumerAddressRegisterDTO consumerAddressRegisterDTO) {
        logger.debug("Mapping data for consumer registration.");
        ConsumerRegisterDTO consumerRegisterDTO = mapToConsumerRegisterDTO(consumerAddressRegisterDTO);

        logger.debug("Sending consumer data to the external customer.");
        return consumerClient.registerConsumerClient(consumerRegisterDTO);
    }

    private AddressResponseDTO registerAddress(ConsumerAddressRegisterDTO consumerAddressRegisterDTO, String consumerId) {
        logger.debug("Mapping data to address registration.");
        AddressRegisterDTO addressRegisterDTO = mapToAddressRegisterDTO(consumerAddressRegisterDTO, consumerId);

        logger.debug("Sending address data to the external client.");
        return addressClient.registeAddress(addressRegisterDTO);
    }

    private ConsumerRegisterDTO mapToConsumerRegisterDTO(ConsumerAddressRegisterDTO consumerAddressRegisterDTO) {
        Assert.notNull(consumerAddressRegisterDTO, "ConsumerAddressRegisterDTO cannot be null");

        logger.debug("Mapping ConsumerAddressRegisterDTO to ConsumerRegisterDTO.");
        ConsumerRegisterDTO consumerRegisterDTO = new ConsumerRegisterDTO();
        consumerRegisterDTO.setAge(consumerAddressRegisterDTO.getAge());
        consumerRegisterDTO.setEmail(consumerAddressRegisterDTO.getEmail());
        consumerRegisterDTO.setName(consumerAddressRegisterDTO.getName());
        return consumerRegisterDTO;
    }

    private AddressRegisterDTO mapToAddressRegisterDTO(ConsumerAddressRegisterDTO consumerAddressRegisterDTO, String consumerId) {
        Assert.notNull(consumerAddressRegisterDTO, "ConsumerAddressRegisterDTO cannot be null");
        Assert.hasText(consumerId, "Consumer ID cannot be blank");

        logger.debug("Mapping ConsumerAddressRegisterDTO to AddressRegisterDTO.");
        AddressRegisterDTO addressRegisterDTO = new AddressRegisterDTO();
        addressRegisterDTO.setConsumerId(consumerId);
        addressRegisterDTO.setCity(consumerAddressRegisterDTO.getAddress().getCity());
        addressRegisterDTO.setState(consumerAddressRegisterDTO.getAddress().getState());
        addressRegisterDTO.setStreet(consumerAddressRegisterDTO.getAddress().getStreet());
        addressRegisterDTO.setZipCode(consumerAddressRegisterDTO.getAddress().getZipCode());
        return addressRegisterDTO;
    }
}
