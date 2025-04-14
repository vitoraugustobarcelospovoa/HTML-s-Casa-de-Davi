package com.thymeleaf.crud.thcrud.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "despesas")
public class DespesaModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "id_usuario", nullable = false)
  private UserModel usuario;

  @Column(name = "id_beneficiario")
  private Long idBeneficiario;

  private LocalDate data;
  private BigDecimal valor;
  private LocalDate dataVencimento;
  private Integer parcelas;

  private String formaPagamento;
  private String categoria;
  private String situacao;
  private String descricao;
  private String statusAtividade;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "document_id")
  private Document documento;

}
