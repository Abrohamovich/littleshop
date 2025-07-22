package org.abrohamovich.littleshop.adapter.web.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.dto.PageResponse;
import org.abrohamovich.littleshop.application.dto.user.UserCreateCommand;
import org.abrohamovich.littleshop.application.dto.user.UserResponse;
import org.abrohamovich.littleshop.application.dto.user.UserUpdateCommand;
import org.abrohamovich.littleshop.application.port.in.user.CreateUserUseCase;
import org.abrohamovich.littleshop.application.port.in.user.DeleteUserUseCase;
import org.abrohamovich.littleshop.application.port.in.user.GetUserUseCase;
import org.abrohamovich.littleshop.application.port.in.user.UpdateUserUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserRestController {
    private final CreateUserUseCase createUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final UserWebMapper userWebMapper;

    @PostMapping
    public ResponseEntity<UserWebResponse> create(@Valid @RequestBody UserCreateWebRequest userCreateWebRequest) {
        UserCreateCommand userCreateCommand = userWebMapper.toCreateCommand(userCreateWebRequest);
        UserResponse userResponse = createUserUseCase.save(userCreateCommand);
        return new ResponseEntity<>(userWebMapper.toWebResponse(userResponse), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserWebResponse> findById(@PathVariable Long id) {
        UserResponse userResponse = getUserUseCase.findById(id);
        return new ResponseEntity<>(userWebMapper.toWebResponse(userResponse), HttpStatus.OK);
    }

    public ResponseEntity<PageResponse<UserWebResponse>> get(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserResponse> usersPage;

        if (firstName != null && !firstName.isBlank()) {
            usersPage = getUserUseCase.findByFirstNameLike(firstName, pageable);
        } else if (lastName != null && !lastName.isBlank()) {
            usersPage = getUserUseCase.findByLastNameLike(lastName, pageable);
        } else if (email != null && !email.isBlank()) {
            usersPage = getUserUseCase.findByEmailLike(email, pageable);
        } else {
            usersPage = getUserUseCase.findAll(pageable);
        }

        Page<UserWebResponse> webResponsePage = usersPage.map(userWebMapper::toWebResponse);
        return new ResponseEntity<>(PageResponse.fromSpringPage(webResponsePage), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserWebResponse> update(@PathVariable Long id, @Valid @RequestBody UserUpdateWebRequest userUpdateWebRequest) {
        UserUpdateCommand userUpdateCommand = userWebMapper.toUpdateCommand(userUpdateWebRequest);
        UserResponse userResponse = updateUserUseCase.update(id, userUpdateCommand);
        return new ResponseEntity<>(userWebMapper.toWebResponse(userResponse), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        deleteUserUseCase.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
