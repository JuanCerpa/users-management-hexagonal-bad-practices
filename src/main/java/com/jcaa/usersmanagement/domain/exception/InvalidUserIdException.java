package com.jcaa.usersmanagement.domain.exception;

public final class InvalidUserIdException extends DomainException {

<<<<<<< HEAD
  private static final String USER_ID_EMPTY_MESSAGE = "The user id must not be empty.";
=======
  private static final String MSG_EMPTY = "The user id must not be empty.";
>>>>>>> refactoring-clean-code

  private InvalidUserIdException(final String message) {
    super(message);
  }

  public static InvalidUserIdException becauseValueIsEmpty() {
<<<<<<< HEAD
    return new InvalidUserIdException(USER_ID_EMPTY_MESSAGE);
=======
    return new InvalidUserIdException(MSG_EMPTY);
>>>>>>> refactoring-clean-code
  }
}
