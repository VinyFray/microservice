package br.com.zup.gateway.infra.clients.address;

import br.com.zup.gateway.infra.clients.address.dtos.AddressRegisterDto;
import br.com.zup.gateway.infra.clients.address.dtos.AddressResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AddressClient {

    @Autowired
    private WebClient webClient;
    private final String URL_BASE = "http://localhost:8082/address";

    public AddressResponseDTO registeAddress(AddressRegisterDto addressRegisterDto){
        return webClient.post()
                .uri(URL_BASE)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(addressRegisterDto)
                .retrieve()
                .bodyToMono(AddressResponseDTO.class)
                .block();
    }
}
