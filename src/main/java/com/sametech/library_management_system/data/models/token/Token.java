package com.sametech.library_management_system.data.models.token;

import com.sametech.library_management_system.data.models.users.AppUser;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    private boolean expired;
    private boolean revoked;
    private final LocalDateTime createAt = LocalDateTime.now();
    private final LocalDateTime expiryTime = createAt.plusMinutes(10);
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    public Token(AppUser appUser, String token){
        super();
        this.appUser = appUser;
        this.token = token;
    }
}
