package producer;

import address.Address;
import base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import product.Product;
import warranty.Warranty;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

@Entity
@Table(name = "producers")
public class Producer extends BaseEntity {

    private String name;
    private Market market;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id", unique = true)
    private Address hqAddress;

    @OneToMany(mappedBy = "producer")
    private Set<Warranty> warrantyPolicies;

    @OneToMany(mappedBy = "producer")
    private List<Product> products;



}
