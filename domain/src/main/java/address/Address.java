package address;

import base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import producer.Producer;
import warehouse.Warehouse;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter

@Entity
@Table(name = "addresses")
public class Address extends BaseEntity {

    private String addressLine;
    private String city;
    private String zipCode;

    @OneToOne(mappedBy = "hqAddress")
    private Producer producer;

    @OneToOne(mappedBy = "address")
    private Warehouse warehouse;

}
