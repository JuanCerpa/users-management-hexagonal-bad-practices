package com.jcaa.usersmanagement.infrastructure.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public final class AppProperties {

  private static final String PROPERTIES_FILE = "application.properties";
  private static final String FILE_NOT_FOUND_MESSAGE = "File not found in classpath: %s";
  private static final String PROPERTY_NOT_FOUND_MESSAGE = "Property not found in %s: %s";

  private final Properties properties;

  public AppProperties() {
    this(AppProperties.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));
  }

  // Package-private — test entry point
  AppProperties(final InputStream stream) {
    this.properties = doLoad(stream);
  }

  private static Properties doLoad(final InputStream stream) {
<<<<<<< HEAD
    final InputStream nonNullStream =
        Objects.requireNonNull(stream, String.format(FILE_NOT_FOUND_MESSAGE, PROPERTIES_FILE));

    final Properties loadedProperties = new Properties();
    try (nonNullStream) {
      loadedProperties.load(nonNullStream);
=======
    if (Objects.isNull(stream)) {
      throw new NullPointerException("File not found in classpath: " + PROPERTIES_FILE);
    }
    final Properties loadedProperties = new Properties();
    try (stream) {
      loadedProperties.load(stream);
>>>>>>> refactoring-clean-code
    } catch (final IOException exception) {
      throw ConfigurationException.becauseLoadFailed(exception);
    }
    return loadedProperties;
  }

  public String get(final String key) {
    final String value = properties.getProperty(key);
<<<<<<< HEAD
    return Objects.requireNonNull(
        value, String.format(PROPERTY_NOT_FOUND_MESSAGE, PROPERTIES_FILE, key));
=======
    if (Objects.isNull(value)) {
      throw new NullPointerException("Property not found in " + PROPERTIES_FILE + ": " + key);
    }
    return value;
>>>>>>> refactoring-clean-code
  }

  public int getInt(final String key) {
    return Integer.parseInt(get(key));
  }
}
