package com.example.demo.Repositories;

import com.example.demo.DTO.UserDTO;
import com.example.demo.Entiti.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findAllByEmail(String email);
    Users findById(int id);
    Users findByEmail(String email);
}
