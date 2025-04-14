package com.thymeleaf.crud.thcrud.dto;

import lombok.Data;
import java.util.List;

@Data
public class ReceitaWsResponse {
    private String nome;
    private String fantasia;
    private String logradouro;
    private String numero;
    private String bairro;
    private String municipio;
    private String uf;
    private String cep;
    private String telefone;
    private String email;
    private String cnpj;

    private List<Atividade> atividade_principal;

    @Data
    public static class Atividade {
        private String code;
        private String text;
    }
}