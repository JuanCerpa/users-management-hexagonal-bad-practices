package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.DeleteUserUseCase;
import com.jcaa.usersmanagement.application.port.out.DeleteUserPort;
import com.jcaa.usersmanagement.application.port.out.GetUserByIdPort;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteUserCommand;
import com.jcaa.usersmanagement.application.service.mapper.UserApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.UserNotFoundException;
import com.jcaa.usersmanagement.domain.valueobject.UserId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import java.util.Set;

import lombok.extern.java.Log;

@Log
public final class DeleteUserService implements DeleteUserUseCase {

  private final DeleteUserPort deleteUserPort;
  private final GetUserByIdPort getUserByIdPort;
  private final Validator validator;

  public DeleteUserService(
      DeleteUserPort deleteUserPort,
      GetUserByIdPort getUserByIdPort,
      Validator validator) {
    this.deleteUserPort = deleteUserPort;
    this.getUserByIdPort = getUserByIdPort;
    this.validator = validator;
  }

  @Override
  public void execute(DeleteUserCommand command) {
    verifyCommandConstraints(command);
    UserId targetId = UserApplicationMapper.fromDeleteCommandToUserId(command);
    checkUserExistence(targetId);
    deleteUserPort.delete(targetId);
    log.info(String.format("User deleted: %s", targetId.value()));
  }

  private void verifyCommandConstraints(DeleteUserCommand command) {
    Set<ConstraintViolation<DeleteUserCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  private void checkUserExistence(UserId targetId) {
    getUserByIdPort
        .getById(targetId)
        .orElseThrow(() -> UserNotFoundException.becauseIdWasNotFound(targetId.value()));
  }
}
