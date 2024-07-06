package org.learn.book_management_system.book;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.learn.book_management_system.category.BookCategory;
import org.learn.book_management_system.util.StringListConverter;

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

    @Column(columnDefinition = "JSON")
    @Convert(converter = StringListConverter.class)
    private Set<String> authors = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private BookCategory category;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp lastRead = new Timestamp(System.currentTimeMillis());

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp publishDate = new Timestamp(System.currentTimeMillis());
}
