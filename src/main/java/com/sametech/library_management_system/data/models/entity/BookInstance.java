package com.sametech.library_management_system.data.models.entity;


import com.sametech.library_management_system.data.models.users.Librarian;
import com.sametech.library_management_system.data.models.users.LibraryUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class BookInstance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String imprint;
    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> books;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private LibraryUser borrowedBy;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Librarian approvedBy;
    private LocalDateTime borrowedDate;
}
