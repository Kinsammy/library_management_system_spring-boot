package com.sametech.library_management_system.data.models.users;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    ADMIN_READ("admin:read"),
    ADMIN_CREATE("admin:create"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete"),
    LIBRARIAN_READ("librarian:read"),
    LIBRARIAN_CREATE("librarian:create"),
    LIBRARIAN_UPDATE("librarian:update"),
    LIBRARIAN_DELETE("librarian:delete")
    ;

    @Getter
    private final String permission;
}
