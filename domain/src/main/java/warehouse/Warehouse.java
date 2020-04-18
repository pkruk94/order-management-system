package warehouse;

import address.Address;
import base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
@Table(name = "warehouses")
public class Warehouse extends BaseEntity {



    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id", unique = true)
    private Address address;
}
