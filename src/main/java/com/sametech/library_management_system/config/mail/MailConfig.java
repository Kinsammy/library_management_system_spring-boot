package com.sametech.library_management_system.config.mail;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MailConfig {
    private String apiKey;
    private String mailUrl;
}
