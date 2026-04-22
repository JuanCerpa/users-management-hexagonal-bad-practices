package com.jcaa.usersmanagement.domain.exception;

public final class InvalidUserEmailException extends DomainException {

<<<<<<< HEAD
  private static final String USER_EMAIL_EMPTY_MESSAGE = "The user email must not be empty.";
  private static final String USER_EMAIL_INVALID_FORMAT_MESSAGE =
      "The user email format is invalid: '%s'.";
=======
  private static final String MSG_EMPTY = "The user email must not be empty.";
  private static final String MSG_INVALID_FORMAT = "The user email format is invalid: '%s'.";
>>>>>>> refactoring-clean-code

  private InvalidUserEmailException(final String message) {
    super(message);
  }

  public static InvalidUserEmailException becauseValueIsEmpty() {
<<<<<<< HEAD
    return new InvalidUserEmailException(USER_EMAIL_EMPTY_MESSAGE);
  }

  public static InvalidUserEmailException becauseFormatIsInvalid(final String email) {
    return new InvalidUserEmailException(String.format(USER_EMAIL_INVALID_FORMAT_MESSAGE, email));
=======
    return new InvalidUserEmailException(MSG_EMPTY);
  }

  public static InvalidUserEmailException becauseFormatIsInvalid(final String email) {
    return new InvalidUserEmailException(String.format(MSG_INVALID_FORMAT, email));
>>>>>>> refactoring-clean-code
  }
}
