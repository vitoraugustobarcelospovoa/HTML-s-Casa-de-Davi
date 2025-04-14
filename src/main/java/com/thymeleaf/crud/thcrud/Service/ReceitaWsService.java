package com.thymeleaf.crud.thcrud.Service;


import com.thymeleaf.crud.thcrud.dto.ReceitaWsResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReceitaWsService {

    public ReceitaWsResponse consultarCnpj(String cnpj) {
        String url = "https://www.receitaws.com.br/v1/cnpj/" + cnpj;
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, ReceitaWsResponse.class);
    }
}