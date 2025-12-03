package com.example.demo.Controller;

import com.example.demo.Model.User;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios") // 👈 CAMBIO: "usuarios" para coincidir con Android
public class UserController {

    @Autowired
    private UserService userService;

    // 1. LOGIN (POST /api/usuarios/login) - ¡NUEVO!
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginData) {
        // Buscamos al usuario por email usando el servicio
        Optional<User> userOpt = userService.findByEmail(loginData.getEmail());
        
        if (userOpt.isPresent()) {
            User dbUser = userOpt.get();
            // Comparamos contraseñas (Texto plano para este ejercicio)
            if (dbUser.getPassword().equals(loginData.getPassword())) {
                return new ResponseEntity<>(dbUser, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Credenciales incorrectas", HttpStatus.UNAUTHORIZED);
    }

    // 2. REGISTRO (POST /api/usuarios/registro)
    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody User user) {
        try {
            // Verificamos si ya existe el email
            if (userService.findByEmail(user.getEmail()).isPresent()) {
                return new ResponseEntity<>("El email ya existe", HttpStatus.BAD_REQUEST);
            }
            User newUser = userService.createUser(user);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // 3. LISTAR TODOS (GET /api/usuarios)
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    // 4. BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.findUserById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 5. ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}