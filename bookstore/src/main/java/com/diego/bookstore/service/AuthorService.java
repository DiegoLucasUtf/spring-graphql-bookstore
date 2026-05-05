package com.diego.bookstore.service;

import com.diego.bookstore.model.Author;
import com.diego.bookstore.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public Author findById(Long id) {
        return authorRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Autor não encontrado: " + id)
        );
    }

    public Author create(String name, String nationality) {
        Author author = new Author(null, name, nationality, null);
        return authorRepository.save(author);
    }
}