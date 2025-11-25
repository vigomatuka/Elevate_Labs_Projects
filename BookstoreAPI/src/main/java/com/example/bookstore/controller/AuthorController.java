package com.example.bookstore.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.bookstore.entity.Author;
import com.example.bookstore.service.AuthorService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping
    public List<Author> getAllAuthors(){
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id){
        Optional<Author> author = authorService.getAuthorById(id);
        return author.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Author> addAuthor(@RequestBody Author author){
        Author saved = authorService.saveAuthor(author);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id0}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody Author updatedAuthor){
        Optional<Author> current = authorService.getAuthorById(id);
        if (current.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Author author = current.get();
        author.setName(updatedAuthor.getName());
        author.setBooks(updatedAuthor.getBooks());

        Author saved = authorService.saveAuthor(author);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id){
        if (authorService.getAuthorById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Author> searchAuthors(@RequestParam String name){
        return authorService.searchAuthorsByName(name);
    }


}
