package dto.warehouse;

import dto.address.UpdateAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateWarehouse {

    private Long id;
    private UpdateAddress updateAddress;

}
