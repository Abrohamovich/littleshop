package org.abrohamovich.littleshop.adapter.web.auth;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.dto.auth.AuthenticationResponse;
import org.abrohamovich.littleshop.application.dto.auth.LoginCommand;
import org.abrohamovich.littleshop.application.port.in.auth.AuthenticateUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthRestController {
    private final AuthenticateUseCase authenticateUseCase;
    private final AuthWebMapper authWebMapper;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationWebResponse> login(@RequestBody LoginWebRequest request) {
        LoginCommand command = authWebMapper.toLoginCommand(request);
        AuthenticationResponse response = authenticateUseCase.authenticate(command);
        return ResponseEntity.ok(authWebMapper.toWebResponse(response));
    }
}
