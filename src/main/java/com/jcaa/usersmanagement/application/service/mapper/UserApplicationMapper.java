package com.jcaa.usersmanagement.application.service.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateUserCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteUserCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateUserCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetUserByIdQuery;
import com.jcaa.usersmanagement.domain.enums.UserRole;
import com.jcaa.usersmanagement.domain.enums.UserStatus;
import com.jcaa.usersmanagement.domain.model.UserModel;
import com.jcaa.usersmanagement.domain.valueobject.UserEmail;
import com.jcaa.usersmanagement.domain.valueobject.UserId;
import com.jcaa.usersmanagement.domain.valueobject.UserName;
import com.jcaa.usersmanagement.domain.valueobject.UserPassword;
import java.util.Objects;

import lombok.experimental.UtilityClass;

public final class UserApplicationMapper {

  private UserApplicationMapper() {
    throw new UnsupportedOperationException("Utility class should not be instantiated");
  }

  public static UserModel fromCreateCommandToModel(final CreateUserCommand command) {
    UserId idStr = new UserId(command.id());
    UserName nameStr = new UserName(command.name());
    UserEmail emailStr = new UserEmail(command.email());
    UserPassword passStr = UserPassword.fromPlainText(command.password());
    UserRole roleStr = UserRole.fromString(command.role());

    return UserModel.create(idStr, nameStr, emailStr, passStr, roleStr);
  }

  public static UserModel fromUpdateCommandToModel(
      final UpdateUserCommand command, final UserPassword currentPassword) {

    String commandPass = command.password();
    UserPassword passwordToUse = (commandPass == null || commandPass.isBlank())
        ? currentPassword
        : UserPassword.fromPlainText(commandPass);

    return new UserModel(
        new UserId(command.id()),
        new UserName(command.name()),
        new UserEmail(command.email()),
        passwordToUse,
        UserRole.fromString(command.role()),
        UserStatus.fromString(command.status()));
  }

  public static UserId fromGetUserByIdQueryToUserId(final GetUserByIdQuery query) {
    return new UserId(query.id());
  }

  public static UserId fromDeleteCommandToUserId(final DeleteUserCommand command) {
    return new UserId(command.id());
  }
}
