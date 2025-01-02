package br.com.zup.address.controllers.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AddressResponseDTO {

    @NotBlank(message = "Not Blank")
    private String id;

    @NotBlank(message = "The 'street' field cannot be empty.")
    @Size(max = 100, message = "The 'street' field must have a maximum of 100 characters.")
    private String street;

    @NotBlank(message = "The 'city' field cannot be empty.")
    @Size(max = 50, message = "The 'city' field must have a maximum of 50 characters.")
    private String city;

    @NotBlank(message = "The 'zipCode' field cannot be empty.")
    @Size(max = 10, message = "The 'zipCode' field must have a maximum of 10 characters.")
    private String zipCode;

    @NotBlank(message = "The 'state' field cannot be empty.")
    @Size(max = 2, message = "The 'state' field must have a maximum of 2 characters.")
    private String state;

    @NotBlank(message = "The 'consumerId' field cannot be empty.")
    @Size(max = 36, message = "The 'consumerId' field must have a maximum of 36 characters.")
    private String consumerId;

    public AddressResponseDTO() {

    }
}

