package pl.pjwstk.woloappapi.service;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import pl.pjwstk.woloappapi.model.Role;
import pl.pjwstk.woloappapi.repository.RoleRepository;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
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

    @Transactional
    public void createRole(Role role) {
        roleRepository.save(role);
    }

    @Transactional
    public void updateRole(Role role, Long id) {
        roleRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role with ID " + id + " does not exist"));
        role.setId(id);
        roleRepository.save(role);
    }

    @Transactional
    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new IllegalArgumentException("Role with ID " + id + " does not exist");
        }
        roleRepository.deleteById(id);
    }
}
