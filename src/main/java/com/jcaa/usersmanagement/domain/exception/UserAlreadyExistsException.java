package com.jcaa.usersmanagement.domain.exception;

public final class UserAlreadyExistsException extends DomainException {

<<<<<<< HEAD
  private static final String USER_ALREADY_EXISTS_BY_EMAIL_MESSAGE =
      "A user with email '%s' already exists.";
=======
  private static final String MSG_ALREADY_EXISTS = "A user with email '%s' already exists.";
>>>>>>> refactoring-clean-code

  private UserAlreadyExistsException(final String message) {
    super(message);
  }

  public static UserAlreadyExistsException becauseEmailAlreadyExists(final String email) {
<<<<<<< HEAD
    return new UserAlreadyExistsException(String.format(USER_ALREADY_EXISTS_BY_EMAIL_MESSAGE, email));
=======
    return new UserAlreadyExistsException(String.format(MSG_ALREADY_EXISTS, email));
>>>>>>> refactoring-clean-code
  }
}
