package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import com.jcaa.usersmanagement.application.port.out.EmailSenderPort;
import com.jcaa.usersmanagement.domain.enums.UserRole;
import com.jcaa.usersmanagement.domain.enums.UserStatus;
import com.jcaa.usersmanagement.domain.exception.EmailSenderException;
import com.jcaa.usersmanagement.domain.model.UserModel;
import com.jcaa.usersmanagement.domain.valueobject.UserEmail;
import com.jcaa.usersmanagement.domain.valueobject.UserId;
import com.jcaa.usersmanagement.domain.valueobject.UserName;
import com.jcaa.usersmanagement.domain.valueobject.UserPassword;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("EmailNotificationService")
@ExtendWith(MockitoExtension.class)
class EmailNotificationServiceTest {

  @Mock private EmailSenderPort emailSenderPort;
  @Mock private EmailSenderPort spyEmailSenderPort;

  private EmailNotificationService service;
  private EmailNotificationService serviceSpy;

  private static final String EMAIL = "john@example.com";
  private static final String NAME = "John Arrieta";
  private static final String PASSWORD = "SecurePass1";
  private static final String TEMPLATE_CONTENT =
      "<html>{{name}} {{email}} {{password}} {{role}} {{status}}</html>";

  private UserModel user;

  @BeforeEach
  void setUp() {
    service = new EmailNotificationService(emailSenderPort);
    serviceSpy = spy(new EmailNotificationService(spyEmailSenderPort));
    user =
        new UserModel(
            new UserId("u-001"),
            new UserName(NAME),
            new UserEmail(EMAIL),
            UserPassword.fromPlainText(PASSWORD),
            UserRole.ADMIN,
            UserStatus.ACTIVE);
  }

  @Test
  @DisplayName("notifyUserCreated() sends email to correct destination")
  void shouldSendCreatedNotificationToCorrectEmail() {
    // Act
    service.notifyUserCreated(user, PASSWORD);

    // Assert
    verify(emailSenderPort)
        .send(
            argThat(
                destination ->
                    EMAIL.equals(destination.getDestinationEmail())
                        && destination.getSubject().contains("creada")));
  }

  @Test
  @DisplayName("notifyUserUpdated() sends email to correct destination")
  void shouldSendUpdatedNotificationToCorrectEmail() {
    // Act
    service.notifyUserUpdated(user);

    // Assert
    verify(emailSenderPort)
        .send(
            argThat(
                destination ->
                    EMAIL.equals(destination.getDestinationEmail())
                        && destination.getSubject().contains("actualizada")));
  }

  @Test
  @DisplayName("notifyUserCreated() rethrows EmailSenderException when output port fails")
  void shouldRethrowEmailSenderExceptionOnCreate() {
    // Arrange
    final EmailSenderException cause =
        EmailSenderException.becauseSmtpFailed(EMAIL, "Connection refused");
    doThrow(cause).when(emailSenderPort).send(any());

    // Act & Assert
    assertThrows(EmailSenderException.class, () -> service.notifyUserCreated(user, PASSWORD));
  }

  @Test
  @DisplayName("notifyUserUpdated() rethrows EmailSenderException when output port fails")
  void shouldRethrowEmailSenderExceptionOnUpdate() {
    // Arrange
    final EmailSenderException cause =
        EmailSenderException.becauseSmtpFailed(EMAIL, "Connection refused");
    doThrow(cause).when(emailSenderPort).send(any());

    // Act & Assert
    assertThrows(EmailSenderException.class, () -> service.notifyUserUpdated(user));
  }

  @Test
  @DisplayName("loadTemplate() throws EmailSenderException when template is missing")
  void shouldThrowWhenTemplateNotFound() {
    // Arrange
    doReturn(null).when(serviceSpy).openResourceStream(any());

    // Act & Assert
    assertThrows(EmailSenderException.class, () -> serviceSpy.notifyUserCreated(user, PASSWORD));
  }

  @Test
  @DisplayName("loadTemplate() throws EmailSenderException when IOException occurs")
  void shouldThrowWhenTemplateThrowsIOException() throws IOException {
    // Arrange
    final InputStream brokenStream = mock(InputStream.class);
    doThrow(new IOException("Disk error")).when(brokenStream).readAllBytes();
    doReturn(brokenStream).when(serviceSpy).openResourceStream(any());

    // Act & Assert
    assertThrows(EmailSenderException.class, () -> serviceSpy.notifyUserCreated(user, PASSWORD));
  }

  @Test
  @DisplayName("renderTemplate() replaces all tokens with expected values")
  void shouldRenderAllTokensInTemplate() {
    // Arrange
    final InputStream templateStream =
        new ByteArrayInputStream(TEMPLATE_CONTENT.getBytes(StandardCharsets.UTF_8));
    doReturn(templateStream).when(serviceSpy).openResourceStream(any());

    // Act
    serviceSpy.notifyUserCreated(user, PASSWORD);

    // Assert
    verify(spyEmailSenderPort)
        .send(
            argThat(
                destination ->
                    destination.getBody().contains(NAME) && destination.getBody().contains(EMAIL)));
  }
}

