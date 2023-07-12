package com.sametech.library_management_system.data.models.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LibraryUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phoneNumber;
    @OneToOne(targetEntity = LibraryUser.class, fetch = FetchType.EAGER)
//    @JoinColumn(name = "library_user_id")
    private Address address;
    private Gender gender;
    private int age;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private AppUser userDetails;
}
