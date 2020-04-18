package warehouse;

import address.Address;
import base.BaseEntity;
import commodity.Commodity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
@Table(name = "warehouses")
public class Warehouse extends BaseEntity {

    @OneToMany(mappedBy = "warehouses")
    private List<Commodity> commodities;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id", unique = true)
    private Address address;
}
