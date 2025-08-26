package com.FemCoders.DropTheLink.dtos.user;

public record LoginRequest(
        String username,
        String password
) { }