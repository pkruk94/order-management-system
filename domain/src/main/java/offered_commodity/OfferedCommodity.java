package offered_commodity;

import base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import product.Product;
import shop.Shop;
import value_object.PercentageValue;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

@Entity
@Table(name = "offered_commodities",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"product_id", "shop_id"})})
public class OfferedCommodity extends BaseEntity {

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id", unique = true)
    private Product product;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "discount"))
    private PercentageValue discount;

    private Integer quantity;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "shop_id")
    private Shop shop;
}
