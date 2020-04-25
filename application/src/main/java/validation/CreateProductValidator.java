package validation;

import dto.product.CreateProduct;
import validation.generic.AbstractValidator;

import java.util.Map;
import java.util.regex.Pattern;

public class CreateProductValidator extends AbstractValidator<CreateProduct> {

    private static final Pattern PRODUCT_NAME_MATCHER = Pattern.compile("\"[A-z0-9]+\"");

    @Override
    public Map<String, String> validate(CreateProduct item) {

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

        if (item.getPrice() == null) {
            errors.put("price", "price cannot be null");
        }

        if (item.getProducerId() == null) {
            errors.put("producer id", "producer id cannot be null");
        }

        if (item.getProductCategory() == null) {
            errors.put("product category", "product category cannot be null");
        }

        if (item.getWarrantyPolicyId() == null) {
            errors.put("warranty policy", "warranty policy id cannot be null");
        }

        return errors;
    }

    private boolean isNameValid(String name) {
        return PRODUCT_NAME_MATCHER.matcher(name).matches();
    }
}
