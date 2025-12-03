package com.example.demo.Repository;

import com.example.demo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Esto crea la consulta SQL automáticamente: SELECT * FROM users WHERE email = ?
    Optional<User> findByEmail(String email);
}