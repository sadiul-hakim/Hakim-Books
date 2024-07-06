package org.learn.book_management_system.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface CategoryRepository extends JpaRepository<BookCategory,Integer> {
    Optional<BookCategory> findByName(String name);
}
