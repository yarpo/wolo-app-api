package pl.pjwstk.woloappapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pjwstk.woloappapi.model.Shift;
import pl.pjwstk.woloappapi.repository.ShiftRepository;
import pl.pjwstk.woloappapi.utils.NotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class ShiftService {
    private final ShiftRepository shiftRepository;
    public List<Shift> getAllShifts() {
        return shiftRepository.findAll();
    }

    public Shift getShiftById(Long id) {
        return shiftRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Shift id not found!"));
    }

    public void createShift(Shift Shift) {
        shiftRepository.save(Shift);
    }

    public Shift updateShift(Shift Shift) {
        if (!shiftRepository.existsById(Shift.getId())) {
            throw new IllegalArgumentException("Shift with ID " + Shift.getId() + " does not exist");
        }
        return shiftRepository.save(Shift);
    }

    public void deleteShift(Long id) {
        if (!shiftRepository.existsById(id)) {
            throw new IllegalArgumentException("Shift with ID " + id + " does not exist");
        }
        shiftRepository.deleteById(id);
    }


}