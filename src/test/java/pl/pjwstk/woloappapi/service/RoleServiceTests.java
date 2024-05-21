package pl.pjwstk.woloappapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import pl.pjwstk.woloappapi.model.entities.Privilege;
import pl.pjwstk.woloappapi.model.entities.Role;
import pl.pjwstk.woloappapi.model.entities.User;
import pl.pjwstk.woloappapi.repository.RoleRepository;
import pl.pjwstk.woloappapi.utils.IllegalArgumentException;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTests {

    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private RoleService roleService;
    @Test
    public void testGetAllRoles(){
        List<Role> roles = List.of(new Role(), new Role());
        when(roleRepository.findAll()).thenReturn(roles);

        List<Role> result = roleService.getAllRoles();

        assertEquals(roles.size(), result.size());
        assertEquals(roles, result);
        verify(roleRepository, times(1)).findAll();
    }

    @Test
    public void testGetByRoleId(){
        Role role = new Role();
        role.setId(1L);
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        Role retrievedRole = roleService.getRoleById(1L);

        assertEquals(role.getId(), retrievedRole.getId());
        verify(roleRepository, times(1)).findById(role.getId());
    }

    @Test
    void testGetRoleById_NotFound() {
        Long roleId = 1L;
        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class, () -> {
            roleService.getRoleById(roleId);
        });

        assertEquals("Role id not found!", exception.getMessage());
        verify(roleRepository, times(1)).findById(roleId);
    }

    @Test
    public void testGetByRoleName(){
        Role role = new Role();
        role.setName("Test Role");
        when(roleRepository.findByName("Test Role")).thenReturn(role);

        Role retrievedRole = roleService.getRoleByName("Test Role");
        assertEquals(role.getName(), retrievedRole.getName());
        verify(roleRepository, times(1)).findByName(role.getName());
    }

    @Test
    public void testCreateRole(){
        Role role = new Role();
        role.setId(1L);
        role.setName("Test Role");

        List<Privilege> privileges = new ArrayList<>();
        Privilege privilege = new Privilege();
        privilege.setId(1L);
        privilege.setName("TEST_PRIVILEGE");
        privileges.add(privilege);
        role.setPrivileges(privileges);

        List<User> usersList = new ArrayList<>();
        User user = new User();
        user.setId(1L);
        usersList.add(user);
        role.setUserEntities(usersList);

        List<GrantedAuthority> expectedAuthorities = new ArrayList<>();
        expectedAuthorities.add(new SimpleGrantedAuthority("TEST_PRIVILEGE"));
        expectedAuthorities.add(new SimpleGrantedAuthority("ROLE_Test Role"));

        roleService.createRole(role);

        ArgumentCaptor<Role> roleCaptor = ArgumentCaptor.forClass(Role.class);
        verify(roleRepository).save(roleCaptor.capture());
        Role capturedRole = roleCaptor.getValue();
        assertEquals(1L, capturedRole.getId());
        assertEquals("Test Role", capturedRole.getName());
        assertEquals(1L, capturedRole.getPrivileges().get(0).getId());
        assertEquals(1L, capturedRole.getUserEntities().get(0).getId());
        assertEquals(expectedAuthorities, capturedRole.getAuthorities());
    }

    @Test
    public void testUpdateRole() {
        Role oldRole = new Role();
        oldRole.setId(1L);
        oldRole.setName("Old Role Name");

        Role newRole = new Role();
        newRole.setId(oldRole.getId());
        newRole.setName("New Role Name");

        when(roleRepository.findById(1L)).thenReturn(Optional.of(oldRole));

        roleService.updateRole(newRole);

        ArgumentCaptor<Role> roleCaptor = ArgumentCaptor.forClass(Role.class);
        verify(roleRepository).save(roleCaptor.capture());
        Role capturedRole = roleCaptor.getValue();
        assertEquals(1L, capturedRole.getId());
        assertEquals("New Role Name", capturedRole.getName());
        verify(roleRepository, times(1)).findById(oldRole.getId());
    }

    @Test
    public void testDeleteRole() {
        Role role = new Role();
        role.setId(1L);
        role.setName("Test Role");

        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        roleService.deleteRole(1L);

        verify(roleRepository, times(1)).findById(1L);
        verify(roleRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteRole_UserRole() {
        var roleId = 1L;
        var role = Role.builder()
                .id(roleId)
                .name("USER")
                .build();

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));

        var exception = assertThrows(IllegalArgumentException.class, () -> {
            roleService.deleteRole(roleId);
        });

        assertEquals("Can't delete role User", exception.getMessage());
        verify(roleRepository, times(1)).findById(roleId);
        verify(roleRepository, times(0)).deleteById(roleId);
    }

    @Test
    void testDeleteRole_NotFound() {
        Long roleId = 1L;
        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        roleService.deleteRole(roleId);

        verify(roleRepository, times(1)).findById(roleId);
        verify(roleRepository, times(0)).deleteById(roleId);
    }
}
