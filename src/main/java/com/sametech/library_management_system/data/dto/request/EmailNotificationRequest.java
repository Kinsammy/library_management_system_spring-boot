package com.sametech.library_management_system.data.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailNotificationRequest {
    private final Sender sender = new Sender("SamTech Library", "noreply@sametech.net");
    private List<Recipient> to = new ArrayList<>();
    private String subject;
    private String htmlContent;
}
