package com.jcaa.usersmanagement.domain.exception;

public final class InvalidCredentialsException extends DomainException {

<<<<<<< HEAD
  private static final String INVALID_CREDENTIALS_MESSAGE = "Correo o contraseña incorrectos.";
  private static final String USER_NOT_ACTIVE_MESSAGE =
      "Tu cuenta no está activa. Contacta al administrador.";
=======
  private static final String MSG_INVALID_CREDENTIALS = "Correo o contraseña incorrectos.";
  private static final String MSG_USER_NOT_ACTIVE = "Tu cuenta no está activa. Contacta al administrador.";
>>>>>>> refactoring-clean-code

  private InvalidCredentialsException(final String message) {
    super(message);
  }

  public static InvalidCredentialsException becauseCredentialsAreInvalid() {
<<<<<<< HEAD
    return new InvalidCredentialsException(INVALID_CREDENTIALS_MESSAGE);
  }

  public static InvalidCredentialsException becauseUserIsNotActive() {
    return new InvalidCredentialsException(USER_NOT_ACTIVE_MESSAGE);
=======
    return new InvalidCredentialsException(MSG_INVALID_CREDENTIALS);
  }

  public static InvalidCredentialsException becauseUserIsNotActive() {
    return new InvalidCredentialsException(MSG_USER_NOT_ACTIVE);
>>>>>>> refactoring-clean-code
  }
}
