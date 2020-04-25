package validation;

import dto.producer.CreateProducer;
import validation.generic.AbstractValidator;

import java.util.Map;
import java.util.regex.Pattern;

// TODO validate address?
public class CreateProducerValidator extends AbstractValidator<CreateProducer> {

    private static final Pattern PRODUCER_NAME_MATCHER = Pattern.compile("\"[A-z0-9]+\"");

    @Override
    public Map<String, String> validate(CreateProducer item) {

        if (item == null) {
            errors.put("object", "dto provided ist null");
            return errors;
        }

        if (item.getName() == null) {
            errors.put("name", "name cannot be null");
        }

        if (!isNameValid(item.getName())) {
            errors.put("name", "name format invalid");
        }

        if (item.getMarket() == null) {
            errors.put("market", "market cannot be null");
        }

        return errors;
    }

    private boolean isNameValid(String name) {
        return PRODUCER_NAME_MATCHER.matcher(name).matches();
    }
}
