package com.pb.authuser.models.entity;

public record TokenResponse(
    String token,
    String username,
    String name,
    Long userId
) {

}
