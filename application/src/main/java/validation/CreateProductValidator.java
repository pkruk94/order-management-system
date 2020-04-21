package validation;

import dto.CreateProduct;
import validation.generic.AbstractValidator;

import java.util.Map;

public class CreateProductValidator extends AbstractValidator<CreateProduct> {
    @Override
    public Map<String, String> validate(CreateProduct item) {

        if (item == null) {

            return errors;
        }

        if (item)
    }
}
