package validation;

import dto.address.CreateAddress;
import validation.generic.AbstractValidator;

import java.util.Map;
import java.util.regex.Pattern;

public class AddressValidator extends AbstractValidator<CreateAddress> {

    private static final Pattern ADDRESS_LINE = Pattern.compile("[A-z0-9. ]+");
    private static final Pattern ZIP_CODE_FORMAT = Pattern.compile("\\d{2}-\\d{3}");
    private static final Pattern CITY = Pattern.compile("[A-z]+");


    @Override
    public Map<String, String> validate(CreateAddress item) {

        if (item == null) {
            errors.put("object", "dto provided ist null");
            return errors;
        }

        if (item.getAddressLine() == null) {
            errors.put("address line", "address line cannot be null");
        }

        if (!isAddressLineValid(item.getAddressLine())) {
            errors.put("address line", "address line format invalid");
        }

        if (item.getZipCode() == null) {
            errors.put("zip code", "zip code cannot be null");
        }

        if (!isZipCodeValid(item.getZipCode())) {
            errors.put("zip code", "zip code format invalid");
        }

        if (item.getCity() == null) {
            errors.put("city", "city cannot be null");
        }

        if (!isCityNameValid(item.getCity())) {
            errors.put("city", "city name invalid");
        }

        return errors;
    }

    private boolean isAddressLineValid(String name) {
        return ADDRESS_LINE.matcher(name).matches();
    }

    private boolean isZipCodeValid(String zipcode) {
        return ZIP_CODE_FORMAT.matcher(zipcode).matches();
    }

    private boolean isCityNameValid(String city) {
        return CITY.matcher(city).matches();
    }
}
