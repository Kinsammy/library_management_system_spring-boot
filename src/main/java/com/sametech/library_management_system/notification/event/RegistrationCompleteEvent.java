package com.sametech.library_management_system.notification.event;

import com.sametech.library_management_system.data.models.users.AppUser;
import org.springframework.context.ApplicationEvent;

public class RegistrationCompleteEvent extends ApplicationEvent {
    private AppUser user;
    private String applicationUrl;

    public RegistrationCompleteEvent(AppUser user, String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;
    }
}
