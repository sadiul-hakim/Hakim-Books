package org.learn.book_management_system.book;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.learn.book_management_system.author.Author;
import org.learn.book_management_system.user.UserModel;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private int pages;
    private int readPages;
    private int price;

    @ManyToMany(mappedBy = "books")
    private Set<Author> authors = new HashSet<>();

    @ManyToMany(mappedBy = "owned_books")
    private Set<UserModel> owners = new HashSet<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp lastRead;
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp publishDate;
}
