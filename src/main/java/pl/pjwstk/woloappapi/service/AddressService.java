package pl.pjwstk.woloappapi.service;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import pl.pjwstk.woloappapi.model.Address;
import pl.pjwstk.woloappapi.repository.AddressRepository;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public Address getAddressById(Long id) {
        return addressRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Address id not found!"));
    }

    public void createAddress(Address address) {
        addressRepository.save(address);
    }
}
