package dto.shop;

import dto.address.UpdateAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UpdateShop {

    private Long id;
    private String name;
    private BigDecimal maxSinglePurchaseBudgetFraction;
    private BigDecimal budget;
    private UpdateAddress address;

}
