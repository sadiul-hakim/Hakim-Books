package org.learn.book_management_system.book;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.learn.book_management_system.author.Author;
import org.learn.book_management_system.author.AuthorService;
import org.learn.book_management_system.user.UserModel;
import org.learn.book_management_system.user.UserService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final UserService userService;

    public Optional<BookDTO> save(BookDTO book) {
        try {
            if (book == null) {
                log.warn("BookService.save() :: book is null.");
                return Optional.empty();
            }

            Book model = convertToModel(book);
            log.warn("BookService.save() :: Saving Book {}", book.title());
            Book save = bookRepository.save(model);
            log.warn("BookService.save() :: Done Saving Book {}", book.title());
            return convertToDTO(save);
        } catch (Exception ex) {
            log.warn("BookService.save() :: Failed to Save Book.");
            return Optional.empty();
        }
    }

    private Book convertToModel(BookDTO dto) {
        Book book = new Book();
        book.setTitle(dto.title());
        book.setPages(dto.pages());
        book.setReadPages(dto.readPages());
        book.setPrice(dto.price());

        Set<Integer> authorIds = dto.authors();
        Set<Author> authors = new HashSet<>();
        if (authorIds != null) {
            authorIds.forEach(id -> {
                Optional<Author> author = authorService.findModelById(id);
                if (author.isEmpty()) {
                    log.warn("BookService.convertToModel() :: Could not find author by id {}", id);
                    return;
                }

                authors.add(author.get());
            });
        }
        book.setAuthors(authors);

        Set<Integer> ownerIds = dto.owners();
        Set<UserModel> owners = new HashSet<>();
        if (ownerIds != null) {
            ownerIds.forEach(id -> {
                Optional<UserModel> owner = userService.findModelById(id);
                if (owner.isEmpty()) {
                    log.warn("BookService.convertToModel() :: Could not find owner by id {}", id);
                    return;
                }

                owners.add(owner.get());
            });
        }
        book.setOwners(owners);

        return book;
    }

    public Optional<BookDTO> convertToDTO(Book model) {
        Set<Author> authorModels = model.getAuthors();
        Set<UserModel> ownerModels = model.getOwners();

        Set<Integer> authors = authorModels.stream().map(Author::getId).collect(Collectors.toSet());
        Set<Integer> owners = ownerModels.stream().map(UserModel::getId).collect(Collectors.toSet());


        return Optional.of(new BookDTO(model.getId(), model.getTitle(), model.getPages(), model.getReadPages(), model.getPrice(),
                authors, owners, model.getLastRead().toLocalDateTime(),
                model.getPublishDate().toLocalDateTime()));
    }
}
