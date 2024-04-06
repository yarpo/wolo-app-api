package pl.pjwstk.woloappapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pjwstk.woloappapi.model.entities.Address;
import pl.pjwstk.woloappapi.model.entities.AddressToEvent;
import pl.pjwstk.woloappapi.model.entities.District;
import pl.pjwstk.woloappapi.model.entities.Organisation;
import pl.pjwstk.woloappapi.repository.AddressRepository;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTests {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService;

    @Test
    public void testCreateAddress(){
        Address address = new Address();
        address.setId(1L);
        address.setStreet("Sample Street");
        address.setHomeNum("123");

        District district = new District();
        district.setId(1L);
        address.setDistrict(district);

        List<Organisation> organisationList = new ArrayList<>();
        Organisation organisation1 = new Organisation();
        Organisation organisation2 = new Organisation();
        organisation1.setId(1L);
        organisation2.setId(2L);
        organisationList.add(organisation1);
        organisationList.add(organisation2);
        address.setOrganisations(organisationList);

        List<AddressToEvent> addressToEventList = new ArrayList<>();
        AddressToEvent addressToEvent1 = new AddressToEvent();
        AddressToEvent addressToEvent2 = new AddressToEvent();
        addressToEvent1.setId(1L);
        addressToEvent2.setId(2L);
        addressToEventList.add(addressToEvent1);
        addressToEventList.add(addressToEvent2);
        address.setAddressToEvents(addressToEventList);

        addressService.createAddress(address);

        ArgumentCaptor<Address> addressCaptor = ArgumentCaptor.forClass(Address.class);
        verify(addressRepository).save(addressCaptor.capture());
        Address capturedAddress = addressCaptor.getValue();
        assertEquals(1L, capturedAddress.getId());
        assertEquals("Sample Street", capturedAddress.getStreet());
        assertEquals("123", capturedAddress.getHomeNum());
        assertEquals(1L, capturedAddress.getDistrict().getId());
        assertEquals(2, capturedAddress.getOrganisations().size());
        assertEquals(2, capturedAddress.getAddressToEvents().size());
    }

}
