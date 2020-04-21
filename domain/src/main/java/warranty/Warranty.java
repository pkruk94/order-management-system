package warranty;

import base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import producer.Producer;
import product.Product;
import value_object.PercentageValue;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

@Entity
@Table(name = "warranties")
public class Warranty extends BaseEntity {

    private Integer durationYears;

    private PercentageValue fracPriceReturned;

    private Integer completionTimeInDays;

    @ElementCollection(targetClass = WarrantyServices.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="warranty_services")
    @Column(name="service")
    private Set<WarrantyServices> servicesAvailable;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "producer_id")
    private Producer producer;

    @OneToMany(mappedBy = "warrantyPolicy")
    private List<Product> product;

}
