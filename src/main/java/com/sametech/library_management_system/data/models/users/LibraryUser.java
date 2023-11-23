package com.sametech.library_management_system.data.models.users;

import com.sametech.library_management_system.data.models.entity.BookInstance;
import com.sametech.library_management_system.data.models.token.Token;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LibraryUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String phoneNumber;
    @OneToOne(targetEntity = LibraryUser.class, fetch = FetchType.EAGER)
    private Address address;
    private Gender gender;
    private int age;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "app_user_id")
    private AppUser userDetails;
    @OneToMany(mappedBy = "borrowedBy", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BookInstance> bookInstances = new ArrayList<>();


    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof LibraryUser libraryUser)) return false;
        return Objects.equals(id, libraryUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
