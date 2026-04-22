package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.LoginUseCase;
import com.jcaa.usersmanagement.application.port.out.GetUserByEmailPort;
import com.jcaa.usersmanagement.application.service.dto.command.LoginCommand;
import com.jcaa.usersmanagement.domain.exception.InvalidCredentialsException;
import com.jcaa.usersmanagement.domain.model.UserModel;
import com.jcaa.usersmanagement.domain.valueobject.UserEmail;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public final class LoginService implements LoginUseCase {

  private final GetUserByEmailPort getUserByEmailPort;
  private final Validator validator;

  @Override
  public UserModel execute(final LoginCommand command) {
    validateCommand(command);

    final UserEmail email = new UserEmail(command.email());
    final UserModel user = findUserByEmail(email);

<<<<<<< HEAD
    verifyPassword(user, command.password());
    verifyUserStatus(user);
=======
    validateUserCredentials(user, command.password());
>>>>>>> refactoring-clean-code

    return user;
  }

  private UserModel findUserByEmail(final UserEmail email) {
    return getUserByEmailPort.getByEmail(email)
        .orElseThrow(InvalidCredentialsException::becauseCredentialsAreInvalid);
  }

<<<<<<< HEAD
  private void verifyPassword(final UserModel user, final String plainPassword) {
    if (!user.passwordMatches(plainPassword)) {
      throw InvalidCredentialsException.becauseCredentialsAreInvalid();
    }
  }

  private void verifyUserStatus(final UserModel user) {
    if (!user.isAllowedToLogin()) {
=======
  private void validateUserCredentials(final UserModel user, final String plainPassword) {
    if (!user.passwordMatches(plainPassword)) {
      throw InvalidCredentialsException.becauseCredentialsAreInvalid();
    }
    if (!user.isActive()) {
>>>>>>> refactoring-clean-code
      throw InvalidCredentialsException.becauseUserIsNotActive();
    }
  }

  private void validateCommand(final LoginCommand command) {
    final Set<ConstraintViolation<LoginCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}

