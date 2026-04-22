package com.jcaa.usersmanagement.domain.valueobject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.jcaa.usersmanagement.domain.exception.InvalidUserNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

<<<<<<< HEAD
@DisplayName("UserName")
=======
@DisplayName("UserName Value Object Test Suite")
>>>>>>> refactoring-clean-code
class UserNameTest {

  @ParameterizedTest
  @DisplayName("Should create UserName with trimmed value when it meets minimum length")
  @ValueSource(strings = {"John Arrieta", "   John Arrieta   ", "John Arrieta \t"})
  @DisplayName("constructor normalizes and preserves valid names")
  void shouldValidateUserNameMinimumLength(final String userName) {
<<<<<<< HEAD
    // Act
    final UserName userNameValueObject = new UserName(userName);

    // Assert
    assertEquals("John Arrieta", userNameValueObject.toString());
=======
    // Arrange
    final String correctUserName = "John Arrieta";

    // Act
    final UserName userNameVo = new UserName(userName);

    // Assert
    assertEquals(correctUserName, userNameVo.toString());
>>>>>>> refactoring-clean-code
  }

  @Test
<<<<<<< HEAD
  @DisplayName("constructor throws NullPointerException when value is null")
=======
  @DisplayName("Should throw NullPointerException when UserName is null")
>>>>>>> refactoring-clean-code
  void shouldValidateUserNameIsNotNull() {
    // Act & Assert
    assertThrows(NullPointerException.class, () -> new UserName(null));
  }

  @ParameterizedTest
  @DisplayName("Should throw InvalidUserNameException when UserName is empty or too short")
  @ValueSource(
      strings = {"", "  ", "\t", "\n", "\r", "\f", "\b", "Jo", "Ty  ", "", "   Cy ", "Ed\t"})
  @DisplayName("constructor throws InvalidUserNameException on blank or too short values")
  void shouldValidateUserNameIsNotEmptyAndMinimumLength(final String userName) {
    // Act & Assert
    assertThrows(InvalidUserNameException.class, () -> new UserName(userName));
  }
}

