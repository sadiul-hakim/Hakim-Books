package org.learn.book_management_system.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.learn.book_management_system.role.Role;
import org.learn.book_management_system.util.IntegerSetConverter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    @Column(unique = true)
    private String email;

    @NotEmpty
    @Size(max = 200)
    private String password;

    @NotEmpty
    @Size(max = 200)
    private String photo;

    @Size(max = 500)
    private String thoughts;
    @ManyToOne
    private Role role;

    @Column(columnDefinition = "JSON")
    @Convert(converter = IntegerSetConverter.class)
    private Set<Integer> owned_books = new HashSet<>();

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp startDate = new Timestamp(System.currentTimeMillis());

    public void addBook(int book) {
        owned_books.add(book);
    }
}
