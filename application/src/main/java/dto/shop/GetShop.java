package dto.shop;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetShop {

    private String name;
    private BigDecimal percentageValue;
    private BigDecimal money;
    private Long addressID;
    private Long userManagerID;

}



