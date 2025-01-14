package br.com.zup.gateway.infra.clients.consumer;

import br.com.zup.gateway.infra.clients.consumer.dtos.ConsumerRegisterDTO;
import br.com.zup.gateway.infra.clients.consumer.dtos.ConsumerResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ConsumerClient {

    @Autowired
    private WebClient webClient;
    private final String URL_BASE = "http://loclahos:8080";


    public ConsumerResponseDTO registerConsumerClient(ConsumerRegisterDTO registerDTO){
        return webClient
                .post()
                .uri(URL_BASE)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(registerDTO)
                .retrieve()
                .bodyToMono(ConsumerResponseDTO.class)
                .block();
    }


    public ConsumerResponseDTO getConsumer(String consumerId){
        return webClient
                .get()
                .uri(URL_BASE+"/"+consumerId)
                .retrieve()
                .bodyToMono(ConsumerResponseDTO.class)
                .block();
    }
}
