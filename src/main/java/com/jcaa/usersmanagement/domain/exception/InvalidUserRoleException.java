package com.jcaa.usersmanagement.domain.exception;

public final class InvalidUserRoleException extends DomainException {

<<<<<<< HEAD
  private static final String INVALID_USER_ROLE_MESSAGE =
      "The user role '%s' is not valid.";
=======
  private static final String MSG_INVALID_ROLE = "The user role '%s' is not valid.";
>>>>>>> refactoring-clean-code

  private InvalidUserRoleException(final String message) {
    super(message);
  }

  public static InvalidUserRoleException becauseValueIsInvalid(final String role) {
<<<<<<< HEAD
    return new InvalidUserRoleException(String.format(INVALID_USER_ROLE_MESSAGE, role));
=======
    return new InvalidUserRoleException(String.format(MSG_INVALID_ROLE, role));
>>>>>>> refactoring-clean-code
  }
}
