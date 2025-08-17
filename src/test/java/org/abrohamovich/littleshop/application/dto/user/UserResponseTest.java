package org.abrohamovich.littleshop.application.dto.user;

import org.abrohamovich.littleshop.domain.model.User;
import org.abrohamovich.littleshop.domain.model.UserRole;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class UserResponseTest {
    @Test
    void toResponse_WithValidData_ShouldReturnUserResponse() {
        Long id = 1L;
        String firstName = "FirstName";
        String lastName = "LastName";
        String email = "Email";
        String password = "OIHF98w3t43g";
        UserRole role = UserRole.WORKER;
        String phone = "+1 50-2304-49-44";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        User user = User.withId(id, firstName, lastName, email,
                password, role, phone, createdAt, updatedAt);

        UserResponse userResponse = UserResponse.toResponse(user);

        assertNotNull(userResponse);
        assertEquals(user.getId(), userResponse.getId());
        assertEquals(firstName, userResponse.getFirstName());
        assertEquals(lastName, userResponse.getLastName());
        assertEquals(email, userResponse.getEmail());
        assertEquals(role, userResponse.getRole());
        assertEquals(phone, userResponse.getPhone());
    }

    @Test
    void toResponse_WithNullUser_ShouldThrowException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> UserResponse.toResponse(null));

        assertTrue(ex.getMessage().startsWith("User cannot be null to continue the conversion."));
    }
}
