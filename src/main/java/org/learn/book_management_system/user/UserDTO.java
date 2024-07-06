package org.learn.book_management_system.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private int id;
    private String fullName;
    private String email;
    private String password;
    private String photo;
    private String thoughts;
    private String role;
    private String startDate;
    private Set<Integer> owned_books;
}
