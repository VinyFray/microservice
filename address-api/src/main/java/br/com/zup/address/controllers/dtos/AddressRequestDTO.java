package br.com.zup.address.controllers.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;

@Getter
@Setter
public class AddressRequestDTO {

    @NotBlank(message = "The field cannot be empty.")
    @Size(max = 100, message = "The 'street' field must have a maximum of 100 characters.")
    private String street;

    @NotBlank(message = "The field cannot be empty.")
    @Size(max = 50, message = "The 'city' field must have a maximum of 50 characters.")
    private String city;

    @NotBlank(message = "The field cannot be empty.")
    @Size(max = 10, message = "The 'zipCode' field must have a maximum of 10 characters.")
    private String zipCode;

    @NotBlank(message = "The field cannot be empty.")
    @Size(max = 2, message = "The 'state' field must have a maximum of 2 characters.")
    private String state;

    @NotBlank(message = "The field cannot be empty.")
    @Size (max = 36, message = "The 'consumerId' field must have a maximum of 36 characters.")
    private String consumerId;

    public AddressRequestDTO() {
    }

}
