package com.sametech.library_management_system.data.models.users;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sametech.library_management_system.data.models.users.Permission.*;


@RequiredArgsConstructor
public enum Role {
    LIBRARY_USER(Collections.emptySet()),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_CREATE,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    LIBRARIAN_READ,
                    LIBRARIAN_CREATE,
                    LIBRARIAN_UPDATE,
                    LIBRARIAN_DELETE
            )
    ),
    LIBRARIAN(
            Set.of(
                    LIBRARIAN_READ,
                    LIBRARIAN_CREATE,
                    LIBRARIAN_UPDATE,
                    LIBRARIAN_DELETE
            )
    )
    ;

    @Getter
    private final Set<Permission> permissions;


    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities  = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
