package com.driver.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(unique = true)
    private String email;

    private int age;
    private String country;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("author")
    private List<Book> booksWritten;

    public Author() {
    }

    public Author(String name, String email, int age, String country) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.country = country;
    }

//    public Author(String name, String email, int age, String country, List<Book> booksWritten) {
//        this.name = name;
//        this.email = email;
//        this.age = age;
//        this.country = country;
//        this.booksWritten = booksWritten;
//    }
}

