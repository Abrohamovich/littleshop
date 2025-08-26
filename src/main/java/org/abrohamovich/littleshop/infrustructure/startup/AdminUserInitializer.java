package org.abrohamovich.littleshop.infrustructure.startup;

import lombok.RequiredArgsConstructor;
import org.abrohamovich.littleshop.application.dto.user.UserCreateCommand;
import org.abrohamovich.littleshop.application.port.in.user.CreateUserUseCase;
import org.abrohamovich.littleshop.application.port.in.user.GetUserUseCase;
import org.abrohamovich.littleshop.domain.model.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminUserInitializer implements CommandLineRunner {
    private final CreateUserUseCase createUserUseCase;
    private final GetUserUseCase getUserUseCase;

    @Value("${app.admin.firstName:Admin}")
    private String firstname;
    @Value("${app.admin.lastName:User}")
    private String lastname;
    @Value("${app.admin.email:admin.user@examole.com}")
    private String email;
    @Value("${app.admin.phone:+1394022933}")
    private String phone;
    @Value("${app.admin.password:admin12345}")
    private String password;

    @Override
    public void run(String... args) throws Exception {
        UserCreateCommand command = new UserCreateCommand(
                firstname,
                lastname,
                email,
                password,
                UserRole.ADMIN,
                phone);

        Pageable pageable = PageRequest.of(0, 100);

        if (getUserUseCase.findAll(pageable).isEmpty()) {
            createUserUseCase.save(command);
        }
    }
}
