package com.thymeleaf.crud.thcrud.Repository;

import com.thymeleaf.crud.thcrud.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByUsername(String username);
}
