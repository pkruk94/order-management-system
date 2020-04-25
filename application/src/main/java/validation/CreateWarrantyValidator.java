package validation;

import dto.warranty.CreateWarranty;
import validation.generic.AbstractValidator;

import java.util.Map;

public class CreateWarrantyValidator extends AbstractValidator<CreateWarranty> {

    private static final Integer MINIMAL_COMPLETION_TIME_IN_DAYS = 1;
    private static final Integer MINIMAL_DURATION_IN_YEARS = 1;

    @Override
    public Map<String, String> validate(CreateWarranty item) {
        if (item == null) {
            errors.put("object", "dto provided ist null");
            return errors;
        }

        if (item.getCompletionTimeInDays() == null) {
            errors.put("Completion Time in Days", "This value cannot be null");
        }

        if (item.getCompletionTimeInDays() < MINIMAL_COMPLETION_TIME_IN_DAYS) {
            errors.put("Completion Time in Days", "This value is invalid");
        }

        if (item.getDurationYears() == null) {
            errors.put("Duration in Years", "This value cannot be null");
        }

        if (item.getDurationYears() < MINIMAL_DURATION_IN_YEARS) {
            errors.put("Duration in Years", "This value is invalid");
        }

        if (item.getFracPriceReturned() == null) {
            errors.put("Fraction of price returned", "This value cannot be null");
        }

        if (item.getProducerId() == null) {
            errors.put("PProducer ID", "Warranty must have producer assigned");
        }

        if (item.getServicesAvailable() == null || item.getServicesAvailable().isEmpty()) {
            errors.put("Services available", "Warranty must have services available");
        }

        return errors;
    }

}
