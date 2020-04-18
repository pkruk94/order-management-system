package warehouse;

import base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import product.Product;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

@Entity
@Table(name = "commodities")
public class Commodity extends BaseEntity {

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id", unique = true)
    private Product product;

    private Integer quantity;
}
