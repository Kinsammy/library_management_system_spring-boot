package com.sametech.library_management_system.data.models.token;

import com.sametech.library_management_system.data.models.users.LibraryUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    private boolean expired;
    private boolean revoked;
    private final LocalDateTime createAt = LocalDateTime.now();
    private final LocalDateTime expiryTime = createAt.plusMinutes(5);
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "user_details_id")
    private LibraryUser  libraryUser;

    public Token(LibraryUser libraryUser, String token){
        super();
        this.libraryUser = libraryUser;
        this.token = token;
    }
}
