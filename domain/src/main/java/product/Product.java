package product;

import base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import offered_commodity.OfferedCommodity;
import order.Order;
import order_position.OrderPosition;
import producer.Producer;
import value_object.Money;
import value_object.PercentageValue;
import warehouse_commodity.WarehouseCommodity;
import warranty.Warranty;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter

@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    private String name;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "price"))
    private Money price;

    @Enumerated(EnumType.STRING)
    private ProductCategory productCategory;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "producer_id")
    private Producer producer;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "warranty_id")
    private Warranty warrantyPolicy;

    @OneToMany(mappedBy = "product")
    private List<OrderPosition> orderPosition;

    @OneToMany(mappedBy = "product")
    private List<WarehouseCommodity> warehouseCommodities;

    @OneToMany(mappedBy = "product")
    private List<OfferedCommodity> offeredCommodities;

}
