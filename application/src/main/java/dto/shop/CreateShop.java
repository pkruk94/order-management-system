package dto.shop;

import dto.address.CreateAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreateShop {

    private String name;
    private BigDecimal maxSinglePurchaseBudgetFraction;
    private BigDecimal budget;
    private CreateAddress address;

}
