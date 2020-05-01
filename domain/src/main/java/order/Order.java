package order;

import address.Address;
import base.BaseEntity;
import discount_policy.DiscountPolicy;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import order_position.OrderPosition;
import user.User;
import value_object.Money;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id")
    private User customer;

    private LocalDateTime orderTime;

    private LocalDateTime paymentDeadline;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "discount_policy")
    private DiscountPolicy discountPolicy;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address")
    private Address address;

    @OneToMany(mappedBy = "order")
    private List<OrderPosition> orderPosition;

//    public Money totalPrice() {
//        return orderPosition
//                .stream()
//                .map(OrderPosition::totalPrice)
//                .reduce(new Money(), Money::add);
//    }
}
