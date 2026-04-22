package com.jcaa.usersmanagement.domain.exception;

public final class UserNotFoundException extends DomainException {

<<<<<<< HEAD
  private static final String USER_NOT_FOUND_BY_ID_MESSAGE =
      "The user with id '%s' was not found.";
=======
  private static final String MSG_NOT_FOUND = "The user with id '%s' was not found.";
>>>>>>> refactoring-clean-code

  private UserNotFoundException(final String message) {
    super(message);
  }

  public static UserNotFoundException becauseIdWasNotFound(final String userId) {
<<<<<<< HEAD
    return new UserNotFoundException(String.format(USER_NOT_FOUND_BY_ID_MESSAGE, userId));
=======
    return new UserNotFoundException(String.format(MSG_NOT_FOUND, userId));
>>>>>>> refactoring-clean-code
  }
}
