package org.learn.book_management_system.author;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public Optional<AuthorDTO> save(AuthorDTO author) {
        try {
            if (author == null) {
                log.warn("AuthorService:save() :: Author is null");
                return Optional.empty();
            }

            Author model = convertToModel(author);
            log.info("AuthorService.save() :: Saving author {}", author.fullName());
            Author save = authorRepository.save(model);
            log.info("AuthorService.save() :: Done Saving author {}", author.fullName());
            return convertToDTO(save);
        } catch (Exception ex) {
            log.info("AuthorService.save() :: Failed to Save author.");
            return Optional.empty();

        }
    }

    public Optional<AuthorDTO> findById(int id) {
        if (id == 0) {
            log.warn("AuthorService::findById(} :: id is 0");
            return Optional.empty();
        }

        Optional<Author> model = authorRepository.findById(id);
        return model.flatMap(this::convertToDTO);
    }

    public Optional<Author> findModelById(int id) {
        if (id == 0) {
            log.warn("AuthorService::findModelById(} :: id is 0");
            return Optional.empty();
        }

        return authorRepository.findById(id);
    }

    private Author convertToModel(AuthorDTO dto) {
        return new Author(0, dto.fullName(), dto.about(), new HashSet<>());
    }

    private Optional<AuthorDTO> convertToDTO(Author author) {
        return Optional.of(new AuthorDTO(author.getId(), author.getFullName(), author.getAbout()));
    }
}
