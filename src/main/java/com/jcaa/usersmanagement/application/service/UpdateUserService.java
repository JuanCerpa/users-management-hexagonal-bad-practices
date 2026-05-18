package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.UpdateUserUseCase;
import com.jcaa.usersmanagement.application.port.out.GetUserByEmailPort;
import com.jcaa.usersmanagement.application.port.out.GetUserByIdPort;
import com.jcaa.usersmanagement.application.port.out.UpdateUserPort;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateUserCommand;
import com.jcaa.usersmanagement.application.service.mapper.UserApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.UserAlreadyExistsException;
import com.jcaa.usersmanagement.domain.exception.UserNotFoundException;
import com.jcaa.usersmanagement.domain.model.UserModel;
import com.jcaa.usersmanagement.domain.valueobject.UserEmail;
import com.jcaa.usersmanagement.domain.valueobject.UserId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.util.Set;

@Log
public final class UpdateUserService implements UpdateUserUseCase {

  private final UpdateUserPort updateUserPort;
  private final GetUserByIdPort getUserByIdPort;
  private final GetUserByEmailPort getUserByEmailPort;
  private final EmailNotificationService emailNotificationService;
  private final Validator validator;

  public UpdateUserService(
      UpdateUserPort updateUserPort,
      GetUserByIdPort getUserByIdPort,
      GetUserByEmailPort getUserByEmailPort,
      EmailNotificationService emailNotificationService,
      Validator validator) {
    this.updateUserPort = updateUserPort;
    this.getUserByIdPort = getUserByIdPort;
    this.getUserByEmailPort = getUserByEmailPort;
    this.emailNotificationService = emailNotificationService;
    this.validator = validator;
  }

  @Override
  public void execute(UpdateUserCommand command) {
    verifyCommand(command);

    log.info("Actualizando usuario id=" + command.id());

    UserId id = new UserId(command.id());
    UserModel existingUser = fetchExistingUser(id);
    UserEmail emailAddress = new UserEmail(command.email());

    checkEmailAvailability(emailAddress, id);

    UserModel userToUpdate =
        UserApplicationMapper.fromUpdateCommandToModel(command, existingUser.getPassword());
    UserModel updatedUser = updateUserPort.update(userToUpdate);

    emailNotificationService.notifyUserUpdated(updatedUser);
  }

  private void verifyCommand(UpdateUserCommand command) {
    Set<ConstraintViolation<UpdateUserCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  private UserModel fetchExistingUser(UserId id) {
    return getUserByIdPort
        .getById(id)
        .orElseThrow(() -> UserNotFoundException.becauseIdWasNotFound(id.value()));
  }

  private void checkEmailAvailability(UserEmail emailAddress, UserId ownerId) {
    getUserByEmailPort.getByEmail(emailAddress)
        .ifPresent(existing -> {
          if (!existing.getId().equals(ownerId)) {
            throw UserAlreadyExistsException.becauseEmailAlreadyExists(emailAddress.value());
          }
        });
  }
}
