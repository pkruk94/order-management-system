package service.order;

import dto.address.AddressData;
import dto.order.CreateOrder;
import dto.order_position.CreateOrderPosition;
import exception.OrderServiceException;
import lombok.RequiredArgsConstructor;
import order.OrderRepository;
import order.PaymentMethod;
import order_position.OrderPositionRepository;
import shop.ShopRepository;
import user.User;
import user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;
    private OrderPositionRepository orderPositionRepository;
    private ShopRepository shopRepository;
    private UserRepository userRepository;

    public Long makeOrder(CreateOrder createOrder) {

        User user = userRepository.findByID(createOrder.getUserID()).orElseThrow(() -> new OrderServiceException("User not found in DB"));

        if (user.getManager() == null) {
            assignUserManager(user);
        }

        validateWarehouseAvailability(createOrder.getOrderPositions());

        return null;
    }

    private void assignUserManager(User user) {
        User manager = userRepository.findManagerWithLowestNumOfCustomers().orElseThrow(() -> new OrderServiceException("Problem while getting available manager"));
        user.setManager(manager);
    }

    private void validateWarehouseAvailability(List<CreateOrderPosition> createOrderPositions) {
        for (CreateOrderPosition createOrderPosition : createOrderPositions) {
            if (createOrderPosition.getQuantity() > shopRepository.findProductCountInCorrespondingWarehouses(createOrderPosition.getProductID(), createOrderPosition.getShopID())) {
                throw new OrderServiceException("Cannot realize this order - product not available in store");
            }
        }
    }
}