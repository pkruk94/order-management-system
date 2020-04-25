package dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import product.ProductCategory;
import value_object.Money;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GetProduct {

    private Long id;
    private String name;
    private Money price;
    private ProductCategory productCategory;
    private Long producerId;
    private Long warrantyPolicyId;

}
