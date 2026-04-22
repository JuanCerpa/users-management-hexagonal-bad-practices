package com.jcaa.usersmanagement.domain.exception;

public final class InvalidUserNameException extends DomainException {

<<<<<<< HEAD
  private static final String USER_NAME_EMPTY_MESSAGE = "The user name must not be empty.";
  private static final String USER_NAME_TOO_SHORT_MESSAGE =
      "The user name must have at least %d characters.";
=======
  private static final String MSG_EMPTY = "The user name must not be empty.";
  private static final String MSG_TOO_SHORT = "The user name must have at least %d characters.";
>>>>>>> refactoring-clean-code

  private InvalidUserNameException(final String message) {
    super(message);
  }

  public static InvalidUserNameException becauseValueIsEmpty() {
<<<<<<< HEAD
    return new InvalidUserNameException(USER_NAME_EMPTY_MESSAGE);
  }

  public static InvalidUserNameException becauseLengthIsTooShort(final int minimumLength) {
    return new InvalidUserNameException(String.format(USER_NAME_TOO_SHORT_MESSAGE, minimumLength));
=======
    return new InvalidUserNameException(MSG_EMPTY);
  }

  public static InvalidUserNameException becauseLengthIsTooShort(final int minimumLength) {
    return new InvalidUserNameException(
        String.format(MSG_TOO_SHORT, minimumLength));
>>>>>>> refactoring-clean-code
  }
}
