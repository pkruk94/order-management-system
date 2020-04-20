package order_position;

import base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import order.Order;
import product.Product;
import value_object.Money;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

@Entity
@Table(name = "orders_positions")
public class OrderPosition extends BaseEntity {

    private Integer quantity;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_id")
    private Order order;

    public Money totalPrice() { return product.totalPrice().multiply(quantity); }

}
