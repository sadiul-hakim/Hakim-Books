package org.learn.book_management_system.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private int id;
    private String title;
    private int pages;
    private int readPages;
    private int price;
    private String category;
    private Set<String> authors = new HashSet<>();
    private String lastRead;
    private String publishDate;
}
