package com.sametech.library_management_system.notification.listener;

import com.sametech.library_management_system.data.dto.request.EmailNotificationRequest;
import com.sametech.library_management_system.data.dto.request.Recipient;
import com.sametech.library_management_system.data.models.users.AppUser;
import com.sametech.library_management_system.exception.LibraryLogicException;
import com.sametech.library_management_system.notification.event.RegistrationCompleteEvent;
import com.sametech.library_management_system.notification.mail.IMailService;
import com.sametech.library_management_system.notification.mail.MailService;
import com.sametech.library_management_system.service.serviceImplementation.AppUserService;
import com.sametech.library_management_system.service.serviceImplementation.TokenService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteListener implements ApplicationListener<RegistrationCompleteEvent> {
    private final AppUserService userService;

    private final IMailService mailService;
    private final TokenService tokenService;

    private AppUser user;



    @Override
    public void onApplicationEvent(@NotNull  RegistrationCompleteEvent event) {
        log.info("Sending verification token...");
        user = event.getUser();
        String userEmail = userService.getUserByEmail(user.getEmail()).getEmail();
        String token = tokenService.generateAndSaveToken(event.getUser());
        String verificationUrl = "http://localhost:5252/api/v1/auth/verify?email=" + userEmail + ",token=" + token;


        try {
          this.sendVerificationEmail(verificationUrl);
      } catch (IOException exception) {
          throw new LibraryLogicException(exception.getMessage());
      }
    }

    private void sendVerificationEmail(String verificationUrl) throws IOException {
        EmailNotificationRequest request = new EmailNotificationRequest();
        request.setSubject("Email verification");
        request.setHtmlContent("<h2>Hi " + user.getFirstName() + ", </h2>" +
                "<p>Welcome To SamTech Library, Thanks for joining the team. </p>" +
                "<p>Please, follow the link below to complete your registration.</p>" +
                "<p><a href=\"" + verificationUrl + "\">Verify your email and activate your account</a></p>" +

                "<p>Thank you<br>users Registration Portal Service</p>");

        request.getTo().add(new Recipient(user.getFirstName(), user.getEmail()));
        mailService.sendMail(request);
    }


}
