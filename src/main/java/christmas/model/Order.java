package christmas.model;

import christmas.config.ErrorMessage;
import christmas.config.EventConfig;
import christmas.config.Menu;
import christmas.util.MenuUtils;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Order {
    private static final Pattern ORDER_PATTERN = Pattern.compile("([가-힣]+-[0-9]+)(,[가-힣]+-[0-9]+)*");
    private static final String ITEM_SPLIT_SYMBOL = ",";
    private static final String PARTS_SPLIT_SYMBOL = "-";
    private static final String OUTPUT_FORMAT = "%s %d개";

    private final Map<Menu, Integer> orderItems;

    private Order(Map<Menu, Integer> orderItems) {
        this.orderItems = Map.copyOf(orderItems);
    }

    public static Order from(String orderInput) {
        validateOrderFormat(orderInput);
        Map<Menu, Integer> orderItems = parseOrderItems(orderInput);
        validateOrderItems(orderItems);
        validateTotalOrderQuantity(orderItems);
        return new Order(orderItems);
    }

    public static Order from(Map<Menu, Integer> orderItems) {
        return new Order(orderItems);
    }

    private static void validateOrderFormat(String orderInput) {
        if (!ORDER_PATTERN.matcher(orderInput).matches()) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_ORDER_INPUT.getMessage());
        }
    }

    private static Map<Menu, Integer> parseOrderItems(String orderInput) {
        return Arrays.stream(orderInput.split(ITEM_SPLIT_SYMBOL))
                .map(Order::parseItem)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (existing, replacement) -> {
                    throw new IllegalArgumentException(ErrorMessage.INVALID_ORDER_INPUT.getMessage());
                }));
    }

    private static Map.Entry<Menu, Integer> parseItem(String item) {
        String[] parts = item.split(PARTS_SPLIT_SYMBOL);
        String menuName = parts[0];
        int quantity = Integer.parseInt(parts[1]);

        validateMenuItem(menuName, quantity);
        Menu menu = MenuUtils.getMenuByName(menuName);

        return new AbstractMap.SimpleEntry<>(menu, quantity);
    }

    private static void validateMenuItem(String menuName, int quantity) {
        if (!MenuUtils.menuExists(menuName)
                || quantity < EventConfig.MIN_ORDER.getValue()
                || quantity > EventConfig.MAX_ORDER.getValue()) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_ORDER_INPUT.getMessage());
        }
    }

    private static void validateOrderItems(Map<Menu, Integer> orderItems) {
        if (orderItems.keySet().stream().allMatch(menu -> menu.getCategory() == Menu.Category.BEVERAGE)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_ORDER_INPUT.getMessage());
        }
    }

    private static void validateTotalOrderQuantity(Map<Menu, Integer> orderItems) {
        int totalQuantity = orderItems.values().stream().mapToInt(Integer::intValue).sum();

        if (totalQuantity < EventConfig.MIN_ORDER.getValue() || totalQuantity > EventConfig.MAX_ORDER.getValue()) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_ORDER_INPUT.getMessage());
        }
    }

    public int countItemsByCategory(Menu.Category category) {
        return orderItems.entrySet().stream()
                .filter(entry -> entry.getKey().getCategory() == category)
                .mapToInt(Map.Entry::getValue)
                .sum();
    }

    public Map<Menu, Integer> getOrderItems() {
        return orderItems;
    }

    public int getTotalAmount() {
        return orderItems.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    @Override
    public String toString() {
        return orderItems.entrySet().stream()
                .map(entry -> String.format(OUTPUT_FORMAT, entry.getKey().getName(), entry.getValue()))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}