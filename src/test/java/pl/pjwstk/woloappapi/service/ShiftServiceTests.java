package pl.pjwstk.woloappapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjwstk.woloappapi.model.entities.Address;
import pl.pjwstk.woloappapi.model.entities.Shift;
import pl.pjwstk.woloappapi.model.entities.ShiftToUser;
import pl.pjwstk.woloappapi.repository.ShiftRepository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShiftServiceTests {

    @Mock
    private ShiftRepository shiftRepository;

    @InjectMocks
    private ShiftService shiftService;

    @Test
    public void testGetShiftById(){
        Shift shift = new Shift();
        shift.setId(1L);
        when(shiftRepository.findById(1L)).thenReturn(Optional.of(shift));

        Shift retrievedShift = shiftService.getShiftById(1L);

        assertEquals(shift.getId(), retrievedShift.getId());
    }

    @Test
    public void testCreateShift(){
        Shift shift = new Shift();
        shift.setId(1L);
        shift.setStartTime(LocalTime.of(9, 0));
        shift.setEndTime(LocalTime.of(17, 0));
        shift.setRegisteredUsers(1);
        shift.setCapacity(10);
        shift.setLeaderRequired(true);
        shift.setRequiredMinAge(18);
        shift.setShiftDirectionsEN("Test Shift Directions");

        Address address = new Address();
        address.setId(1L);

        List<ShiftToUser> shiftToUserList = new ArrayList<>();
        ShiftToUser shiftToUser = new ShiftToUser();
        shiftToUser.setId(1L);
        shiftToUserList.add(shiftToUser);
        shift.setShiftToUsers(shiftToUserList);

        shiftService.createShift(shift);

        ArgumentCaptor<Shift> shiftCaptor = ArgumentCaptor.forClass(Shift.class);
        verify(shiftRepository).save(shiftCaptor.capture());
        Shift capturedShift = shiftCaptor.getValue();
        assertEquals(1L, capturedShift.getId());
        assertEquals(LocalTime.of(9, 0), capturedShift.getStartTime());
        assertEquals(LocalTime.of(17, 0), capturedShift.getEndTime());
        assertEquals(1, capturedShift.getRegisteredUsers());
        assertEquals(10, capturedShift.getCapacity());
        assertTrue(capturedShift.isLeaderRequired());
        assertEquals(18, capturedShift.getRequiredMinAge());
        assertEquals("Test Shift Directions", capturedShift.getShiftDirectionsEN());
        assertEquals(1L, capturedShift.getShiftToUsers().get(0).getId());
    }

    @Test
    public void testDelete(){
        Shift shift = new Shift();

        when(shiftRepository.findById(1L)).thenReturn(Optional.of(shift));

        shiftService.delete(1L);

        verify(shiftRepository, times(1)).findById(1L);
        verify(shiftRepository, times(1)).deleteById(1L);
    }
}
