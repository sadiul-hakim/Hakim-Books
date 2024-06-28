package org.learn.book_management_system.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.learn.book_management_system.book.Book;
import org.learn.book_management_system.role.Role;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @Size(max = 55)
    private String fullName;

    @NotEmpty
    @Email
    @Size(max = 75)
    private String email;

    @NotEmpty
    @Size(max = 200)
    private String password;

    @NotEmpty
    @Size(max = 25)
    private String displayName;

    @Size(max = 500)
    private String thoughts;
    @ManyToOne
    private Role role;

    @ManyToMany
    private Set<Book> owned_books = new HashSet<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp startDate = new Timestamp(System.currentTimeMillis());
}
