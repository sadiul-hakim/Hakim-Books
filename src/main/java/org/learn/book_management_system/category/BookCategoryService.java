package org.learn.book_management_system.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookCategoryService {
    private final CategoryRepository categoryRepository;

    public Optional<BookCategoryDTO> save(BookCategoryDTO category) {
        if (category == null) {
            log.warn("BookCategoryService.save() :: category is null");
            return Optional.empty();
        }

        log.info("BookCategoryService.save() :: saving category {}", category.getName());
        BookCategory model = convertToModel(category);
        BookCategory save = categoryRepository.save(model);
        return convertToDTO(save);
    }

    public Optional<BookCategory> findModelByName(String name) {
        if (!StringUtils.hasText(name)) {
            log.info("BookCategoryService.findModelByName() :: name is empty");
            return Optional.empty();
        }

        log.info("BookCategoryService.findModelByName() :: Finding by name {}", name);
        return categoryRepository.findByName(name);
    }

    public List<BookCategoryDTO> getAll() {
        log.info("BookCategoryService.save() :: getting all book category.");
        List<BookCategory> all = categoryRepository.findAll();
        return convertList(all);
    }

    public boolean deleteById(int id) {
        log.info("BookCategoryService.deleteById() :: deleting category {}", id);
        categoryRepository.deleteById(id);
        return true;
    }

    private List<BookCategoryDTO> convertList(List<BookCategory> categories) {
        List<BookCategoryDTO> dtos = new ArrayList<>();
        categories.forEach(role -> {
            Optional<BookCategoryDTO> dto = convertToDTO(role);
            dto.ifPresent(dtos::add);
        });

        return dtos;
    }

    private Optional<BookCategoryDTO> convertToDTO(BookCategory category) {
        return Optional.of(new BookCategoryDTO(category.getId(), category.getName(), category.getDescription()));
    }

    private BookCategory convertToModel(BookCategoryDTO dto) {
        return new BookCategory(0, dto.getName(), dto.getDescription(), Collections.emptySet());
    }
}
