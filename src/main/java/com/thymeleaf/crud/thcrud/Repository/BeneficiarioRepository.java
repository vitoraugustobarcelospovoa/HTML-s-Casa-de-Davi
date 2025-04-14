package com.thymeleaf.crud.thcrud.Repository;


import com.thymeleaf.crud.thcrud.Model.BeneficiarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeneficiarioRepository extends JpaRepository<BeneficiarioModel, Long> {
}