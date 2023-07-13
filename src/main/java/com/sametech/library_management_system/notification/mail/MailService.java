package com.sametech.library_management_system.notification.mail;

import com.sametech.library_management_system.config.mail.MailConfig;
import com.sametech.library_management_system.data.dto.request.EmailNotificationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@AllArgsConstructor
@Slf4j
public class MailService implements IMailService{
    private final MailConfig mailConfig;
    @Override
    public String sendMail(EmailNotificationRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key",mailConfig.getApiKey());
        HttpEntity<EmailNotificationRequest> requestHttpEntity = new HttpEntity<>(request, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(mailConfig.getMailUrl(), requestHttpEntity, String.class);
        return response.getBody();
    }
}
