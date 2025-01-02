package br.com.zup.gateway.controllers.dtos;

import br.com.zup.gateway.infra.clients.address.dtos.AddressResponseDTO;
import br.com.zup.gateway.infra.clients.consumer.dtos.ConsumerResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsumerAddressResponseDTO {

    private ConsumerResponseDTO consumer;
    private AddressResponseDTO address;

    public ConsumerAddressResponseDTO() {
    }

    public ConsumerAddressResponseDTO(ConsumerResponseDTO consumerResponseDTO, AddressResponseDTO addressResponseDTO) {
        this.consumer = consumerResponseDTO;
        this.address = addressResponseDTO;
    }
}