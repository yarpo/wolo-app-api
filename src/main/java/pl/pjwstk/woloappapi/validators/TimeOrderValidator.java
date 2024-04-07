package pl.pjwstk.woloappapi.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pl.pjwstk.woloappapi.annotations.TimeOrder;
import pl.pjwstk.woloappapi.model.ShiftRequestDto;


public class TimeOrderValidator implements ConstraintValidator<TimeOrder, ShiftRequestDto> {

    @Override
    public void initialize(TimeOrder timeOrder) {}

    @Override
    public boolean isValid(ShiftRequestDto shift, ConstraintValidatorContext context) {
        return shift.getStartTime().isBefore(shift.getEndTime());
    }
}
