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

public final class LoginService implements LoginUseCase {

  private final GetUserByEmailPort getUserByEmailPort;
  private final Validator validator;

  public LoginService(GetUserByEmailPort getUserByEmailPort, Validator validator) {
    this.getUserByEmailPort = getUserByEmailPort;
    this.validator = validator;
  }

  @Override
  public UserModel execute(LoginCommand command) {
    assertCommandValidity(command);

    UserEmail emailAddress = new UserEmail(command.email());
    UserModel domainUser = retrieveUser(emailAddress);

    verifyCredentials(domainUser, command.password());

    return domainUser;
  }

  private UserModel retrieveUser(UserEmail emailAddress) {
    return getUserByEmailPort.getByEmail(emailAddress)
        .orElseThrow(InvalidCredentialsException::becauseCredentialsAreInvalid);
  }

  private void verifyCredentials(UserModel domainUser, String plainPassword) {
    if (!domainUser.passwordMatches(plainPassword)) {
      throw InvalidCredentialsException.becauseCredentialsAreInvalid();
    }
    if (!domainUser.isActive()) {
      throw InvalidCredentialsException.becauseUserIsNotActive();
    }
  }

  private void assertCommandValidity(LoginCommand command) {
    Set<ConstraintViolation<LoginCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
