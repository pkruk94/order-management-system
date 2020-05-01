package dto.warehouse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferProductsFromTo {

    private Long productID;
    private Long sourceWarehouseID;
    private Long targetWarehouseID;
    private int quantity;

}
