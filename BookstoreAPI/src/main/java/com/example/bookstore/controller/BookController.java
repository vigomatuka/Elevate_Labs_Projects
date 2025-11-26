package com.example.bookstore.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.bookstore.entity.Book;
import com.example.bookstore.service.BookService;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        Optional<Book> book = bookService.getBookById(id);
        return book.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        Book saved = bookService.saveBook(book);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook){
        Optional<Book> current = bookService.getBookById(id);
        if (current.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Book book = current.get();
        book.setTitle(updatedBook.getTitle());
        book.setPrice(updatedBook.getPrice());
        book.setAuthor(updatedBook.getAuthor());

        Book saved = bookService.saveBook(book);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        if (bookService.getBookById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String title){
        return bookService.searchBookByTitle(title);
    }

    @GetMapping("/author/{authorId}")
    public List<Book> getBooksByAuthor(@PathVariable Long authorId){
        return bookService.getBooksByAuthorId(authorId);
    }

    @GetMapping("/page")
    public Page<Book> getBooksPaginated(
        @PageableDefault(
            size = 2,
            sort = "title",
            direction = Sort.Direction.ASC
        ) Pageable pageable
    ) {
        return bookService.getBooksPage(pageable);
    }
    @GetMapping("/search/page")
    public Page<Book> searchBooks(
        @RequestParam String keyword,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "title") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return bookService.searchBooks(keyword, pageable);
    }
}
