package com.jcaa.usersmanagement.domain.model;

import com.jcaa.usersmanagement.domain.enums.UserRole;
import com.jcaa.usersmanagement.domain.enums.UserStatus;
import com.jcaa.usersmanagement.domain.valueobject.UserEmail;
import com.jcaa.usersmanagement.domain.valueobject.UserId;
import com.jcaa.usersmanagement.domain.valueobject.UserName;
import com.jcaa.usersmanagement.domain.valueobject.UserPassword;
import lombok.Value;

@Value
public class UserModel {

  UserId id;
  UserName name;
  UserEmail email;
  UserPassword password;
  UserRole role;
  UserStatus status;

  public static UserModel create(
      final UserId id,
      final UserName name,
      final UserEmail email,
      final UserPassword password,
      final UserRole role) {
    return new UserModel(id, name, email, password, role, UserStatus.PENDING);
  }

  public UserModel activate() {
    return new UserModel(id, name, email, password, role, UserStatus.ACTIVE);
  }

  public UserModel deactivate() {
    return new UserModel(id, name, email, password, role, UserStatus.INACTIVE);
  }

<<<<<<< HEAD
  public String getIdValue() { return id.value(); }
  public String getNameValue() { return name.value(); }
  public String getEmailValue() { return email.value(); }
  public String getPasswordValue() { return password.value(); }

  public boolean passwordMatches(String plainText) {
    return password.verifyPlain(plainText);
  }

  public boolean isAllowedToLogin() {
    return status == UserStatus.ACTIVE;
=======
  public boolean isActive() {
    return status == UserStatus.ACTIVE;
  }

  public boolean passwordMatches(final String plainPassword) {
    return password.verifyPlain(plainPassword);
  }

  public String idValue() {
    return id.value();
  }

  public String nameValue() {
    return name.value();
  }

  public String emailValue() {
    return email.value();
  }

  public String passwordValue() {
    return password.value();
  }

  public String roleName() {
    return role.name();
  }

  public String statusName() {
    return status.name();
>>>>>>> refactoring-clean-code
  }
}
