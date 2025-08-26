package org.abrohamovich.littleshop.application.dto.auth;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public record LoginCommand(String email, String password) {
}
