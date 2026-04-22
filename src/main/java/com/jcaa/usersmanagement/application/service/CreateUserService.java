package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.CreateUserUseCase;
import com.jcaa.usersmanagement.application.port.out.GetUserByEmailPort;
import com.jcaa.usersmanagement.application.port.out.SaveUserPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateUserCommand;
<<<<<<< HEAD
import com.jcaa.usersmanagement.domain.enums.UserRole;
=======
import com.jcaa.usersmanagement.application.service.mapper.UserApplicationMapper;
>>>>>>> refactoring-clean-code
import com.jcaa.usersmanagement.domain.exception.UserAlreadyExistsException;
import com.jcaa.usersmanagement.domain.model.UserModel;
import com.jcaa.usersmanagement.domain.valueobject.UserEmail;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public final class CreateUserService implements CreateUserUseCase {

  private final SaveUserPort saveUserPort;
  private final GetUserByEmailPort getUserByEmailPort;
  private final EmailNotificationService emailNotificationService;
  private final Validator validator;

  @Override
  public UserModel execute(final CreateUserCommand command) {
<<<<<<< HEAD
    validateCommand(command);
    log.info("Creando usuario.");
    ensureEmailIsUnique(command.email());

    final UserModel userToSave = buildUserModel(command);
    final UserModel savedUser = saveUserPort.save(userToSave);

    emailNotificationService.notifyUserCreated(savedUser, command.password());
    return savedUser;
  }

=======

    validateCommand(command);

    ensureEmailIsNotTaken(new UserEmail(command.email()));

    final UserModel userToSave = UserApplicationMapper.fromCreateCommandToModel(command);

    final UserModel savedUser = saveUserPort.save(userToSave);

    emailNotificationService.notifyUserCreated(savedUser, command.password());

    return savedUser;
  }

  private void ensureEmailIsNotTaken(final UserEmail email) {
    if (getUserByEmailPort.getByEmail(email).isPresent()) {
      throw UserAlreadyExistsException.becauseEmailAlreadyExists(email.value());
    }
  }

>>>>>>> refactoring-clean-code
  private void validateCommand(final CreateUserCommand command) {
    final Set<ConstraintViolation<CreateUserCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
<<<<<<< HEAD
  }

  private void ensureEmailIsUnique(final String rawEmail) {
    final UserEmail email = new UserEmail(rawEmail);
    if (getUserByEmailPort.getByEmail(email).isPresent()) {
      throw UserAlreadyExistsException.becauseEmailAlreadyExists(email.value());
    }
  }

  private UserModel buildUserModel(final CreateUserCommand command) {
    return UserModel.create(
        new UserId(command.id()),
        new UserName(command.name()),
        new UserEmail(command.email()),
        UserPassword.fromPlainText(command.password()),
        UserRole.fromString(command.role())
    );
=======
>>>>>>> refactoring-clean-code
  }
}
