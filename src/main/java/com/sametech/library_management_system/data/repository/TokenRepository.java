package com.sametech.library_management_system.data.repository;

import com.sametech.library_management_system.data.models.token.Token;
import com.sametech.library_management_system.data.models.users.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("""
select t from Token t inner join LibraryUser l on t.libraryUser.id = l.id
where l.id = :libraryUserId and (t.expired = false  or t.revoked=false)
"""

    )
    List<Token> findAllValidTokensByLibraryUserId(Long libraryUserId);
    Optional<Token> findByToken(String token);
    Optional<Token> findTokenByLibraryUserAndToken(LibraryUser libraryUser, String token);
    Optional<Token> findTokenByLibraryUser(LibraryUser libraryUser);
}
