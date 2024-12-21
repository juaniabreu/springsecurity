package com.todocodeacademy.springsecurity.controller;

import com.todocodeacademy.springsecurity.dto.UserDto;
import com.todocodeacademy.springsecurity.model.Permission;
import com.todocodeacademy.springsecurity.model.Role;
import com.todocodeacademy.springsecurity.model.UserSec;
import com.todocodeacademy.springsecurity.service.IRoleService;
import com.todocodeacademy.springsecurity.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @GetMapping
    public ResponseEntity<List<UserSec>> getAllUsers() {
        List<UserSec> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserSec> getUserById(@PathVariable Long id) {
        Optional<UserSec> user = userService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserSec> createUser(@RequestBody UserSec userSec) {

        Set<Role> roleList = new HashSet<Role>();
        Role readRole;

        //encriptamos contraseña
        userSec.setPassword(userService.encriptPassword(userSec.getPassword()));

        // Recuperar la Permission/s por su ID
        for (Role role : userSec.getRolesList()) {
            readRole = roleService.findById(role.getId()).orElse(null);
            if (readRole != null) {
                //si encuentro, guardo en la lista
                roleList.add(readRole);
            }
        }

        if (!roleList.isEmpty()) {
            userSec.setRolesList(roleList);

            UserSec newUser = userService.save(userSec);
            return ResponseEntity.ok(newUser);
        }
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UserSec> deleteUser(@PathVariable Long id) {
        UserSec user = userService.findById(id).get();
        user.setEnabled(false);
        userService.save(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/alta/{id}")
    public ResponseEntity<UserSec> updateUser(@PathVariable Long id) {
        UserSec user = userService.findById(id).get();
        user.setEnabled(true);
        userService.save(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/buscar")
    public ResponseEntity<UserSec> buscarUser(@RequestBody String username) {
        System.out.println(username);
        Optional<UserSec> user = userService.findEntityByUsername(username);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/who")
    public ResponseEntity<Map<String, String>> who() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        String username;

        // Extraer el username correctamente
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();  // Aquí obtenemos el username correctamente
        } else {
            username = authentication.getName();  // Fallback por si no es UserDetails
        }

        Map<String, String> response = new HashMap<>();
        response.put("username", username);  // Devolver solo el username

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}