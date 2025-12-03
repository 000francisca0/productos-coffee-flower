package com.example.demo.config;

import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository repository;

    public DataSeeder(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Solo creamos usuarios si la base de datos está vacía
        if (repository.count() == 0) {
            System.out.println("---------- CREANDO USUARIOS (ADMIN Y CLIENTE) ----------");

            // 1. USUARIO ADMINISTRADOR
            // Este es el que usarás para ver el panel de admin en la App
            User admin = new User();
            admin.setUsername("adminUser");
            admin.setName("Administrador Principal");
            admin.setEmail("admin@coffeeflower.com"); // 🔑 Email para Login
            admin.setPassword("admin123");            // 🔑 Contraseña
            admin.setAddress("Oficina Central 1");
            admin.setRole("admin");                   // 🔑 Rol vital para la App

            // 2. USUARIO CLIENTE
            // Este es para pruebas de compra normal
            User cliente = new User();
            cliente.setUsername("clienteUser");
            cliente.setName("Cliente Frecuente");
            cliente.setEmail("cliente@gmail.com");
            cliente.setPassword("cliente123");
            cliente.setAddress("Av. Siempre Viva 123");
            cliente.setRole("cliente");

            // Guardamos la lista en la base de datos
            repository.saveAll(Arrays.asList(admin, cliente));
            
            System.out.println("✅ ¡Usuarios insertados correctamente!");
            System.out.println("   -> Admin: admin@coffeeflower.com / admin123");
            System.out.println("   -> Cliente: cliente@gmail.com / cliente123");
        }
    }
}