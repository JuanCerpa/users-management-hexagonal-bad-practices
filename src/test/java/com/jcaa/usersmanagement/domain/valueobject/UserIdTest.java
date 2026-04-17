package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.jcaa.usersmanagement.domain.exception.InvalidUserIdException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("UserId")
class UserIdTest {

  @ParameterizedTest
  @ValueSource(strings = {" user123 ", "  user123  ", "user123\t"})
  @DisplayName("constructor trims incoming value")
  void shouldCreateUserIdWithTrimmedValue(final String input) {
    // Act
    final UserId userId = new UserId(input);

    // Assert
    assertEquals("user123", userId.toString());
  }

  @Test
  @DisplayName("constructor throws NullPointerException when value is null")
  void shouldThrowNullPointerExceptionWhenUserIdIsNull() {
    // Act & Assert
    assertThrows(NullPointerException.class, () -> new UserId(null));
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "   ", "\t", "\n", "\r", "\f", "\b"})
  @DisplayName("constructor throws InvalidUserIdException when value is blank")
  void shouldThrowInvalidUserIdExceptionWhenUserIdIsEmpty(final String input) {
    // Act & Assert
    assertThrows(InvalidUserIdException.class, () -> new UserId(input));
  }
}

