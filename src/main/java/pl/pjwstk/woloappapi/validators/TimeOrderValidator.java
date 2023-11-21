package pl.pjwstk.woloappapi.validators;

import pl.pjwstk.woloappapi.annotations.TimeOrder;
import pl.pjwstk.woloappapi.model.Shift;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TimeOrderValidator implements ConstraintValidator<TimeOrder, Shift> {

    @Override
    public void initialize(TimeOrder timeOrder){
    }

    @Override
    public boolean isValid(Shift shift, ConstraintValidatorContext context) {
        return shift.getStartTime().isBefore(shift.getEndTime());
    }

}
