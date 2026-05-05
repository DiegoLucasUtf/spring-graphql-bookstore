package com.diego.bookstore.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String genre;
    private Integer publication_year;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
}