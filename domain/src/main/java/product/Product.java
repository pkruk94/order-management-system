package product;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import producer.Producer;
import value_object.Money;
import warranty.Warranty;

import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
@Table(name = "products")
public class Product {

    private String name;
    private Money price;
    private ProductCategory productCategory;
    private Producer producer;
    private Warranty warrantyPolicy;

}
