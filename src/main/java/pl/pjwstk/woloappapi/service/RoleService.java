package pl.pjwstk.woloappapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pjwstk.woloappapi.model.entities.Role;
import pl.pjwstk.woloappapi.repository.RoleRepository;
import pl.pjwstk.woloappapi.utils.IllegalArgumentException;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(Long id) {
        return roleRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Role id not found!"));
    }
    public Role getRoleByName(String name){
        return roleRepository.findByName(name);
    }

    @Transactional
    public void createRole(Role role) {
        roleRepository.save(role);
    }

    @Transactional
    public void updateRole(Role role) {
        roleRepository
                .findById(role.getId())
                .orElseThrow(() -> new IllegalArgumentException("Role with ID " + role.getId() + " does not exist"));
        roleRepository.save(role);
    }

    @Transactional
    public void deleteRole(Long id) {
        roleRepository.findById(id).ifPresent(r -> {
            if (!"USER".equals(r.getName())) {
                roleRepository.deleteById(id);
            } else {
                throw new IllegalArgumentException("Can't delete role User");
            }
        });
    }
}
