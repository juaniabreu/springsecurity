package com.todocodeacademy.springsecurity.controller;


import com.todocodeacademy.springsecurity.model.Role;
import com.todocodeacademy.springsecurity.model.UserSec;
import com.todocodeacademy.springsecurity.service.IRoleService;
import com.todocodeacademy.springsecurity.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<String> who() {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            System.out.println(auth.getPrincipal());
            System.out.println(auth.getName());
            UserSec user = (UserSec) auth.getPrincipal();
            System.out.println(user.getUsername());
            return new ResponseEntity<>("username", HttpStatus.OK);
    }
}