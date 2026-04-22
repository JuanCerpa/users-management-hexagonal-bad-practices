package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io;

import com.jcaa.usersmanagement.domain.enums.UserStatus;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UserResponse;
import java.util.List;
<<<<<<< HEAD
import java.util.Map;
import java.util.Objects;
=======
>>>>>>> refactoring-clean-code
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UserResponsePrinter {

  private static final String SEPARATOR = "-".repeat(52);
  private static final String ROW_FORMAT = "  %-10s : %s%n";

  private static final Map<String, String> STATUS_LABELS = Map.of(
      "ACTIVE", "Activo",
      "INACTIVE", "Inactivo",
      "PENDING", "Pendiente de activacion",
      "BLOCKED", "Bloqueado",
      "DELETED", "Eliminado"
  );

  private final ConsoleIO console;

  public void print(final UserResponse response) {
    console.println(SEPARATOR);
<<<<<<< HEAD
    console.printf(ROW_FORMAT, "ID", response.id());
    console.printf(ROW_FORMAT, "Name", response.name());
    console.printf(ROW_FORMAT, "Email", response.email());
    console.printf(ROW_FORMAT, "Role", response.role());
    console.printf(ROW_FORMAT, "Status", getStatusLabel(response.status()));
=======
    console.printf(ROW_FORMAT, "ID",     response.id());
    console.printf(ROW_FORMAT, "Name",   response.name());
    console.printf(ROW_FORMAT, "Email",  response.email());
    console.printf(ROW_FORMAT, "Status", UserStatus.fromString(response.status()).getLabel());
>>>>>>> refactoring-clean-code
    console.println(SEPARATOR);
  }

  public void printList(final List<UserResponse> users) {
    if (users.isEmpty()) {
      console.println("  No users found.");
      return;
    }
    console.printf("%n  Total: %d user(s)%n", users.size());
    users.forEach(this::print);
  }

  public void printSummary(final List<UserResponse> users) {
<<<<<<< HEAD
    if (Objects.isNull(users) || users.isEmpty()) {
      console.println("  No users found.");
      return;
    }

    users.forEach(u -> console.printf("  %s (%s)%n", u.name(), getStatusLabel(u.status())));
  }

  private static String getStatusLabel(final String status) {
    return STATUS_LABELS.getOrDefault(status, "Estado desconocido");
=======
    if (users == null || users.isEmpty()) {
      console.println("  No users found.");
      return;
    }
    users.forEach(u -> 
      console.printf("  %s (%s)%n", u.name(), UserStatus.fromString(u.status()).getLabel())
    );
>>>>>>> refactoring-clean-code
  }
}
