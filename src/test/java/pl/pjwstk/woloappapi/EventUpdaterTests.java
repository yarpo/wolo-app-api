package pl.pjwstk.woloappapi;

import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjwstk.woloappapi.model.EventEditRequestDto;
import pl.pjwstk.woloappapi.model.ShiftEditRequestDto;
import pl.pjwstk.woloappapi.model.entities.*;
import pl.pjwstk.woloappapi.repository.EventRepository;
import pl.pjwstk.woloappapi.service.DistrictService;
import pl.pjwstk.woloappapi.utils.EmailUtil;
import pl.pjwstk.woloappapi.utils.EventMapper;
import pl.pjwstk.woloappapi.utils.EventUpdater;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventUpdaterTests {
    @Mock
    private EventRepository eventRepository;

    @Mock
    private DistrictService districtService;

    @Mock
    private EmailUtil emailUtil;

    @Mock
    private EventMapper eventMapper;

    @InjectMocks
    private EventUpdater eventUpdater;

    private Event event;
    private EventEditRequestDto eventEditRequestDto;
    private City city;

    private Shift shift;

    @BeforeEach
    public void setUp() {
        city = new City();
        city.setId(1L);
        city.setName("Test City");

        var category = Category.builder().id(1L).build();
        var ctu = new CategoryToEvent();
        ctu.setCategory(category);
        var list = new ArrayList<CategoryToEvent>();
        list.add(ctu);
        var user = User.builder().email("email@gmail.com").build();
        var stu = new ShiftToUser();
        stu.setUser(user);
        var userList= new ArrayList<ShiftToUser>();
        userList.add(stu);
        var district= District.builder().id(1L).build();
        var address = Address.builder().district(district).build();
        shift = Shift.builder()
                .id(1L)
                .shiftToUsers(userList)
                .address(address)
                .build();
        var shiftList = new ArrayList<Shift>();
        shiftList.add(shift);
        event = Event.builder()
                .id(1L)
                .namePL("Test Event")
                .categories(list)
                .shifts(shiftList)
                .isPeselVerificationRequired(false)
                .build();

        eventEditRequestDto = new EventEditRequestDto();
        eventEditRequestDto.setNamePL("Updated Event PL");
        eventEditRequestDto.setNameEN("Updated Event EN");
        eventEditRequestDto.setNameUA("Updated Event UA");
        eventEditRequestDto.setNameRU("Updated Event RU");
        eventEditRequestDto.setDescriptionPL("Updated Description PL");
        eventEditRequestDto.setDescriptionEN("Updated Description EN");
        eventEditRequestDto.setDescriptionUA("Updated Description UA");
        eventEditRequestDto.setDescriptionRU("Updated Description RU");
        eventEditRequestDto.setOrganisationId(1L);
        eventEditRequestDto.setCategories(List.of(1L, 2L));
        eventEditRequestDto.setPeselVerificationRequired(false);
        eventEditRequestDto.setAgreementNeeded(false);
        eventEditRequestDto.setDate(LocalDate.now());
        eventEditRequestDto.setImageUrl("http://example.com/image.jpg");

        var newDistrict = District.builder().id(1L).build();
        newDistrict.setCity(city);
        when(districtService.getDistrictById(1L)).thenReturn(newDistrict);

        var shiftDto = new ShiftEditRequestDto();
        shiftDto.setId(1L);
        shiftDto.setDistrictId(1L);
        eventEditRequestDto.setShifts(List.of(shiftDto));


    }

    @Test
    public void testUpdateEvent() throws MessagingException {

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(eventMapper.toShift(any(ShiftEditRequestDto.class))).thenReturn(shift.builder());

        eventUpdater.update(eventEditRequestDto, 1L, true);

        verify(emailUtil, times(1)).sendEditEventMail(anyString(), anyLong());
        verify(eventRepository, times(1)).findById(1L);
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    public void testUpdateEventWithNonExistentId() throws MessagingException {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            eventUpdater.update(eventEditRequestDto, 1L, true);
        });

        verify(eventRepository, times(1)).findById(1L);
        verify(eventRepository, never()).save(any(Event.class));
        verify(emailUtil, never()).sendEditEventMail(anyString(), anyLong());
    }


    @Test
    public void testUpdateEventWithPeselVerificationRequired() throws MessagingException {
        eventEditRequestDto.setPeselVerificationRequired(true);
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(eventMapper.toShift(any(ShiftEditRequestDto.class)))
                .thenReturn(Shift.builder().event(event));
        eventUpdater.update(eventEditRequestDto, 1L, true);


        verify(eventRepository, times(1)).findById(1L);
        verify(eventRepository, times(1)).save(event);
        verify(emailUtil, times(1)).sendPeselVerificationEmail(anyString(), eq(event.getId()));
    }

    @Test
    public void testUpdateEventWithAgreementNeeded() throws MessagingException {
        eventEditRequestDto.setAgreementNeeded(true);
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(eventMapper.toShift(any())).thenReturn(shift.builder());
        eventUpdater.update(eventEditRequestDto, 1L, true);

        verify(eventRepository, times(1)).findById(1L);
        verify(eventRepository, times(1)).save(event);
        verify(emailUtil, times(1)).sendAgreementNeededEmail(anyString(), eq(event.getId()));
    }

    @Test
    public void testUpdateEventWithShiftUpdates() throws MessagingException {
        ShiftEditRequestDto shiftDto = new ShiftEditRequestDto();
        shiftDto.setDistrictId(1L);
        eventEditRequestDto.setShifts(List.of(shiftDto));

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(eventMapper.toShift(any(ShiftEditRequestDto.class))).thenReturn(Shift.builder());

        eventUpdater.update(eventEditRequestDto, 1L, true);

        verify(eventRepository, times(1)).findById(1L);
        verify(eventRepository, times(1)).save(event);
        verify(emailUtil, times(1)).sendEditEventMail(anyString(), eq(event.getId()));
    }
}
