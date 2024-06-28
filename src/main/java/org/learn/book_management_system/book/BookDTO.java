package org.learn.book_management_system.book;

import java.time.LocalDateTime;
import java.util.Set;

public record BookDTO(
        int id,
        String title,
        int pages,
        int readPages,
        int price,
        Set<Integer> authors,
        Set<Integer> owners,
        LocalDateTime lastRead,
        LocalDateTime publishDate
) {
}
