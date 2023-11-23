package com.sametech.library_management_system.data.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AdminInviteRequest {
    private String email;
    private String name;
}
