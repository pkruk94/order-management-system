package dto.producer;

import address.Address;
import lombok.*;
import producer.Market;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateProducer {

    private String name;
    private Market market;
    private Address hqAddress;

}
