package com.thymeleaf.crud.thcrud.Repository;

import com.thymeleaf.crud.thcrud.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {

}
