package dto.order;

import dto.address.AddressData;
import dto.order_position.CreateOrderPosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import order.PaymentMethod;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateOrder {

    private Long userID;
    private List<CreateOrderPosition> orderPositions;
    private Long discountPolicyID;
    private AddressData addressData;
    private LocalDateTime orderTime;
    private PaymentMethod paymentMethod;

}
