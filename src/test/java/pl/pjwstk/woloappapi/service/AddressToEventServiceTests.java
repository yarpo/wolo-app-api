package pl.pjwstk.woloappapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjwstk.woloappapi.model.entities.Address;
import pl.pjwstk.woloappapi.model.entities.AddressToEvent;
import pl.pjwstk.woloappapi.model.entities.Event;
import pl.pjwstk.woloappapi.model.entities.Shift;
import pl.pjwstk.woloappapi.repository.AddressToEventRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AddressToEventServiceTests {

    @Mock
    private AddressToEventRepository addressToEventRepository;

    @InjectMocks
    private AddressToEventSevice addressToEventSevice;

    @Test
    public void testCreateAddressToEvent(){
        AddressToEvent addressToEvent = new AddressToEvent();
        addressToEvent.setId(1L);

        Address address = new Address();
        address.setId(1L);
        addressToEvent.setAddress(address);

        Event event = new Event();
        event.setId(1L);
        addressToEvent.setEvent(event);

        List<Shift> shiftList = new ArrayList<>();
        Shift shift1 = new Shift();
        shift1.setId(1L);
        Shift shift2 = new Shift();
        shift2.setId(2L);
        shiftList.add(shift1);
        shiftList.add(shift2);
        addressToEvent.setShifts(shiftList);

        addressToEventSevice.createAddressToEvent(addressToEvent);

        ArgumentCaptor<AddressToEvent> addressToEventCaptor = ArgumentCaptor.forClass(AddressToEvent.class);
        verify(addressToEventRepository).save(addressToEventCaptor.capture());
        AddressToEvent capturedAddress = addressToEventCaptor.getValue();
        assertEquals(1L, capturedAddress.getId());
        assertEquals(1L, capturedAddress.getAddress().getId());
        assertEquals(1L, capturedAddress.getEvent().getId());
        assertEquals(2, capturedAddress.getShifts().size());
    }
}
