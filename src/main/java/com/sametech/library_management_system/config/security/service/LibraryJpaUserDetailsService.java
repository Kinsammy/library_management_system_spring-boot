package com.sametech.library_management_system.config.security.service;

import com.sametech.library_management_system.config.security.user.details.LibrarySecuredUser;
import com.sametech.library_management_system.data.models.users.AppUser;
import com.sametech.library_management_system.service.serviceInterface.IAppUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class LibraryJpaUserDetailsService  implements UserDetailsService {
    private final IAppUserService appUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserService.getUserByEmail(username);
        return new LibrarySecuredUser(user);
    }
}
