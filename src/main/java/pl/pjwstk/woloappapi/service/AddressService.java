package pl.pjwstk.woloappapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pjwstk.woloappapi.model.Address;
import pl.pjwstk.woloappapi.repository.AddressRepository;

@Service
@AllArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    @Transactional
    public void createAddress(Address address) {
        addressRepository.save(address);
    }
}
