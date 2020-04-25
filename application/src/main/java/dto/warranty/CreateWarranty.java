package dto.warranty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import value_object.PercentageValue;
import warranty.WarrantyServices;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateWarranty {

    private Integer durationYears;
    private PercentageValue fracPriceReturned;
    private Integer completionTimeInDays;
    private Set<WarrantyServices> servicesAvailable;
    private Long producerId;

}
