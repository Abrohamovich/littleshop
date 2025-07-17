package org.abrohamovich.littleshop.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.abrohamovich.littleshop.domain.exception.user.UserValidationException;
import org.abrohamovich.littleshop.util.PasswordHasher;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode
@ToString
public class User {
    private final Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserRole role;
    private String phone;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private User(Long id, String firstName, String lastName, String email, String hashedPassword,
                 UserRole role, String phone, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = null;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = hashedPassword;
        this.role = role;
        this.phone = phone;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        validateSelf();
    }

    public static User createNewUser(String firstName, String lastName, String email,
                                     String rawPassword, UserRole role, String phone) {
        validateRawPassword(rawPassword);
        String hashedPassword = PasswordHasher.hashPassword(rawPassword);
        return new User(null, firstName, lastName, email, hashedPassword, role, phone, LocalDateTime.now(), LocalDateTime.now());
    }

    public static User withId(Long id, String firstName, String lastName, String email, String hashedPassword,
                              UserRole role, String phone, LocalDateTime createdAt, LocalDateTime updatedAt) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null for existing User");
        }
        return new User(id, firstName, lastName, email, hashedPassword, role, phone, createdAt, updatedAt);
    }

    private static void validateRawPassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }
        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters.");
        }
    }

    public void updateDetails(String firstName, String lastName, String email,
                              String newRawPassword, UserRole role, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.phone = phone;
        this.updatedAt = LocalDateTime.now();

        if (newRawPassword != null && !newRawPassword.isBlank()) {
            validateRawPassword(newRawPassword);
            this.password = PasswordHasher.hashPassword(newRawPassword);
        }

        validateSelf();
    }

    public boolean checkPassword(String rawPassword) {
        return PasswordHasher.verify(rawPassword, this.password);
    }

    private void validateSelf() {
        List<String> errors = new ArrayList<>();

        if (firstName == null || firstName.isBlank()) {
            errors.add("firstName cannot be null or empty");
        }
        if (lastName == null || lastName.isBlank()) {
            errors.add("lastName cannot be null or empty");
        }
        //todo
        if (email == null || email.isBlank()) {
            errors.add("email cannot be null or empty");
        }
        if (password == null || password.isBlank()) {
            errors.add("hashed password cannot be null or empty");
        }
        if (role == null) {
            errors.add("role cannot be null");
        }
        //todo
        if (phone == null || phone.isBlank()) {
            errors.add("phone cannot be null or empty");
        }

        if (!errors.isEmpty()) {
            String errorMessage = "User validation failed: " + String.join(", ", errors);
            throw new UserValidationException(errorMessage);
        }
    }
}
