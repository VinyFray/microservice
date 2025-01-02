package br.com.zup.consumer.controllers.dtos;

import br.com.zup.consumer.models.Consumer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsumerRequestDTO {

    @NotBlank(message = "The name cannot be blank")
    @Size(min = 3, max = 20)
    private String name;

    @NotBlank(message = "Age cannot be blank")
    @Size(max = 3, message = "Age must be a valid number and less than 130")
    private String age;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    public ConsumerRequestDTO(String name, String age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public Consumer toEntity() {
        return new Consumer(
                null,
                this.name,
                this.age,
                this.email
        );
    }
}