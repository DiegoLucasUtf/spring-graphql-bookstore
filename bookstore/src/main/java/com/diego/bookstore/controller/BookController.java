package com.diego.bookstore.controller;

import com.diego.bookstore.model.Author;
import com.diego.bookstore.model.Book;
import com.diego.bookstore.service.AuthorService;
import com.diego.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    @QueryMapping             
    public List<Book> allBooks() {
        return bookService.findAll();
    }

    @QueryMapping
    public Book bookById(@Argument Long id) { 
        return bookService.findById(id);
    }

    @QueryMapping
    public List<Author> allAuthors() {
        return authorService.findAll();
    }

    @QueryMapping
    public Author authorById(@Argument Long id) {
        return authorService.findById(id);
    }

    @MutationMapping           
    public Author createAuthor(@Argument String name,
                               @Argument String nationality) {
        return authorService.create(name, nationality);
    }

    @MutationMapping
    public Book createBook(@Argument String title,
                           @Argument String genre,
                           @Argument Integer publication_year,
                           @Argument Long authorId) {
        return bookService.create(title, genre, publication_year, authorId);
    }
}