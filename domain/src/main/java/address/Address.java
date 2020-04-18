package address;

import base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import producer.Producer;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

@Entity
@Table(name = "addresses")
public class Address extends BaseEntity {

    private String addressLine;
    private String city;
    private String zipCode;

    @OneToOne(mappedBy = "hqAddress")
    private Producer producer;

}
