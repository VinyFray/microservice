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

    public static class ConsumerDTO extends ConsumerResponseDTO {
        public ConsumerDTO(String number, String johnDoe, String number1, String mail) {
        }
    }

    public static class AddressDTO extends AddressResponseDTO {
        public AddressDTO(String number, String number1, String s, String springfield, String il, String number2) {
        }
    }
}