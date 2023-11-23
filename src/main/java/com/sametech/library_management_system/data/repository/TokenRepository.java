package com.sametech.library_management_system.data.repository;

import com.sametech.library_management_system.data.models.token.Token;
import com.sametech.library_management_system.data.models.users.AppUser;
import com.sametech.library_management_system.data.models.users.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("""
select t from Token t inner join AppUser au on t.appUser.id = au.id
where au.id = :appUserId and (t.expired = false  or t.revoked = false)
"""
    )
    List<Token> findValidTokenByAppUserId(Long appUserId);
    Optional<Token> findByToken(String token);
    Optional<Token> findTokenByAppUserAndToken(AppUser appUser, String token);

    Optional<Token> findTokenByAppUser(AppUser appUser);


}
