package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import product.ProductCategory;
import value_object.Money;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProduct {

    private Long productId;
    private String name;
    private Money price;
    private ProductCategory productCategory;
    private Long producerId;
    private Long warrantyPolicyId;

}
