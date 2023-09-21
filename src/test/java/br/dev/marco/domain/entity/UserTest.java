package br.dev.marco.domain.entity;


import br.dev.marco.domain.exception.PasswordException;
import br.dev.marco.domain.exception.UsernameException;
import br.dev.marco.domain.usecase.enuns.UserType;
import br.dev.marco.domain.usecase.exceptions.UserCreationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    User user;

    @Test
    @DisplayName("Should instantiate a Free User entity correctly")
    void instantiateFreeUserCorrectly() throws PasswordException, UsernameException, UserCreationException {
        user = new User("johndoe","Test@123", UserType.FREE.getRoleName());
        Assertions.assertNotNull(user);
        Assertions.assertEquals("johndoe", user.getUsername().getValue());
        Assertions.assertEquals("Test@123", user.getPassword().getValue());
        Assertions.assertEquals("simple-user", user.getRole());
    }

    @Test
    @DisplayName("Should instantiate a Member User entity correctly")
    void instantiateMemberUserCorrectly() throws PasswordException, UsernameException, UserCreationException {
        user = new User("johndoe","Test@123", UserType.MEMBER.getRoleName());
        Assertions.assertNotNull(user);
        Assertions.assertEquals("johndoe", user.getUsername().getValue());
        Assertions.assertEquals("Test@123", user.getPassword().getValue());
        Assertions.assertEquals("member-user", user.getRole());
    }

    @Test
    @DisplayName("Should throw an exception when instantiate a User entity with invalid username")
    void shouldThrowErrorWhenInstantiateUsername() {
        Assertions.assertThrows(UsernameException.class, () -> new User("john doe","Test@123", UserType.FREE.getRoleName()));
    }

    @Test
    @DisplayName("Should throw an exception when instantiate a User entity with invalid passsword")
    void shouldThrowErrorWhenInstantiatePassword()  {
        Assertions.assertThrows(PasswordException.class, () -> new User("johndoe","Test", UserType.FREE.getRoleName()));
    }

    @Test
    @DisplayName("Should throw an exception when instantiate a User entity with invalid role")
    void shouldThrowErrorWhenInstantiateRole() {
        Assertions.assertThrows(UserCreationException.class, () -> new User("johndoe","Test@123", "invalid_role"));
    }

    @Test
    @DisplayName("Should change password correctly")
    void changePasswordCorrectly() throws PasswordException, UsernameException, UserCreationException {
        user = new User("johndoe","Test@123", UserType.FREE.getRoleName());
        user.changePassword("Test@1234");
        Assertions.assertEquals("Test@1234", user.getPassword().getValue());
    }

    @Test
    @DisplayName("Should throw an exception when trying to change to an invalid password")
    void shouldThrowErrorWhenChangePassword() throws PasswordException, UsernameException, UserCreationException {
        user = new User("johndoe","Test@123", UserType.FREE.getRoleName());
        Assertions.assertThrows(PasswordException.class, () -> user.changePassword("Test"));
    }

}

