package dto.producer;

import address.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import producer.Market;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GetProducer {

    private Long id;
    private String name;
    private Market market;
    private Address hqAddress;

}
