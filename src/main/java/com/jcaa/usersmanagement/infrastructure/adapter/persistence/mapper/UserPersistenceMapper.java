package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;

import com.jcaa.usersmanagement.domain.enums.UserRole;
import com.jcaa.usersmanagement.domain.enums.UserStatus;
import com.jcaa.usersmanagement.domain.model.UserModel;
import com.jcaa.usersmanagement.domain.valueobject.UserEmail;
import com.jcaa.usersmanagement.domain.valueobject.UserId;
import com.jcaa.usersmanagement.domain.valueobject.UserName;
import com.jcaa.usersmanagement.domain.valueobject.UserPassword;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.UserPersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.UserEntity;
import lombok.experimental.UtilityClass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class UserPersistenceMapper {

  private UserPersistenceMapper() {
    throw new AssertionError("No instances of UserPersistenceMapper allowed");
  }

  public static UserPersistenceDto fromModelToDto(final UserModel user) {
    String id = user.idValue();
    String name = user.nameValue();
    String email = user.emailValue();
    String password = user.passwordValue();
    String role = user.roleName();
    String status = user.statusName();

    return new UserPersistenceDto(id, name, email, password, role, status, null, null);
  }

  public static UserEntity fromResultSetToEntity(final ResultSet resultSet) throws SQLException {
    String id = resultSet.getString("id");
    String name = resultSet.getString("name");
    String email = resultSet.getString("email");
    String password = resultSet.getString("password");
    String role = resultSet.getString("role");
    String status = resultSet.getString("status");
    String createdAt = resultSet.getString("created_at");
    String updatedAt = resultSet.getString("updated_at");

    return new UserEntity(id, name, email, password, role, status, createdAt, updatedAt);
  }

  public static UserModel fromEntityToModel(final UserEntity entity) {
    UserId idObj = new UserId(entity.id());
    UserName nameObj = new UserName(entity.name());
    UserEmail emailObj = new UserEmail(entity.email());
    UserPassword passObj = UserPassword.fromHash(entity.password());
    UserRole roleEnum = UserRole.fromString(entity.role());
    UserStatus statusEnum = UserStatus.fromString(entity.status());

    return new UserModel(idObj, nameObj, emailObj, passObj, roleEnum, statusEnum);
  }

  public static UserModel fromResultSetToModel(final ResultSet resultSet) throws SQLException {
    UserEntity entity = fromResultSetToEntity(resultSet);
    return fromEntityToModel(entity);
  }

  public static List<UserModel> fromResultSetToModelList(final ResultSet resultSet) throws SQLException {
    List<UserModel> list = new ArrayList<>();
    while (resultSet.next()) {
      list.add(fromResultSetToModel(resultSet));
    }
    return list;
  }
}