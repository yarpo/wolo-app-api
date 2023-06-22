package pl.pjwstk.woloappapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pjwstk.woloappapi.model.Role;
import pl.pjwstk.woloappapi.repository.RoleRepository;
import pl.pjwstk.woloappapi.utils.EventNotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("role id not found!"));
    }

    public void createRole(Role role) {
        roleRepository.save(role);
    }

    public Role updateRole(Role role) {
        if (!roleRepository.existsById(role.getId())) {
            throw new IllegalArgumentException("role with ID " + role.getId() + " does not exist");
        }
        return roleRepository.save(role);
    }

    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new IllegalArgumentException("role with ID " + id + " does not exist");
        }
        roleRepository.deleteById(id);
    }


}