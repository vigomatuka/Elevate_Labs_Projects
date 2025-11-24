package com.example.bookstore.entity;
import jakarta.persistence.*;


@Entity //makes it a JPA entity (DB table)
@Table(name = "books") //table is named books
public class Book {
    @Id //pimary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment id
    private Long id;
    
    @Column(nullable = false)
    private String title;

    @Column(unique = true, nullable = false)
    private String isbn;

    Double price;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public Book(){} //JPA must have an empty constructor
    public Book(String title, String isbn, Double price, Author author){
        this.title = title;
        this.isbn = isbn;
        this.price = price;
        this.author = author;
    }

    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    public String getTitle(){return title;}
    public void setTitle(String title){this.title = title;}

    public Double getPrice(){return price;}
    public void setPrice(Double price){this.price = price;}

    public Author getAuthor(){return author;}
    public void setAuthor(Author author){this.author = author;}
}
