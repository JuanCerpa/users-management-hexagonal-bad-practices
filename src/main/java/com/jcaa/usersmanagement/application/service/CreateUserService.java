package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.CreateUserUseCase;
import com.jcaa.usersmanagement.application.port.out.GetUserByEmailPort;
import com.jcaa.usersmanagement.application.port.out.SaveUserPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateUserCommand;
import com.jcaa.usersmanagement.application.service.mapper.UserApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.UserAlreadyExistsException;
import com.jcaa.usersmanagement.domain.model.UserModel;
import com.jcaa.usersmanagement.domain.valueobject.UserEmail;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import java.util.Set;

public final class CreateUserService implements CreateUserUseCase {

  private final SaveUserPort saveUserPort;
  private final GetUserByEmailPort getUserByEmailPort;
  private final EmailNotificationService emailNotificationService;
  private final Validator validator;

  public CreateUserService(
      SaveUserPort saveUserPort,
      GetUserByEmailPort getUserByEmailPort,
      EmailNotificationService emailNotificationService,
      Validator validator) {
    this.saveUserPort = saveUserPort;
    this.getUserByEmailPort = getUserByEmailPort;
    this.emailNotificationService = emailNotificationService;
    this.validator = validator;
  }

  @Override
  public UserModel execute(CreateUserCommand command) {
    assertCommandIsValid(command);
    verifyEmailUniqueness(new UserEmail(command.email()));

    UserModel domainUser = UserApplicationMapper.fromCreateCommandToModel(command);
    UserModel persistedUser = saveUserPort.save(domainUser);

    emailNotificationService.notifyUserCreated(persistedUser, command.password());
    return persistedUser;
  }

  private void verifyEmailUniqueness(UserEmail emailAddress) {
    if (getUserByEmailPort.getByEmail(emailAddress).isPresent()) {
      throw UserAlreadyExistsException.becauseEmailAlreadyExists(emailAddress.value());
    }
  }

  private void assertCommandIsValid(CreateUserCommand cmd) {
    Set<ConstraintViolation<CreateUserCommand>> violations = validator.validate(cmd);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
