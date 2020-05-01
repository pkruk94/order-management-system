package validation.producer;

import dto.address.AddressData;
import dto.producer.CreateProducer;
import validation.address.AddressValidator;
import validation.generic.AbstractValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// TODO validate address?
public class CreateProducerValidator extends AbstractValidator<CreateProducer> {

    private static final Pattern PRODUCER_NAME_MATCHER = Pattern.compile("\"[A-z0-9]+\"");
    private static final AddressValidator addressValidator = new AddressValidator();

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

        Map<String, String> validateAddress = validateAddress(item.getHqAddress().getAddressData());

        if (validateAddress != null) {
            errors = Stream.concat(
                    errors.entrySet().stream(),
                    validateAddress.entrySet().stream()
            ).collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (v1, v2) -> v1 + " ; " + v2,
                    HashMap::new
            ));
        }

        return errors;
    }

    private boolean isNameValid(String name) {
        return PRODUCER_NAME_MATCHER.matcher(name).matches();
    }

    private Map<String, String> validateAddress(AddressData addressData) {
        Map<String, String> addressErrors = addressValidator.validate(addressData);
        if (addressValidator.hasErrors()) {
            return addressErrors;
        }
        return null;
    }
}
