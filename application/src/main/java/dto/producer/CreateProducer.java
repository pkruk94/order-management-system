package dto.producer;

import dto.address.CreateAddress;
import lombok.*;
import producer.Market;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateProducer {

    private String name;
    private Market market;
    private CreateAddress hqAddress;

}
