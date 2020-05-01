package dto.order_position;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateOrderPosition {

    private Long productID;
    private Integer quantity;
    private Long shopID;

}
