package pl.pjwstk.woloappapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pjwstk.woloappapi.model.Address;
import pl.pjwstk.woloappapi.repository.AdressRepository;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class AddressService {
    private final AdressRepository adressRepository;

    public List<Address> getAllAddresses() {
        return adressRepository.findAll();
    }

    public Address getAddressById(Long id) {
        return adressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Address id not found!"));
    }

    public void createAddress(Address address) {
        adressRepository.save(address);
    }
}
