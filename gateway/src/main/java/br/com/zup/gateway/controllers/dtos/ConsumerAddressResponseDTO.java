package br.com.zup.gateway.controllers.dtos;

import br.com.zup.gateway.infra.clients.address.dtos.AddressResponseDTO;
import br.com.zup.gateway.infra.clients.consumer.dtos.ConsumerResponseDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ConsumerAddressResponseDTO {

    @NotBlank
    private String id;

    @NotBlank(message = "The name cannot be blank" )
    @Size(min = 3, max = 20)
    private String name;

    @NotBlank(message = "Age cannot be blank")
    @Size(max = 130)
    private String age;

    @NotBlank(message = "Email cannot be blank" )
    @Email(message = "Out of the norm")
    private String email;

    @NotBlank
    private AddressDTO address;

    public ConsumerAddressResponseDTO() {

    }

    public ConsumerAddressResponseDTO(ConsumerResponseDTO consumerResponseDTO, AddressResponseDTO addressResponseDTO) {
        this.id = consumerResponseDTO.getId();
        this.name = consumerResponseDTO.getName();
        this.age = consumerResponseDTO.getAge();
        this.email = consumerResponseDTO.getEmail();
        this.address = new AddressDTO(addressResponseDTO);
    }
}
