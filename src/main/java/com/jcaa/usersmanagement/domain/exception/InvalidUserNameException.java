package com.jcaa.usersmanagement.domain.exception;

public final class InvalidUserNameException extends DomainException {

  private static final String USER_NAME_EMPTY_MESSAGE = "The user name must not be empty.";
  private static final String USER_NAME_TOO_SHORT_MESSAGE =
      "The user name must have at least %d characters.";

  private InvalidUserNameException(final String message) {
    super(message);
  }

  public static InvalidUserNameException becauseValueIsEmpty() {
    return new InvalidUserNameException(USER_NAME_EMPTY_MESSAGE);
  }

  public static InvalidUserNameException becauseLengthIsTooShort(final int minimumLength) {
    return new InvalidUserNameException(String.format(USER_NAME_TOO_SHORT_MESSAGE, minimumLength));
  }
}
