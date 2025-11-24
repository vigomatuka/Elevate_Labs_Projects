package com.example.bookstore.entity;
import jakarta.persistence.*;
import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;

    private String email;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Book> books;

    public Author(){}
    public Author(String name, String email){
        this.name = name;
        this.email = email;
    }

    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}

    public List<Book> getBooks(){return books;}
    public void setBooks(List<Book> books){this.books = books;}
}
