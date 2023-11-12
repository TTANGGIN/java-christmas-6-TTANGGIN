package christmas.dto.request;

public class OrderInputDto {
    private final String orderInput;

    private OrderInputDto(String orderInput) {
        this.orderInput = orderInput;
    }

    public static OrderInputDto from(String orderInput) {
        return new OrderInputDto(orderInput);
    }

    public String getOrderInput() {
        return orderInput;
    }
}
