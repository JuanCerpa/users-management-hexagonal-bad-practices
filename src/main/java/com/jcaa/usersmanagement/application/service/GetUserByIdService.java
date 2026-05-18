package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetUserByIdUseCase;
import com.jcaa.usersmanagement.application.port.out.GetUserByIdPort;
import com.jcaa.usersmanagement.application.service.dto.query.GetUserByIdQuery;
import com.jcaa.usersmanagement.application.service.mapper.UserApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.UserNotFoundException;
import com.jcaa.usersmanagement.domain.model.UserModel;
import com.jcaa.usersmanagement.domain.valueobject.UserId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import java.util.Set;

public final class GetUserByIdService implements GetUserByIdUseCase {

  private final GetUserByIdPort getUserByIdPort;
  private final Validator validator;

  public GetUserByIdService(GetUserByIdPort getUserByIdPort, Validator validator) {
    this.getUserByIdPort = getUserByIdPort;
    this.validator = validator;
  }

  @Override
  public UserModel execute(GetUserByIdQuery query) {
    verifyQuery(query);

    UserId id = UserApplicationMapper.fromGetUserByIdQueryToUserId(query);
    return getUserByIdPort
        .getById(id)
        .orElseThrow(() -> UserNotFoundException.becauseIdWasNotFound(id.value()));
  }

  private void verifyQuery(GetUserByIdQuery query) {
    Set<ConstraintViolation<GetUserByIdQuery>> violations = validator.validate(query);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
