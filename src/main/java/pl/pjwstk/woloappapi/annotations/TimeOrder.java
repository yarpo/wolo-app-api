package pl.pjwstk.woloappapi.annotations;

import pl.pjwstk.woloappapi.validators.TimeOrderValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TimeOrderValidator.class)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeOrder {
    String message() default "endTime must be after startTime";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
