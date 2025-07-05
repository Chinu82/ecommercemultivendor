package com.mulivendor.ecommerce.repository;

import com.mulivendor.ecommerce.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByEmail(String email);
}
//2:54:21