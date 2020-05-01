package dto.warehouse;

import dto.address.CreateAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class CreateWarehouse {

    private CreateAddress warehouseAddress;

}
