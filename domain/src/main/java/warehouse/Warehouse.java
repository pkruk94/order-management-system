package warehouse;

import address.Address;
import base.BaseEntity;
import warehouse_commodity.WarehouseCommodity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import shop.Shop;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
@Table(name = "warehouses")
public class Warehouse extends BaseEntity {

    @OneToMany(mappedBy = "warehouse")
    private List<WarehouseCommodity> commodities;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id", unique = true)
    private Address address;

    @ManyToMany(mappedBy = "warehouse")
    private List<Shop> shops;

}
