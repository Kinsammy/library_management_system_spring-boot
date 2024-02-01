package com.sametech.library_management_system.notification.publisher;

import com.sametech.library_management_system.data.models.users.AppUser;
import com.sametech.library_management_system.notification.event.RegistrationCompleteEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class AppUserEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public void publish(final AppUser user) {
        log.info("Publishing Custom event...");
        final RegistrationCompleteEvent verifyEmail = new RegistrationCompleteEvent(this, user);
    }
}
