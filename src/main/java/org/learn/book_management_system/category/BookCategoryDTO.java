package org.learn.book_management_system.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookCategoryDTO {
    private int id;
    private String name;
    private String description;
}
