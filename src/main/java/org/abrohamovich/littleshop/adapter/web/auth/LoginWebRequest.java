package org.abrohamovich.littleshop.adapter.web.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginWebRequest {
    private String email;
    private String password;
}
