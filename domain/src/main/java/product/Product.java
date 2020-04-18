package product;

import base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import producer.Producer;
import value_object.Money;
import warranty.Warranty;

import javax.persistence.*;

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

    @Enumerated(EnumType.STRING)
    private ProductCategory productCategory;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "producer_id")
    private Producer producer;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "warranty_id")
    private Warranty warrantyPolicy;

}
