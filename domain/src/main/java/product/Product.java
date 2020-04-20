package product;

import base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import order.Order;
import order_position.OrderPosition;
import producer.Producer;
import value_object.Money;
import value_object.PercentageValue;
import warranty.Warranty;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    private String name;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "price"))
    private Money price;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "discount"))
    private PercentageValue discount;

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

    public Money totalPrice() { return price.withDiscount(discount); }

}
