package warehouse;

import address.Address;
import base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter

@Entity
@Table(name = "warehouses")
public class Warehouse extends BaseEntity {

    @OneToMany(mappedBy = "warehouse")
    private List<WarehouseCommodity> commodities;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id", unique = true)
    private Address address;

    @ManyToMany(mappedBy = "warehouses")
    private List<Shop> shops;

}
