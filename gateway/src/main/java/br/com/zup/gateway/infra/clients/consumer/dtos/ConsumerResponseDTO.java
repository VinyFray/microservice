package br.com.zup.gateway.infra.clients.consumer.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsumerResponseDTO {

    @NotBlank(message = "The ID cannot be blank.")
    private String id;

    @NotBlank(message = "The name cannot be blank.")
    @Size(min = 3, max = 20, message = "The name must be between 3 and 20 characters.")
    private String name;

    @NotNull(message = "Age cannot be null.")
    @Positive(message = "Age must be a positive number.")
    private String age;

    @NotBlank(message = "Email cannot be blank.")
    @Email(message = "The email format is invalid.")
    private String email;


    public ConsumerResponseDTO() {
    }

    public ConsumerResponseDTO(String id, String name, String age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }
}