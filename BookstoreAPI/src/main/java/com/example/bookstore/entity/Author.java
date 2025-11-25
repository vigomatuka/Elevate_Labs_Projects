package com.example.bookstore.entity;
import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Book> books;

    public Author(){}
    public Author(String name, String email){
        this.name = name;
    }

    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public List<Book> getBooks(){return books;}
    public void setBooks(List<Book> books){this.books = books;}
}
