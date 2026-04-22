package com.jcaa.usersmanagement.domain.model;

import java.util.Objects;
import lombok.Value;

@Value
public class EmailDestinationModel {

<<<<<<< HEAD
  private static final String DESTINATION_EMAIL_REQUIRED_MESSAGE =
      "El email del destinatario es requerido.";
  private static final String DESTINATION_NAME_REQUIRED_MESSAGE =
      "El nombre del destinatario es requerido.";
  private static final String SUBJECT_REQUIRED_MESSAGE = "El asunto es requerido.";
  private static final String BODY_REQUIRED_MESSAGE = "El cuerpo del mensaje es requerido.";
=======
  private static final String MSG_EMAIL_REQUIRED = "El email del destinatario es requerido.";
  private static final String MSG_NAME_REQUIRED = "El nombre del destinatario es requerido.";
  private static final String MSG_SUBJECT_REQUIRED = "El asunto es requerido.";
  private static final String MSG_BODY_REQUIRED = "El cuerpo del mensaje es requerido.";
>>>>>>> refactoring-clean-code

  String destinationEmail;
  String destinationName;
  String subject;
  String body;

  public EmailDestinationModel(
      final String destinationEmail,
      final String destinationName,
      final String subject,
      final String body) {
<<<<<<< HEAD
    this.destinationEmail = validateNotBlank(destinationEmail, DESTINATION_EMAIL_REQUIRED_MESSAGE);
    this.destinationName = validateNotBlank(destinationName, DESTINATION_NAME_REQUIRED_MESSAGE);
    this.subject = validateNotBlank(subject, SUBJECT_REQUIRED_MESSAGE);
    this.body = validateNotBlank(body, BODY_REQUIRED_MESSAGE);
  }

  private static String validateNotBlank(final String value, final String errorMessage) {
    final String normalizedValue = Objects.requireNonNull(value, errorMessage).trim();
    if (normalizedValue.isEmpty()) {
=======
    this.destinationEmail = validateNotBlank(destinationEmail, MSG_EMAIL_REQUIRED);
    this.destinationName  = validateNotBlank(destinationName,  MSG_NAME_REQUIRED);
    this.subject          = validateNotBlank(subject,          MSG_SUBJECT_REQUIRED);
    this.body             = validateNotBlank(body,             MSG_BODY_REQUIRED);
  }

  private static String validateNotBlank(final String value, final String errorMessage) {
    if (Objects.isNull(value)) {
      throw new NullPointerException(errorMessage);
    }
    if (value.trim().isEmpty()) {
>>>>>>> refactoring-clean-code
      throw new IllegalArgumentException(errorMessage);
    }
    return normalizedValue;
  }
}
