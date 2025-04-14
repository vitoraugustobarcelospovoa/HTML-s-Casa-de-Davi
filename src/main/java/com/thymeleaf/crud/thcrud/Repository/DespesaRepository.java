package com.thymeleaf.crud.thcrud.Repository;

import com.thymeleaf.crud.thcrud.Model.DespesaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DespesaRepository extends JpaRepository<DespesaModel, Long> {
}
