package com.diego.bookstore.service;

import com.diego.bookstore.model.Book;
import com.diego.bookstore.model.Author;
import com.diego.bookstore.repository.BookRepository;
import com.diego.bookstore.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Livro não encontrado: " + id)
        );
    }

    public Book create(String title, String genre, Integer publication_year, Long authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(
            () -> new RuntimeException("Autor não encontrado: " + authorId)
        );
        Book book = new Book(null, title, genre, publication_year, author);
        return bookRepository.save(book);
    }
}