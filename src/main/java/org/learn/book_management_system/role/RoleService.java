package org.learn.book_management_system.role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository repository;

    public Optional<Role> getModelById(int id) {
        return repository.findById(id);
    }

    public Optional<Role> getModelByRole(String role) {
        return repository.findByRole(role);
    }

    public Optional<RoleDTO> save(RoleDTO role) {
        try {
            if (role == null) {
                log.info("RoleService.save() :: Can not save null role");
                return Optional.empty();
            }
            Role model = convertToModel(role);
            log.info("RoleService.save() :: Saving role {}", role.role());
            Role save = repository.save(model);
            log.info("RoleService.save() :: User saved {}", role.role());

            return convertToDTO(save);
        } catch (Exception ex) {
            log.error("RoleService.save() :: Failed to save role. error: {}", ex.getMessage());
            return Optional.empty();
        }
    }

    private Optional<RoleDTO> convertToDTO(Role role) {
        return Optional.of(new RoleDTO(role.getId(), role.getRole()));
    }

    private Role convertToModel(RoleDTO dto) {
        return new Role(0, dto.role(), new HashSet<>());
    }
}
