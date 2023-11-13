package christmas.dto.response;

import christmas.config.Menu;
import christmas.model.Order;

import java.util.HashMap;
import java.util.Map;

public class OrderDto {
    private final Map<Menu, Integer> orderItems;
    private final int totalAmount;
    private final String orderDetails;

    private OrderDto(Map<Menu, Integer> orderItems, int totalAmount, String orderDetails) {
        this.orderItems = orderItems;
        this.totalAmount = totalAmount;
        this.orderDetails = orderDetails;
    }

    public static OrderDto from(Order order) {
        return new OrderDto(new HashMap<>(order.getOrderItems()), order.getTotalAmount(), order.toString());
    }

    public Map<Menu, Integer> getOrderItems() {
        return orderItems;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public String getOrderDetails() {
        return orderDetails;
    }
}
