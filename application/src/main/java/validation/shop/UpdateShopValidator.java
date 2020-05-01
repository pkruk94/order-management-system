package validation.shop;

import dto.address.AddressData;
import dto.address.UpdateAddress;
import dto.shop.CreateShop;
import dto.shop.UpdateShop;
import validation.address.AddressValidator;
import validation.generic.AbstractValidator;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UpdateShopValidator extends AbstractValidator<UpdateShop> {

    private static final Pattern SHOP_NAME_MATCHER = Pattern.compile("\"[A-z0-9]+\"");
    private static final AddressValidator addressValidator = new AddressValidator();

    @Override
    public Map<String, String> validate(UpdateShop item) {

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

        if (item.getAddress() == null) {
            errors.put("address", "address cannot be null");
        }

        Map<String, String> validateAddress = validateAddress(item.getAddress().getAddressData());

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

        if (!isBudgetValid(item.getBudget())) {
            errors.put("budget", "budget cannot be negative value");
        }

        if (!isMaxSinPurBudgetFractionValid(item.getMaxSinglePurchaseBudgetFraction())) {
            errors.put("fraction", "illegal percentage value");
        }

        return errors;
    }

    private boolean isNameValid(String name) {
        return SHOP_NAME_MATCHER.matcher(name).matches();
    }

    private boolean isBudgetValid(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) >= 0;
    }

    private boolean isMaxSinPurBudgetFractionValid(BigDecimal fraction) {
        return fraction.compareTo(BigDecimal.ZERO) > 0
                && fraction.compareTo(BigDecimal.ONE) < 1;
    }

    private Map<String, String> validateAddress(AddressData addressData) {
        Map<String, String> addressErrors = addressValidator.validate(addressData);
        if (addressValidator.hasErrors()) {
            return addressErrors;
        }
        return null;
    }
}
