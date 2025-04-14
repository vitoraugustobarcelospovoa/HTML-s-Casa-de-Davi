package com.thymeleaf.crud.thcrud.Repository;


import com.thymeleaf.crud.thcrud.Model.BeneficiarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeneficiarioRepository extends JpaRepository<BeneficiarioModel, Long> {
    @Query("SELECT b FROM BeneficiarioModel b WHERE LOWER(b.cnpj_cpf) LIKE LOWER(CONCAT('%', :cnpjCpf, '%')) AND LOWER(b.nomeRazaoSocial) LIKE LOWER(CONCAT('%', :nomeRazaoSocial, '%'))")
    List<BeneficiarioModel> buscarPorFiltro(@Param("cnpjCpf") String cnpjcpf, @Param("nomeRazaoSocial") String nomeRazaoSocial);
}