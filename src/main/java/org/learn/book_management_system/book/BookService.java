package org.learn.book_management_system.book;

import lombok.extern.slf4j.Slf4j;
import org.learn.book_management_system.category.BookCategory;
import org.learn.book_management_system.category.BookCategoryService;
import org.learn.book_management_system.user.UserDTO;
import org.learn.book_management_system.user.UserService;
import org.learn.book_management_system.util.DateUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookCategoryService categoryService;
    private final UserService userService;

    public BookService(BookRepository bookRepository, BookCategoryService categoryService, @Lazy UserService userService) {
        this.bookRepository = bookRepository;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    public Optional<BookDTO> save(BookDTO book) {
        try {
            if (book == null) {
                log.warn("BookService.save() :: book is null.");
                return Optional.empty();
            }

            Book model = convertToModel(book);
            if (model == null) {
                log.warn("BookService.save() :: Model is null");
                return Optional.empty();
            }

            log.warn("BookService.save() :: Saving Book {}", book.getTitle());
            Book save = bookRepository.save(model);
            log.warn("BookService.save() :: Done Saving Book {}", book.getTitle());
            return convertToDTO(save);
        } catch (Exception ex) {
            log.warn("BookService.save() :: Failed to Save Book.");
            return Optional.empty();
        }
    }

    public boolean deleteById(int id) {
        log.info("BookService.deleteById() :: Deleting book by id {}", id);

        bookRepository.deleteById(id);
        return true;
    }

    public Optional<BookDTO> getById(int id) {
        log.info("BookService.getById() :: Getting book by id {}", id);

        Optional<Book> model = bookRepository.findById(id);
        if (model.isEmpty()) {
            log.warn("Could not find book by id {}", id);
        }
        return convertToDTO(model.get());
    }

    public List<BookDTO> getAll() {
        log.info("BookService.getAll() :: getting all books.");
        return convertList(bookRepository.findAll());
    }

    public Set<BookDTO> getAllOfLoggedInUser() {
        log.info("BookService.getAllOfLoggedInUser() :: getting all books.");

        Optional<UserDTO> authenticatedUser = userService.getAuthenticatedUser();
        if (authenticatedUser.isEmpty()) {
            log.warn("BookService.getAllOfLoggedInUser() :: could not get authenticated user!");
            return Collections.emptySet();
        }

        Set<Integer> bookIds = authenticatedUser.get().getOwned_books();
        Set<BookDTO> bookList = new HashSet<>();
        bookIds.forEach(id -> {
            Optional<BookDTO> optional = getById(id);
            optional.ifPresentOrElse(bookList::add, () -> {
                log.warn("BookService.getAllOfLoggedInUser() :: could not get book {}!", id);
            });
        });

        return bookList;
    }

    private List<BookDTO> convertList(List<Book> books) {
        List<BookDTO> dtos = new ArrayList<>();
        books.forEach(book -> {
            Optional<BookDTO> dto = convertToDTO(book);
            dto.ifPresent(dtos::add);
        });

        return dtos;
    }

    private Book convertToModel(BookDTO dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setPages(dto.getPages());
        book.setReadPages(dto.getReadPages());
        book.setPrice(dto.getPrice());

        String categoryName = dto.getCategory();
        Optional<BookCategory> category = categoryService.findModelByName(categoryName);
        if (category.isEmpty()) {
            log.warn("BookService.convertToModel() :: could not find category {}", categoryName);
            return null;
        }
        book.setCategory(category.get());

        Set<String> authorIds = dto.getAuthors();
        book.setAuthors(authorIds);

        return book;
    }

    public Optional<BookDTO> convertToDTO(Book model) {
        Set<String> authors = model.getAuthors();

        return Optional.of(new BookDTO(model.getId(), model.getTitle(), model.getPages(),
                model.getReadPages(), model.getPrice(), model.getCategory().getName(),
                authors, DateUtil.forGUI(model.getLastRead().toLocalDateTime()),
                DateUtil.forGUI(model.getPublishDate().toLocalDateTime())));
    }
}
