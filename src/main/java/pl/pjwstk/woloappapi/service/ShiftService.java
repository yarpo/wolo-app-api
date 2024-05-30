package pl.pjwstk.woloappapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pjwstk.woloappapi.model.entities.Shift;
import pl.pjwstk.woloappapi.model.entities.ShiftToUser;
import pl.pjwstk.woloappapi.repository.ShiftRepository;
import pl.pjwstk.woloappapi.repository.ShiftToUserRepository;
import pl.pjwstk.woloappapi.utils.IllegalArgumentException;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ShiftService {
    private final ShiftRepository shiftRepository;
    private final ShiftToUserRepository shiftToUserRepository;

    public Shift getShiftById(long id){
        return shiftRepository.findById(id).orElseThrow(() -> new NotFoundException("Event id not found!"));
    }

    public void createShift(Shift shift) {
        shiftRepository.save(shift);
    }

    @Transactional
    public void delete(Long id) {
        Shift shiftToDelete = shiftRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Shift with ID " + id + " does not exist"));

        if (shiftToDelete.getEvent().getDate().isAfter(LocalDate.now())) {
            shiftRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Can't delete shift: it has a past or today's date");
        }
    }

    public List<ShiftToUser> getPastEventsByUser(Long id) {
        return shiftToUserRepository.findShiftToUsersByUserId(id)
                .stream()
                .filter(shiftToUser -> shiftToUser.getShift().getEvent().getDate().isBefore(LocalDate.now()))
                .toList();
    }

    public List<ShiftToUser> getCurrentEventsByUser(Long id) {
        return shiftToUserRepository.findShiftToUsersByUserId(id)
                .stream()
                .filter(shiftToUser -> !shiftToUser.getShift().getEvent().getDate().isBefore(LocalDate.now()))
                .toList();
    }

    @Transactional
    public void editShift(Shift shift) {
        shiftRepository.save(shift);
    }

    public List<ShiftToUser> getReserveEventsByUser(Long id) {
        return shiftToUserRepository.findShiftToUsersByUserId(id)
                .stream()
                .filter(ShiftToUser::isOnReserveList)
                .toList();
    }
}
