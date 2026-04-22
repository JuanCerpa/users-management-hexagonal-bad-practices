package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import com.jcaa.usersmanagement.domain.exception.InvalidUserPasswordException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

<<<<<<< HEAD
@DisplayName("UserPassword")
=======
@DisplayName("UserPassword Value Object Test Suite")
>>>>>>> refactoring-clean-code
class UserPasswordTest {

  @ParameterizedTest
  @DisplayName("Should normalize and hash password correctly")
  @ValueSource(strings = {"password123", "   password123   "})
  @DisplayName("fromPlainText() normalizes input and stores a hash")
  void shouldNormalizeAndHashPassword(final String input) {
<<<<<<< HEAD
=======
    // Arrange
    // (input provided by ParameterizedTest)

>>>>>>> refactoring-clean-code
    // Act
    final UserPassword result = UserPassword.fromPlainText(input);

    // Assert
    assertNotNull(result.value());
    assertNotEquals(input.trim(), result.value());
  }

  @ParameterizedTest
  @ValueSource(strings = {"clave", "    clave     "})
  @DisplayName("fromPlainText() rejects passwords shorter than minimum length")
  void shouldFailWhenPasswordIsTooShort(final String password) {
    // Act & Assert
    assertThrows(InvalidUserPasswordException.class, () -> UserPassword.fromPlainText(password));
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "  ", "\r", "\t", "\n", "\f", "\b", "\0"})
  @DisplayName("fromPlainText() rejects blank passwords")
  void shouldThrowWhenPasswordIsEmptyOrBlank(final String password) {
    // Act & Assert
    assertThrows(InvalidUserPasswordException.class, () -> UserPassword.fromPlainText(password));
  }

  @Test
  @DisplayName("fromPlainText() rejects null passwords")
  void shouldThrowWhenPasswordIsNull() {
    // Act & Assert
    assertThrows(NullPointerException.class, () -> UserPassword.fromPlainText(null));
  }

  @Test
  @DisplayName("verifyPlain() validates original plain text against generated hash")
  void shouldVerifyPlainPassword() {
    // Arrange
    final String plainPassword = "mySecurePassword";
    final UserPassword userPassword = UserPassword.fromPlainText(plainPassword);

    // Act & Assert
    assertTrue(userPassword.verifyPlain(plainPassword));
  }

  @Test
  @DisplayName("fromHash() creates equivalent value object from existing hash")
  void shouldCreateUserPasswordFromExistingHash() {
    // Arrange
    final String rawPassword = "Abcde1234567";
    final UserPassword originalUserPassword = UserPassword.fromPlainText(rawPassword);
    final String generatedHash = originalUserPassword.value();

    // Act
    final UserPassword fromHashUserPassword = UserPassword.fromHash(generatedHash);

    // Assert
    assertEquals(originalUserPassword, fromHashUserPassword);
    assertTrue(fromHashUserPassword.verifyPlain(rawPassword));
  }

  @Test
  @DisplayName("equals() returns false when compared with another type")
  void shouldReturnFalseWhenOtherIsNotInstanceOfUserPassword() {
    // Arrange
    final UserPassword password = UserPassword.fromPlainText("MiPassword123");
    final Object nonUserPassword = mock(Object.class);

    // Act & Assert
    assertNotEquals(password, nonUserPassword);
  }

  @Test
  @DisplayName("equals() returns false for different hashes")
  void shouldReturnFalseWhenDifferentHash() {
    // Arrange
    final UserPassword first = UserPassword.fromPlainText("MiPassword123");
    final UserPassword second = UserPassword.fromPlainText("OtroPassword456");

    // Act & Assert
    assertNotEquals(first, second);
  }

  @Test
  @DisplayName("hashCode() is stable for the same instance")
  void shouldReturnConsistentHashCode() {
    // Arrange
    final UserPassword password = UserPassword.fromPlainText("MiPassword123");

    // Act
    final int firstHashCode = password.hashCode();
    final int secondHashCode = password.hashCode();

    // Assert
    assertEquals(firstHashCode, secondHashCode);
  }

  @Test
  @DisplayName("hashCode() matches for equal objects")
  void shouldHaveSameHashCodeWhenEqual() {
    // Arrange
    final UserPassword first = UserPassword.fromPlainText("MiPassword123");
    final UserPassword second = UserPassword.fromHash(first.value());

    // Act & Assert
    assertEquals(first, second);
    assertEquals(first.hashCode(), second.hashCode());
  }
}

