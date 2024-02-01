package com.sametech.library_management_system.notification.listener;

import com.sametech.library_management_system.data.dto.request.EmailNotificationRequest;
import com.sametech.library_management_system.data.dto.request.Recipient;
import com.sametech.library_management_system.data.models.users.AppUser;
import com.sametech.library_management_system.notification.event.RegistrationCompleteEvent;
import com.sametech.library_management_system.notification.mail.IMailService;
import com.sametech.library_management_system.notification.mail.MailService;
import com.sametech.library_management_system.service.serviceImplementation.AppUserService;
import com.sametech.library_management_system.service.serviceImplementation.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteListener implements ApplicationListener<RegistrationCompleteEvent> {
    private final AppUserService userService;

    private final IMailService mailService;
    private final TokenService tokenService;


    private AppUser user;


    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        user =  event.getUser();

        String verificationToken = tokenService.generateAndSaveToken(user);

        String url = event.getApplicationUrl()+"/api/v1/auth/verify?token="+verificationToken;

        sendVerificationEmail(url);
    }

    private void sendVerificationEmail(String url) {
        EmailNotificationRequest request = new EmailNotificationRequest();
        request.setSubject("Email verification");
        request.setHtmlContent("<h4>, " + user.getFirstName() + ", </h4>" +
                "<h6>Welcome To SamLibrary, Thanks for joining the team. </h6>" +
                "<p>Please, follow the link below to complete your registration.</p>" +
                "<p><a href=\"" + url + "\">Verify your email and activate your account</a></p>" +

                "<p>Thank you<br>users Registration Portal Service</p>");

        request.getTo().add(new Recipient(user.getFirstName(), user.getEmail()));
        mailService.sendMail(request);
    }


}
