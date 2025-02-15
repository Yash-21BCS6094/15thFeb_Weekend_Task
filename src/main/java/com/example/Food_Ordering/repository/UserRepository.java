package com.example.Food_Ordering.repository;

import com.example.Food_Ordering.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findByFirstNameContainingIgnoreCase(String firstName);
    Optional<User> findByEmail(String email);
}
