package com.sametech.library_management_system.notification.mail;

import com.sametech.library_management_system.data.dto.request.EmailNotificationRequest;

public interface IMailService {
    String sendMail(EmailNotificationRequest request);
}
