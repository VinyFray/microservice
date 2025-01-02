package br.com.zup.consumer.controllers.dtos;

import br.com.zup.consumer.models.Consumer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConsumerResponseDTO {

    @NotBlank(message = "Not blank")
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

    public static ConsumerResponseDTO fromEntity(Consumer consumer) {
        ConsumerResponseDTO dto = new ConsumerResponseDTO();
        dto.setId(consumer.getId());
        dto.setName(consumer.getName());
        dto.setAge(consumer.getAge());
        dto.setEmail(consumer.getEmail());
        return dto;
    }
}