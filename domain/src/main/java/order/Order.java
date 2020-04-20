package order;

import base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import order_position.OrderPosition;
import value_object.Money;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    private LocalDateTime orderTime;



    @OneToMany(mappedBy = "order")
    private List<OrderPosition> orderPosition;

//    public Money totalPrice() {
//        return orderPosition
//                .stream()
//                .map(OrderPosition::totalPrice)
//                .reduce(new Money(), Money::add);
//    }
}
