package com.jcaa.usersmanagement.domain.exception;

public final class EmailSenderException extends DomainException {

<<<<<<< HEAD
  private static final String SMTP_FAILED_MESSAGE =
      "No se pudo enviar el correo a '%s'. Error SMTP: %s";
  private static final String SEND_FAILED_MESSAGE =
      "La notificación por correo no pudo ser enviada.";
=======
  private static final String MSG_SMTP_FAILED = "No se pudo enviar el correo a '%s'. Error SMTP: %s";
  private static final String MSG_SEND_FAILED = "La notificación por correo no pudo ser enviada.";
>>>>>>> refactoring-clean-code

  private EmailSenderException(final String message) {
    super(message);
  }

  private EmailSenderException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public static EmailSenderException becauseSmtpFailed(
      final String destinationEmail, final String smtpError) {
<<<<<<< HEAD
    return new EmailSenderException(String.format(SMTP_FAILED_MESSAGE, destinationEmail, smtpError));
  }

  public static EmailSenderException becauseSendFailed(final Throwable cause) {
    return new EmailSenderException(SEND_FAILED_MESSAGE, cause);
=======
    return new EmailSenderException(
        String.format(MSG_SMTP_FAILED, destinationEmail, smtpError));
  }

  public static EmailSenderException becauseSendFailed(final Throwable cause) {
    return new EmailSenderException(MSG_SEND_FAILED, cause);
>>>>>>> refactoring-clean-code
  }
}
