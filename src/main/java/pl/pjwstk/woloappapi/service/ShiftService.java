package pl.pjwstk.woloappapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pjwstk.woloappapi.model.entities.Shift;
import pl.pjwstk.woloappapi.repository.ShiftRepository;
import pl.pjwstk.woloappapi.utils.NotFoundException;
import pl.pjwstk.woloappapi.utils.IllegalArgumentException;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class ShiftService {
    private final ShiftRepository shiftRepository;

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

        if (shiftToDelete.getDate().isAfter(LocalDate.now())) {
            shiftRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Can't delete shift: it has a past or today's date");
        }
    }

    @Transactional
    public void editShift(Shift shift) {
        shiftRepository.save(shift);
    }

}
