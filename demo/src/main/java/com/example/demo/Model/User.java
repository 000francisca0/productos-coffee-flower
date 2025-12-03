package com.example.demo.Model;

import jakarta.persistence.*; // Import for @Table, @Entity, etc.
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_users") // <--- THIS IS THE FIX. "user" is reserved, so we use "app_users"
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;
    private String name;
    private String address;
    private String role;
}