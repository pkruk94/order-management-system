package producer;

import address.Address;
import base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

@Entity
@Table(name = "producers")
public class Producer extends BaseEntity {

    private String name;
    private Market market;
    private Address hqAddress;
    private Set warrantyPolicies;

}
