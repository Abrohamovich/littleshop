package org.abrohamovich.littleshop.adapter.web.user;

import org.abrohamovich.littleshop.application.dto.user.UserCreateCommand;
import org.abrohamovich.littleshop.application.dto.user.UserResponse;
import org.abrohamovich.littleshop.application.dto.user.UserUpdateCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserWebMapper {
    UserCreateCommand toCreateCommand(UserCreateWebRequest request);

    UserUpdateCommand toUpdateCommand(UserUpdateWebRequest request);

    UserWebResponse toWebResponse(UserResponse response);
}
