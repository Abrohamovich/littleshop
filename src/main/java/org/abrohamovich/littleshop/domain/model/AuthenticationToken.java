package org.abrohamovich.littleshop.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
public class AuthenticationToken {
    private final String token;
    private final String tokenType;
    private final LocalDateTime expiresAt;
    private final Long userId;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
}
