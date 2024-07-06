package org.learn.book_management_system.author;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
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
            log.info("AuthorService.save() :: Saving author {}", author.getFullName());
            Author save = authorRepository.save(model);
            log.info("AuthorService.save() :: Done Saving author {}", author.getFullName());
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

    public Optional<Author> findModelByFullName(String name) {
        if (!StringUtils.hasText(name)) {
            log.warn("AuthorService::findModelById(} :: id is 0");
            return Optional.empty();
        }

        return authorRepository.findByFullName(name);
    }

    public boolean deleteById(int id){
        authorRepository.deleteById(id);
        return true;
    }

    public List<AuthorDTO> getAll(){
        log.info("AuthorService.getAll() :: getting all authors.");
        List<Author> all = authorRepository.findAll();
        return convertList(all);
    }

    private List<AuthorDTO> convertList(List<Author> authors) {
        List<AuthorDTO> dtos = new ArrayList<>();
        authors.forEach(author -> {
            Optional<AuthorDTO> dto = convertToDTO(author);
            dto.ifPresent(dtos::add);
        });

        return dtos;
    }

    private Author convertToModel(AuthorDTO dto) {
        return new Author(0, dto.getFullName(), dto.getAbout());
    }

    private Optional<AuthorDTO> convertToDTO(Author author) {
        return Optional.of(new AuthorDTO(author.getId(), author.getFullName(), author.getAbout()));
    }
}
