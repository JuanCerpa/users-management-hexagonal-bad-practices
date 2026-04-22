package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.domain.exception.UserNotFoundException;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.UserResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.UserController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UpdateUserRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UserResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UpdateUserHandler implements OperationHandler {

  private final UserController userController;
  private final ConsoleIO console;
  private final UserResponsePrinter printer;

  @Override
  public void handle() {
<<<<<<< HEAD
    final String id = console.readRequired("User ID                                       : ");
    final String name = console.readRequired("New name                                      : ");
    final String email = console.readRequired("New email                                     : ");
=======
    final String id   = console.readRequired("User ID                                       : ");
    final String name = console.readRequired("New name                                      : ");
    final String email= console.readRequired("New email                                     : ");
>>>>>>> refactoring-clean-code
    final String password = console.readOptional("New password (leave blank to keep current)    : ");
    final String role = console.readRequired("Role   (ADMIN / MEMBER / REVIEWER)            : ");
    final String status = console.readRequired("Status (ACTIVE / INACTIVE / PENDING / BLOCKED): ");

    try {
<<<<<<< HEAD
      final UserResponse updatedUser = userController.updateUser(
=======
      userController.updateUser(
>>>>>>> refactoring-clean-code
          new UpdateUserRequest(
              id,
              name,
              email,
              password.isBlank() ? null : password,
              role,
              status));
      console.println("\n  User updated successfully.");
<<<<<<< HEAD
      printer.print(updatedUser);
=======
      // Fetching the user to show updated details (CQS: command followed by query)
      final UserResponse updated = userController.findUserById(id);
      printer.print(updated);
>>>>>>> refactoring-clean-code
    } catch (final UserNotFoundException exception) {
      console.println("  Not found: " + exception.getMessage());
    }
  }
}
