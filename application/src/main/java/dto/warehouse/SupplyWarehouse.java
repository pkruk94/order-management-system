package dto.warehouse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupplyWarehouse {
    Long productId;
    Long warehouseId;
    Long shopId;
    int quantity;
}
