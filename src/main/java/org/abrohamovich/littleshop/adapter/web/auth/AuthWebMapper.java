package org.abrohamovich.littleshop.adapter.web.auth;

import org.abrohamovich.littleshop.application.dto.auth.AuthenticationResponse;
import org.abrohamovich.littleshop.application.dto.auth.LoginCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthWebMapper {
    LoginCommand toLoginCommand(LoginWebRequest request);

    AuthenticationWebResponse toWebResponse(AuthenticationResponse response);
}
