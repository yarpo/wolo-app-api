package pl.pjwstk.woloappapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pjwstk.woloappapi.model.Shift;
import pl.pjwstk.woloappapi.repository.ShiftRepository;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class ShiftService {
    private final ShiftRepository shiftRepository;
    @Transactional
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

    public int getRegisteredUsersCountForShift(Long shiftId) {
        Shift shift = shiftRepository.findById(shiftId).orElse(null);

        if (shift != null) {
            return shift.getRegisteredUsersCount();
        } else {
            return 0; // lub można zwrócić odpowiedni kod błędu
        }
    }
}
