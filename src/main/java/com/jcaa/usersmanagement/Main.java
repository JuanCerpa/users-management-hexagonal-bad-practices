package com.jcaa.usersmanagement;

import com.jcaa.usersmanagement.infrastructure.config.DependencyContainer;
import com.jcaa.usersmanagement.infrastructure.entrypoint.ApplicationEntryPoint;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.UserManagementCli;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import java.util.Scanner;
import java.util.logging.Logger;

public final class Main {

  private static final Logger log = Logger.getLogger(Main.class.getName());

  public static void main(final String[] args) {
    log.info("Starting Users Management System...");
    
    final DependencyContainer container = buildContainer();
    final ApplicationEntryPoint entryPoint = buildEntryPoint(container);
    
    entryPoint.start();
  }

  private static DependencyContainer buildContainer() {
    return new DependencyContainer();
  }

  private static ApplicationEntryPoint buildEntryPoint(final DependencyContainer container) {
    final Scanner scanner = new Scanner(System.in);
    final ConsoleIO console = new ConsoleIO(scanner, System.out);
    return new UserManagementCli(container.userController(), console);
  }
}