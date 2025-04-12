package com.thymeleaf.crud.thcrud.Repository;

import com.thymeleaf.crud.thcrud.Model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}

