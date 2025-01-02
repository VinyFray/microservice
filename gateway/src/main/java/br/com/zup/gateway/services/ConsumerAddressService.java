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

    private ConsumerResponseDTO registerConsumer(ConsumerAddressRegisterDTO consumerAddressRegisterDTO) {
        logger.debug("Mapping data for consumer registration.");
        ConsumerRegisterDTO consumerRegisterDTO = mapToConsumerRegisterDTO(consumerAddressRegisterDTO);

        logger.debug("Sending consumer data to the external customer.");
        return consumerClient.registerConsumerClient(consumerRegisterDTO);
    }

    private AddressResponseDTO registerAddress(ConsumerAddressRegisterDTO consumerAddressRegisterDTO, String consumerId) {
        logger.debug("Mapping data to address registration.");
        AddressRegisterDTO addressRegisterDto = mapToAddressRegisterDTO(consumerAddressRegisterDTO, consumerId);

        logger.debug("Sending address data to the external client.");
        return addressClient.registeAddress(addressRegisterDto);
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

        logger.debug("Mapping ConsumerAddressRegisterDTO para AddressRegisterDto.");
        AddressRegisterDTO addressRegisterDto = new AddressRegisterDTO();
        addressRegisterDto.setConsumerId(consumerId);
        addressRegisterDto.setCity(consumerAddressRegisterDTO.getAddress().getCity());
        addressRegisterDto.setState(consumerAddressRegisterDTO.getAddress().getState());
        addressRegisterDto.setStreet(consumerAddressRegisterDTO.getAddress().getStreet());
        addressRegisterDto.setZipCode(consumerAddressRegisterDTO.getAddress().getZipCode());
        return addressRegisterDto;
    }
}