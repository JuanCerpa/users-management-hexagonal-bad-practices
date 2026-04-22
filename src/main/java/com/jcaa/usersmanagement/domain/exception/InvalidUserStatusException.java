package com.jcaa.usersmanagement.domain.exception;

public final class InvalidUserStatusException extends DomainException {

<<<<<<< HEAD
  private static final String INVALID_USER_STATUS_MESSAGE =
      "The user status '%s' is not valid.";
=======
  private static final String MSG_INVALID_STATUS = "The user status '%s' is not valid.";
>>>>>>> refactoring-clean-code

  private InvalidUserStatusException(final String message) {
    super(message);
  }

  public static InvalidUserStatusException becauseValueIsInvalid(final String status) {
<<<<<<< HEAD
    return new InvalidUserStatusException(String.format(INVALID_USER_STATUS_MESSAGE, status));
=======
    return new InvalidUserStatusException(String.format(MSG_INVALID_STATUS, status));
>>>>>>> refactoring-clean-code
  }
}
