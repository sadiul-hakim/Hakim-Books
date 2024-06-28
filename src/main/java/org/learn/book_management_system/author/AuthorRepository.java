package org.learn.book_management_system.author;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface AuthorRepository extends JpaRepository<Author,Integer> {
    Optional<Author> findByFullName(String fullName);
}
