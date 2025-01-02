package br.com.zup.gateway.infra.clients.address.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRegisterDto {

    @NotBlank(message = "The 'street' field cannot be empty.")
    @Size(max = 100, message = "The 'street' field must have a maximum of 100 characters.")
    private String street;

    @NotBlank(message = "The 'city' field cannot be empty.")
    @Size(max = 50, message = "The 'city' field must have a maximum of 50 characters.")
    private String city;

    @NotBlank(message = "O campo 'zipCode' não pode estar vazio.")
    @Size(max = 10, message = "O campo 'zipCode' deve ter no máximo 10 caracteres.")
    private String zipCode;

    @NotBlank(message = "The 'state' field cannot be empty.")
    @Size(max = 2, message = "The 'state' field must have a maximum of 2 characters.")
    private String state;

    @NotBlank(message = "The 'consumerId' field cannot be empty.")
    @Size(max = 36, message = "The 'consumerId' field must have a maximum of 36 characters.")
    private String consumerId;

    public AddressRegisterDto() {
    }
}
