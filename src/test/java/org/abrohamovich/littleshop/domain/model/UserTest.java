package org.abrohamovich.littleshop.domain.model;

import org.abrohamovich.littleshop.domain.exception.user.UserValidationException;
import org.abrohamovich.littleshop.util.PasswordHasher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String rawPassword;
    private UserRole role;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String hashedPassword;

    @AfterEach
    void tearDown() {
        id = null;
        firstName = null;
        lastName = null;
        email = null;
        rawPassword = null;
        role = null;
        phone = null;
        createdAt = null;
        updatedAt = null;
        hashedPassword = null;
    }

//  createNewUser()

    @Test
    void createNewUser_WithValidData_ShouldReturnUser() {
        firstName = "John";
        lastName = "Doe";
        email = "john.doe@example.com";
        rawPassword = "password123";
        role = UserRole.WORKER;
        phone = "123-456-7890";

        User user = User.createNewUser(firstName, lastName, email, rawPassword, role, phone);

        assertNotNull(user);
        assertNull(user.getId());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(email, user.getEmail());
        assertEquals(role, user.getRole());
        assertEquals(phone, user.getPhone());
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());
        assertNotEquals(rawPassword, user.getPassword());
        assertTrue(user.checkPassword(rawPassword));
    }

    @Test
    void createNewUser_WithInvalidRawPassword_ShouldThrowException() {
        firstName = "John";
        lastName = "Doe";
        email = "john.doe@example.com";
        rawPassword = "short";
        role = UserRole.WORKER;
        phone = "123-456-7890";

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            User.createNewUser(firstName, lastName, email, rawPassword, role, phone);
        });

        assertTrue(ex.getMessage().contains("Password must be at least 8 characters."));
    }

    @Test
    void createNewUser_WithNullFirstName_ShouldThrowException() {
        firstName = null;
        lastName = "Doe";
        email = "john.doe@example.com";
        rawPassword = "password123";
        role = UserRole.WORKER;
        phone = "123-456-7890";

        UserValidationException ex = assertThrows(UserValidationException.class, () -> {
            User.createNewUser(firstName, lastName, email, rawPassword, role, phone);
        });

        assertTrue(ex.getMessage().startsWith("User validation failed:"));
        assertTrue(ex.getMessage().contains("firstName cannot be null or empty"));
    }

    @Test
    void createNewUser_WithBlankFirstName_ShouldThrowException() {
        firstName = "   ";
        lastName = "Doe";
        email = "john.doe@example.com";
        rawPassword = "password123";
        role = UserRole.WORKER;
        phone = "123-456-7890";

        UserValidationException ex = assertThrows(UserValidationException.class, () -> {
            User.createNewUser(firstName, lastName, email, rawPassword, role, phone);
        });

        assertTrue(ex.getMessage().startsWith("User validation failed:"));
        assertTrue(ex.getMessage().contains("firstName cannot be null or empty"));
    }

    @Test
    void createNewUser_WithNullLastName_ShouldThrowException() {
        firstName = "John";
        lastName = null;
        email = "john.doe@example.com";
        rawPassword = "password123";
        role = UserRole.WORKER;
        phone = "123-456-7890";

        UserValidationException ex = assertThrows(UserValidationException.class, () -> {
            User.createNewUser(firstName, lastName, email, rawPassword, role, phone);
        });

        assertTrue(ex.getMessage().startsWith("User validation failed:"));
        assertTrue(ex.getMessage().contains("lastName cannot be null or empty"));
    }

    @Test
    void createNewUser_WithBlankLastName_ShouldThrowException() {
        firstName = "John";
        lastName = "   ";
        email = "john.doe@example.com";
        rawPassword = "password123";
        role = UserRole.WORKER;
        phone = "123-456-7890";

        UserValidationException ex = assertThrows(UserValidationException.class, () -> {
            User.createNewUser(firstName, lastName, email, rawPassword, role, phone);
        });

        assertTrue(ex.getMessage().startsWith("User validation failed:"));
        assertTrue(ex.getMessage().contains("lastName cannot be null or empty"));
    }

    @Test
    void createNewUser_WithNullEmail_ShouldThrowException() {
        firstName = "John";
        lastName = "Doe";
        email = null;
        rawPassword = "password123";
        role = UserRole.WORKER;
        phone = "123-456-7890";

        UserValidationException ex = assertThrows(UserValidationException.class, () -> {
            User.createNewUser(firstName, lastName, email, rawPassword, role, phone);
        });

        assertTrue(ex.getMessage().startsWith("User validation failed:"));
        assertTrue(ex.getMessage().contains("email cannot be null or empty"));
    }

    @Test
    void createNewUser_WithBlankEmail_ShouldThrowException() {
        firstName = "John";
        lastName = "Doe";
        email = "   ";
        rawPassword = "password123";
        role = UserRole.WORKER;
        phone = "123-456-7890";

        UserValidationException ex = assertThrows(UserValidationException.class, () -> {
            User.createNewUser(firstName, lastName, email, rawPassword, role, phone);
        });

        assertTrue(ex.getMessage().startsWith("User validation failed:"));
        assertTrue(ex.getMessage().contains("email cannot be null or empty"));
    }

    @Test
    void createNewUser_WithNullRole_ShouldThrowException() {
        firstName = "John";
        lastName = "Doe";
        email = "john.doe@example.com";
        rawPassword = "password123";
        role = null;
        phone = "123-456-7890";

        UserValidationException ex = assertThrows(UserValidationException.class, () -> {
            User.createNewUser(firstName, lastName, email, rawPassword, role, phone);
        });

        assertTrue(ex.getMessage().startsWith("User validation failed:"));
        assertTrue(ex.getMessage().contains("role cannot be null"));
    }

    @Test
    void createNewUser_WithNullPhone_ShouldThrowException() {
        firstName = "John";
        lastName = "Doe";
        email = "john.doe@example.com";
        rawPassword = "password123";
        role = UserRole.WORKER;
        phone = null;

        UserValidationException ex = assertThrows(UserValidationException.class, () -> {
            User.createNewUser(firstName, lastName, email, rawPassword, role, phone);
        });

        assertTrue(ex.getMessage().startsWith("User validation failed:"));
        assertTrue(ex.getMessage().contains("phone cannot be null or empty"));
    }

    @Test
    void createNewUser_WithBlankPhone_ShouldThrowException() {
        firstName = "John";
        lastName = "Doe";
        email = "john.doe@example.com";
        rawPassword = "password123";
        role = UserRole.WORKER;
        phone = "   ";

        UserValidationException ex = assertThrows(UserValidationException.class, () -> {
            User.createNewUser(firstName, lastName, email, rawPassword, role, phone);
        });

        assertTrue(ex.getMessage().startsWith("User validation failed:"));
        assertTrue(ex.getMessage().contains("phone cannot be null or empty"));
    }

//  withId()

    @Test
    void withId_WithValidData_ShouldReturnUser() {
        id = 1L;
        firstName = "Jane";
        lastName = "Smith";
        email = "jane.smith@example.com";
        hashedPassword = PasswordHasher.hashPassword("password123");
        role = UserRole.ADMIN;
        phone = "098-765-4321";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        User user = User.withId(id, firstName, lastName, email, hashedPassword, role, phone, createdAt, updatedAt);

        assertNotNull(user);
        assertEquals(id, user.getId());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(email, user.getEmail());
        assertEquals(hashedPassword, user.getPassword());
        assertEquals(role, user.getRole());
        assertEquals(phone, user.getPhone());
        assertEquals(createdAt, user.getCreatedAt());
        assertEquals(updatedAt, user.getUpdatedAt());
    }

    @Test
    void withId_WithNullId_ShouldThrowException() {
        id = null;
        firstName = "Jane";
        lastName = "Smith";
        email = "jane.smith@example.com";
        hashedPassword = PasswordHasher.hashPassword("password123");
        role = UserRole.ADMIN;
        phone = "098-765-4321";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            User.withId(id, firstName, lastName, email, hashedPassword, role, phone, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("ID cannot be null for existing User"));
    }

    @Test
    void withId_WithNullFirstName_ShouldThrowException() {
        id = 1L;
        firstName = null;
        lastName = "Smith";
        email = "jane.smith@example.com";
        hashedPassword = PasswordHasher.hashPassword("password123");
        role = UserRole.ADMIN;
        phone = "098-765-4321";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        UserValidationException ex = assertThrows(UserValidationException.class, () -> {
            User.withId(id, firstName, lastName, email, hashedPassword, role, phone, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("User validation failed:"));
        assertTrue(ex.getMessage().contains("firstName cannot be null or empty"));
    }

    @Test
    void withId_WithBlankFirstName_ShouldThrowException() {
        id = 1L;
        firstName = "   ";
        lastName = "Smith";
        email = "jane.smith@example.com";
        hashedPassword = PasswordHasher.hashPassword("password123");
        role = UserRole.ADMIN;
        phone = "098-765-4321";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        UserValidationException ex = assertThrows(UserValidationException.class, () -> {
            User.withId(id, firstName, lastName, email, hashedPassword, role, phone, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("User validation failed:"));
        assertTrue(ex.getMessage().contains("firstName cannot be null or empty"));
    }

    @Test
    void withId_WithNullLastName_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        lastName = null;
        email = "jane.smith@example.com";
        hashedPassword = PasswordHasher.hashPassword("password123");
        role = UserRole.ADMIN;
        phone = "098-765-4321";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        UserValidationException ex = assertThrows(UserValidationException.class, () -> {
            User.withId(id, firstName, lastName, email, hashedPassword, role, phone, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("User validation failed:"));
        assertTrue(ex.getMessage().contains("lastName cannot be null or empty"));
    }

    @Test
    void withId_WithBlankLastName_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        lastName = "   ";
        email = "jane.smith@example.com";
        hashedPassword = PasswordHasher.hashPassword("password123");
        role = UserRole.ADMIN;
        phone = "098-765-4321";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        UserValidationException ex = assertThrows(UserValidationException.class, () -> {
            User.withId(id, firstName, lastName, email, hashedPassword, role, phone, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("User validation failed:"));
        assertTrue(ex.getMessage().contains("lastName cannot be null or empty"));
    }

    @Test
    void withId_WithNullEmail_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        lastName = "Smith";
        email = null;
        hashedPassword = PasswordHasher.hashPassword("password123");
        role = UserRole.ADMIN;
        phone = "098-765-4321";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        UserValidationException ex = assertThrows(UserValidationException.class, () -> {
            User.withId(id, firstName, lastName, email, hashedPassword, role, phone, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("User validation failed:"));
        assertTrue(ex.getMessage().contains("email cannot be null or empty"));
    }

    @Test
    void withId_WithBlankEmail_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        lastName = "Smith";
        email = "   ";
        hashedPassword = PasswordHasher.hashPassword("password123");
        role = UserRole.ADMIN;
        phone = "098-765-4321";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        UserValidationException ex = assertThrows(UserValidationException.class, () -> {
            User.withId(id, firstName, lastName, email, hashedPassword, role, phone, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("User validation failed:"));
        assertTrue(ex.getMessage().contains("email cannot be null or empty"));
    }

    @Test
    void withId_WithNullHashedPassword_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        lastName = "Smith";
        email = "jane.smith@example.com";
        hashedPassword = null;
        role = UserRole.ADMIN;
        phone = "098-765-4321";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        UserValidationException ex = assertThrows(UserValidationException.class, () -> {
            User.withId(id, firstName, lastName, email, hashedPassword, role, phone, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("User validation failed:"));
        assertTrue(ex.getMessage().contains("hashed password cannot be null or empty"));
    }

    @Test
    void withId_WithBlankHashedPassword_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        lastName = "Smith";
        email = "jane.smith@example.com";
        hashedPassword = "   ";
        role = UserRole.ADMIN;
        phone = "098-765-4321";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        UserValidationException ex = assertThrows(UserValidationException.class, () -> {
            User.withId(id, firstName, lastName, email, hashedPassword, role, phone, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("User validation failed:"));
        assertTrue(ex.getMessage().contains("hashed password cannot be null or empty"));
    }

    @Test
    void withId_WithNullRole_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        lastName = "Smith";
        email = "jane.smith@example.com";
        hashedPassword = PasswordHasher.hashPassword("password123");
        role = null;
        phone = "098-765-4321";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        UserValidationException ex = assertThrows(UserValidationException.class, () -> {
            User.withId(id, firstName, lastName, email, hashedPassword, role, phone, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("User validation failed:"));
        assertTrue(ex.getMessage().contains("role cannot be null"));
    }

    @Test
    void withId_WithNullPhone_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        lastName = "Smith";
        email = "jane.smith@example.com";
        hashedPassword = PasswordHasher.hashPassword("password123");
        role = UserRole.ADMIN;
        phone = null;
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        UserValidationException ex = assertThrows(UserValidationException.class, () -> {
            User.withId(id, firstName, lastName, email, hashedPassword, role, phone, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("User validation failed:"));
        assertTrue(ex.getMessage().contains("phone cannot be null or empty"));
    }

    @Test
    void withId_WithBlankPhone_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        lastName = "Smith";
        email = "jane.smith@example.com";
        hashedPassword = PasswordHasher.hashPassword("password123");
        role = UserRole.ADMIN;
        phone = "   ";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);

        UserValidationException ex = assertThrows(UserValidationException.class, () -> {
            User.withId(id, firstName, lastName, email, hashedPassword, role, phone, createdAt, updatedAt);
        });

        assertTrue(ex.getMessage().startsWith("User validation failed:"));
        assertTrue(ex.getMessage().contains("phone cannot be null or empty"));
    }

//  updateDetails()

    @Test
    void updateDetails_WithValidData_ShouldUpdateUser() {
        id = 1L;
        firstName = "Jane";
        String newFirstName = "Janet";
        lastName = "Smith";
        String newLastName = "Jones";
        email = "jane.smith@example.com";
        String newEmail = "janet.jones@example.com";
        rawPassword = "oldpassword123";
        role = UserRole.ADMIN;
        UserRole newRole = UserRole.WORKER;
        phone = "098-765-4321";
        String newPhone = "000-000-0000";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);
        String hashedPassword = PasswordHasher.hashPassword(rawPassword);

        User user = User.withId(id, firstName, lastName, email, hashedPassword, role, phone, createdAt, updatedAt);
        String initialHashedPassword = user.getPassword();

        user.updateDetails(newFirstName, newLastName, newEmail, null, newRole, newPhone);

        assertNotNull(user);
        assertEquals(newFirstName, user.getFirstName());
        assertEquals(newLastName, user.getLastName());
        assertEquals(newEmail, user.getEmail());
        assertEquals(initialHashedPassword, user.getPassword());
        assertEquals(newRole, user.getRole());
        assertEquals(newPhone, user.getPhone());
        assertEquals(createdAt, user.getCreatedAt());
        assertNotEquals(updatedAt, user.getUpdatedAt());
    }

    @Test
    void updateDetails_WithValidNewPassword_ShouldUpdateUserPassword() {
        id = 1L;
        firstName = "Jane";
        lastName = "Smith";
        email = "jane.smith@example.com";
        rawPassword = "oldpassword123";
        String newRawPassword = "newpassword123";
        role = UserRole.ADMIN;
        phone = "098-765-4321";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);
        String hashedPassword = PasswordHasher.hashPassword(rawPassword);

        User user = User.withId(id, firstName, lastName, email, hashedPassword, role, phone, createdAt, updatedAt);

        user.updateDetails(firstName, lastName, email, newRawPassword, role, phone);

        assertNotEquals(hashedPassword, user.getPassword());
        assertTrue(user.checkPassword(newRawPassword));
    }

    @Test
    void updateDetails_WithBlankNewPassword_ShouldNotUpdatePassword() {
        id = 1L;
        firstName = "Jane";
        lastName = "Smith";
        email = "jane.smith@example.com";
        rawPassword = "oldpassword123";
        String newRawPassword = "   ";
        role = UserRole.ADMIN;
        phone = "098-765-4321";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);
        String hashedPassword = PasswordHasher.hashPassword(rawPassword);

        User user = User.withId(id, firstName, lastName, email, hashedPassword, role, phone, createdAt, updatedAt);
        String initialHashedPassword = user.getPassword();

        user.updateDetails(firstName, lastName, email, newRawPassword, role, phone);

        assertEquals(initialHashedPassword, user.getPassword());
        assertTrue(user.checkPassword(rawPassword));
    }

    @Test
    void updateDetails_WithInvalidNewPassword_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        lastName = "Smith";
        email = "jane.smith@example.com";
        rawPassword = "password123";
        String newRawPassword = "short";
        role = UserRole.ADMIN;
        phone = "098-765-4321";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);
        String hashedPassword = PasswordHasher.hashPassword(rawPassword);

        User user = User.withId(id, firstName, lastName, email, hashedPassword, role, phone, createdAt, updatedAt);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            user.updateDetails(firstName, lastName, email, newRawPassword, role, phone);
        });

        assertTrue(ex.getMessage().contains("Password must be at least 8 characters."));
    }

    @Test
    void updateDetails_WithNullFirstName_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        lastName = "Smith";
        email = "jane.smith@example.com";
        rawPassword = "password123";
        role = UserRole.ADMIN;
        phone = "098-765-4321";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);
        String hashedPassword = PasswordHasher.hashPassword(rawPassword);

        User user = User.withId(id, firstName, lastName, email, hashedPassword, role, phone, createdAt, updatedAt);

        UserValidationException ex = assertThrows(UserValidationException.class, () -> {
            user.updateDetails(null, lastName, email, null, role, phone);
        });

        assertTrue(ex.getMessage().startsWith("User validation failed:"));
        assertTrue(ex.getMessage().contains("firstName cannot be null or empty"));
    }

    @Test
    void updateDetails_WithBlankFirstName_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        lastName = "Smith";
        email = "jane.smith@example.com";
        rawPassword = "password123";
        role = UserRole.ADMIN;
        phone = "098-765-4321";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);
        String hashedPassword = PasswordHasher.hashPassword(rawPassword);

        User user = User.withId(id, firstName, lastName, email, hashedPassword, role, phone, createdAt, updatedAt);

        UserValidationException ex = assertThrows(UserValidationException.class, () -> {
            user.updateDetails("   ", lastName, email, null, role, phone);
        });

        assertTrue(ex.getMessage().startsWith("User validation failed:"));
        assertTrue(ex.getMessage().contains("firstName cannot be null or empty"));
    }

    @Test
    void updateDetails_WithNullLastName_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        lastName = "Smith";
        email = "jane.smith@example.com";
        rawPassword = "password123";
        role = UserRole.ADMIN;
        phone = "098-765-4321";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);
        String hashedPassword = PasswordHasher.hashPassword(rawPassword);

        User user = User.withId(id, firstName, lastName, email, hashedPassword, role, phone, createdAt, updatedAt);

        UserValidationException ex = assertThrows(UserValidationException.class, () -> {
            user.updateDetails(firstName, null, email, null, role, phone);
        });

        assertTrue(ex.getMessage().startsWith("User validation failed:"));
        assertTrue(ex.getMessage().contains("lastName cannot be null or empty"));
    }

    @Test
    void updateDetails_WithBlankLastName_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        lastName = "Smith";
        email = "jane.smith@example.com";
        rawPassword = "password123";
        role = UserRole.ADMIN;
        phone = "098-765-4321";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);
        String hashedPassword = PasswordHasher.hashPassword(rawPassword);

        User user = User.withId(id, firstName, lastName, email, hashedPassword, role, phone, createdAt, updatedAt);

        UserValidationException ex = assertThrows(UserValidationException.class, () -> {
            user.updateDetails(firstName, "   ", email, null, role, phone);
        });

        assertTrue(ex.getMessage().startsWith("User validation failed:"));
        assertTrue(ex.getMessage().contains("lastName cannot be null or empty"));
    }

    @Test
    void updateDetails_WithNullEmail_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        lastName = "Smith";
        email = "jane.smith@example.com";
        rawPassword = "password123";
        role = UserRole.ADMIN;
        phone = "098-765-4321";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);
        String hashedPassword = PasswordHasher.hashPassword(rawPassword);

        User user = User.withId(id, firstName, lastName, email, hashedPassword, role, phone, createdAt, updatedAt);

        UserValidationException ex = assertThrows(UserValidationException.class, () -> {
            user.updateDetails(firstName, lastName, null, null, role, phone);
        });

        assertTrue(ex.getMessage().startsWith("User validation failed:"));
        assertTrue(ex.getMessage().contains("email cannot be null or empty"));
    }

    @Test
    void updateDetails_WithBlankEmail_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        lastName = "Smith";
        email = "jane.smith@example.com";
        rawPassword = "password123";
        role = UserRole.ADMIN;
        phone = "098-765-4321";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);
        String hashedPassword = PasswordHasher.hashPassword(rawPassword);

        User user = User.withId(id, firstName, lastName, email, hashedPassword, role, phone, createdAt, updatedAt);

        UserValidationException ex = assertThrows(UserValidationException.class, () -> {
            user.updateDetails(firstName, lastName, "   ", null, role, phone);
        });

        assertTrue(ex.getMessage().startsWith("User validation failed:"));
        assertTrue(ex.getMessage().contains("email cannot be null or empty"));
    }

    @Test
    void updateDetails_WithNullRole_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        lastName = "Smith";
        email = "jane.smith@example.com";
        rawPassword = "password123";
        role = UserRole.ADMIN;
        phone = "098-765-4321";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);
        String hashedPassword = PasswordHasher.hashPassword(rawPassword);

        User user = User.withId(id, firstName, lastName, email, hashedPassword, role, phone, createdAt, updatedAt);

        UserValidationException ex = assertThrows(UserValidationException.class, () -> {
            user.updateDetails(firstName, lastName, email, null, null, phone);
        });

        assertTrue(ex.getMessage().startsWith("User validation failed:"));
        assertTrue(ex.getMessage().contains("role cannot be null"));
    }

    @Test
    void updateDetails_WithNullPhone_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        lastName = "Smith";
        email = "jane.smith@example.com";
        rawPassword = "password123";
        role = UserRole.ADMIN;
        phone = "098-765-4321";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);
        String hashedPassword = PasswordHasher.hashPassword(rawPassword);

        User user = User.withId(id, firstName, lastName, email, hashedPassword, role, phone, createdAt, updatedAt);

        UserValidationException ex = assertThrows(UserValidationException.class, () -> {
            user.updateDetails(firstName, lastName, email, null, role, null);
        });

        assertTrue(ex.getMessage().startsWith("User validation failed:"));
        assertTrue(ex.getMessage().contains("phone cannot be null or empty"));
    }

    @Test
    void updateDetails_WithBlankPhone_ShouldThrowException() {
        id = 1L;
        firstName = "Jane";
        lastName = "Smith";
        email = "jane.smith@example.com";
        rawPassword = "password123";
        role = UserRole.ADMIN;
        phone = "098-765-4321";
        createdAt = LocalDateTime.now().minusDays(1);
        updatedAt = LocalDateTime.now().minusDays(1);
        String hashedPassword = PasswordHasher.hashPassword(rawPassword);

        User user = User.withId(id, firstName, lastName, email, hashedPassword, role, phone, createdAt, updatedAt);

        UserValidationException ex = assertThrows(UserValidationException.class, () -> {
            user.updateDetails(firstName, lastName, email, null, role, "   ");
        });

        assertTrue(ex.getMessage().startsWith("User validation failed:"));
        assertTrue(ex.getMessage().contains("phone cannot be null or empty"));
    }

//  checkPassword()

    @Test
    void checkPassword_WithCorrectPassword_ShouldReturnTrue() {
        firstName = "John";
        lastName = "Doe";
        email = "john.doe@example.com";
        rawPassword = "password123";
        role = UserRole.WORKER;
        phone = "123-456-7890";

        User user = User.createNewUser(firstName, lastName, email, rawPassword, role, phone);

        assertTrue(user.checkPassword(rawPassword));
    }

    @Test
    void checkPassword_WithIncorrectPassword_ShouldReturnFalse() {
        firstName = "John";
        lastName = "Doe";
        email = "john.doe@example.com";
        rawPassword = "password123";
        String incorrectPassword = "wrongpassword";
        role = UserRole.WORKER;
        phone = "123-456-7890";

        User user = User.createNewUser(firstName, lastName, email, rawPassword, role, phone);

        assertFalse(user.checkPassword(incorrectPassword));
    }
}