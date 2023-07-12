package com.sametech.library_management_system.data.models.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String isbn;
    private String description;
    private String dateAdded;
    private Genre genre;
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Author> authors;
}
