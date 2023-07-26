package com.sametech.library_management_system.data.dto.request;

import com.sametech.library_management_system.data.models.entity.Author;
import com.sametech.library_management_system.data.models.entity.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {
    private Long id;
    private String title;
    private String isbn;
    private String description;
    private Genre genre;
    private Author author;
}
