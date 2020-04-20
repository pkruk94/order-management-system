package warehouse_commodity;

import base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import product.Product;
import value_object.PercentageValue;
import warehouse.Warehouse;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

@Entity
@Table(name = "warehouse_commodities",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"product", "warehouse"})})
public class WarehouseCommodity extends BaseEntity {

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id", unique = true)
    private Product product;

    private Integer quantity;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

}
