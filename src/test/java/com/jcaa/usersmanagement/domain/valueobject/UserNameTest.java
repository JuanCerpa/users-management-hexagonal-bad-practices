package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.jcaa.usersmanagement.domain.exception.InvalidUserNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("UserName")
class UserNameTest {

  @ParameterizedTest
  @ValueSource(strings = {"John Arrieta", "   John Arrieta   ", "John Arrieta \t"})
  @DisplayName("constructor normalizes and preserves valid names")
  void shouldValidateUserNameMinimumLength(final String userName) {
    // Act
    final UserName userNameValueObject = new UserName(userName);

    // Assert
    assertEquals("John Arrieta", userNameValueObject.toString());
  }

  @Test
  @DisplayName("constructor throws NullPointerException when value is null")
  void shouldValidateUserNameIsNotNull() {
    // Act & Assert
    assertThrows(NullPointerException.class, () -> new UserName(null));
  }

  @ParameterizedTest
  @ValueSource(
      strings = {"", "  ", "\t", "\n", "\r", "\f", "\b", "Jo", "Ty  ", "", "   Cy ", "Ed\t"})
  @DisplayName("constructor throws InvalidUserNameException on blank or too short values")
  void shouldValidateUserNameIsNotEmptyAndMinimumLength(final String userName) {
    // Act & Assert
    assertThrows(InvalidUserNameException.class, () -> new UserName(userName));
  }
}

