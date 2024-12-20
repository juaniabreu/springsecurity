package com.todocodeacademy.springsecurity.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"username","message","jwt","status"})//en que orden se va a armar el json
public record AuthResponseDto(String username,
                              String message,
                              String jwt,
                              boolean status){
}
