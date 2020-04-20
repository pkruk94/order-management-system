package discount_policy;


//TODO

import base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import value_object.PercentageValue;

import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
@Table(name = "discount_policies")
public class DiscountPolicy extends BaseEntity {

    private PercentageValue discount;
}
