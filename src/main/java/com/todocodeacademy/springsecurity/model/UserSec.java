package com.todocodeacademy.springsecurity.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="users")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSec  {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    private boolean enabled=true;
    private boolean accountNotExpired=true;
    private boolean accountNotLocked=true;
    private boolean credentialNotExpired=true;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) //el eager me va  a cargar todos los roles
    @JoinTable (name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns=@JoinColumn(name = "role_id"))
    private Set<Role> rolesList = new HashSet<>();


}
