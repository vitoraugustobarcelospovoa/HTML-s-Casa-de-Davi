package com.thymeleaf.crud.thcrud.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Beneficiarios")
public class BeneficiarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_beneficiario;

    private String nome_Razão_Social;

    private String cnpj_cpf;

    private boolean PF_PJ;

    private String cep;

    private String endereco;

    private long numero_end;

    private String bairro;

    private String cidade;

    private String uf;

    private String telefone;

    private String email;

    private String observações;
}
