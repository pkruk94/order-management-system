package warranty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import value_object.PercentageValue;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

@Entity
@Table(name = "warranties")
public class Warranty {

    private Integer durationYears;
    private PercentageValue fracPriceReturned;
    private Integer completionTimeInDays;
    private Set<WarrantyServices> servicesAvailable;

}
