package org.springframework.samples.petclinic.feeding;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

import org.springframework.util.StringUtils;

public class FeedingValidator implements Validator {

    private static final String REQUIRED = "required";

    @Override
    public boolean supports(Class<?> clazz) {
        return Feeding.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Feeding feeding = (Feeding) target;
        LocalDate startDate = feeding.getStartDate();
        double weeksDuration = feeding.getWeeksDuration();

        if (startDate == null) {
            errors.rejectValue("startDate", REQUIRED, REQUIRED);
        }

        if (weeksDuration < 1.0) {
            errors.rejectValue("weeksDuration", "weeksDuration must be greater than one");
        }

        if (feeding.isNew() && feeding.getFeedingType() == null) {
            errors.rejectValue("feeding_type", REQUIRED, REQUIRED);
        }

    }

}
