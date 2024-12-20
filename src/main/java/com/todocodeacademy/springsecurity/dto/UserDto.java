package com.todocodeacademy.springsecurity.dto;

import jakarta.validation.constraints.NotBlank;

public record UserDto (@NotBlank String username, @NotBlank String password) {
}
