package shop;

import address.Address;
import base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import user.User;
import warehouse.Warehouse;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
@Table
public class Shop extends BaseEntity {

    private String name;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "shops_warehouses",
            joinColumns = @JoinColumn(name = "shop_id"),
            inverseJoinColumns = @JoinColumn(name = "warehouse_id")
    )
    private List<Warehouse> warehouses;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "admin_shop_id")
    private User adminShop;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_manager")
    private User userManager;

}
