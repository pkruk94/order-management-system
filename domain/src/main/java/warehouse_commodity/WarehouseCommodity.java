package warehouse_commodity;

import base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import product.Product;
import warehouse.Warehouse;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter

@Entity
@Table(name = "warehouse_commodities",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"product_id", "warehouse_id"})})
public class WarehouseCommodity extends BaseEntity {

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id", unique = true)
    private Product product;

    private Integer quantity;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

}
