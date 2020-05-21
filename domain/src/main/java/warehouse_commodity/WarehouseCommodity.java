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
// TODO get embeded id?
public class WarehouseCommodity extends BaseEntity {

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    private Integer minimalNumberReqInWarehouse;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

}
