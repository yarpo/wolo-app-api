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

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTests {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @Test
    public void testGetAllRoles(){
        Role role1 = new Role();
        Role role2 = new Role();

        when(roleRepository.findAll()).thenReturn(Arrays.asList(role1, role2));

        List<Role> actualRoles = roleService.getAllRoles();

        assertEquals(2, actualRoles.size());
    }

    @Test
    public void testGetByRoleId(){
        Role role = new Role();
        role.setId(1L);
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));

        Role retrievedRole = roleService.getRoleById(1L);

        assertEquals(role.getId(), retrievedRole.getId());
    }

    @Test
    public void testGetByRoleName(){
        Role role = new Role();
        role.setName("Test Role");
        when(roleRepository.findByName("Test Role")).thenReturn(role);

        Role retrievedRole = roleService.getRoleByName("Test Role");
        assertEquals(role.getName(), retrievedRole.getName());
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
    public void testUpdateCategory() {
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
}
