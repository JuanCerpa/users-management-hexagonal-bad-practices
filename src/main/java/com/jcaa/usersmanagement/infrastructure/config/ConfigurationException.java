package com.jcaa.usersmanagement.infrastructure.config;

public final class ConfigurationException extends RuntimeException {

<<<<<<< HEAD
  private static final String LOAD_CONFIGURATION_FAILED_MESSAGE =
      "Failed to load the application configuration.";
=======
  private static final String MSG_LOAD_FAILED = "Failed to load the application configuration.";
>>>>>>> refactoring-clean-code

  private ConfigurationException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public static ConfigurationException becauseLoadFailed(final Throwable cause) {
<<<<<<< HEAD
    return new ConfigurationException(LOAD_CONFIGURATION_FAILED_MESSAGE, cause);
=======
    return new ConfigurationException(MSG_LOAD_FAILED, cause);
>>>>>>> refactoring-clean-code
  }
}
