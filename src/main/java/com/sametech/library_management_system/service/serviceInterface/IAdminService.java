package com.sametech.library_management_system.service.serviceInterface;

import com.sametech.library_management_system.data.dto.request.AdminInviteRequest;
import com.sametech.library_management_system.data.dto.response.ApiResponse;

import java.util.Set;

public interface IAdminService {
    ApiResponse sendInviteRequests(Set<AdminInviteRequest> adminInviteRequest);
}
