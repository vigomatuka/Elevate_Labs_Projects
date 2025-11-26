package com.example.bookstore.service;

import com.example.bookstore.entity.Book;
import com.example.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }
    public Optional<Book> getBookById(Long id){
        return bookRepository.findById(id);
    }
    public Book saveBook(Book book){
        return bookRepository.save(book);
    }
    public void deleteBook(Long id){
        bookRepository.deleteById(id);
    }
    public List<Book> searchBookByTitle(String keyword){
        return bookRepository.findByTitleContaining(keyword);
    }
    public List<Book> getBooksByAuthorId(Long authorId){
        return bookRepository.findByAuthorId(authorId);
    }
    public Page<Book> getBooksPage(Pageable pageable){
        return bookRepository.findAll(pageable);
    }
    public Page<Book> searchBooks(String title, Pageable pageable){
        return bookRepository.findByTitleContaining(title, pageable);
    }
}
