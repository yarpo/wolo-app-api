package pl.pjwstk.woloappapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pjwstk.woloappapi.model.AddressToEvent;
import pl.pjwstk.woloappapi.repository.AddressToEventRepository;

@Service
@AllArgsConstructor
public class AddressToEventSevice {
    private final AddressToEventRepository addressToEventRepository;

    @Transactional
    public void createAddressToEvent(AddressToEvent addressToEvent) {
        addressToEventRepository.save(addressToEvent);
    }

}
