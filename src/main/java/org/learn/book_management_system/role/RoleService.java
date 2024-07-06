package org.learn.book_management_system.role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository repository;

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
            log.info("RoleService.save() :: Saving role {}", role.getRole());
            Role save = repository.save(model);
            log.info("RoleService.save() :: User saved {}", role.getRole());

            return convertToDTO(save);
        } catch (Exception ex) {
            log.error("RoleService.save() :: Failed to save role. error: {}", ex.getMessage());
            return Optional.empty();
        }
    }

    public List<RoleDTO> getAll(){
        log.info("RoleService.getAll() :: getting all roles.");
        List<Role> all = repository.findAll();
        return convertList(all);
    }

    private List<RoleDTO> convertList(List<Role> roles){
        List<RoleDTO> dtos = new ArrayList<>();
        roles.forEach(role -> {
            Optional<RoleDTO> roleDTO = convertToDTO(role);
            roleDTO.ifPresent(dtos::add);
        });

        return dtos;
    }

    public boolean deleteById(int id){
        log.info("RoleService.deleteById() :: Deleting role by id {}",id);

        repository.deleteById(id);
        return true;
    }

    private Optional<RoleDTO> convertToDTO(Role role) {
        return Optional.of(new RoleDTO(role.getId(), role.getRole(),role.getDescription()));
    }

    private Role convertToModel(RoleDTO dto) {
        return new Role(0, dto.getRole(), dto.getDescription(), new HashSet<>());
    }
}
