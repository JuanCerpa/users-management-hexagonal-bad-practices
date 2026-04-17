package com.jcaa.usersmanagement.domain.exception;

public final class UserAlreadyExistsException extends DomainException {

  private static final String USER_ALREADY_EXISTS_BY_EMAIL_MESSAGE =
      "A user with email '%s' already exists.";

  private UserAlreadyExistsException(final String message) {
    super(message);
  }

  public static UserAlreadyExistsException becauseEmailAlreadyExists(final String email) {
    return new UserAlreadyExistsException(String.format(USER_ALREADY_EXISTS_BY_EMAIL_MESSAGE, email));
  }
}
