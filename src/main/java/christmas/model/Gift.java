package christmas.model;

import christmas.config.DiscountType;
import christmas.config.Menu;

public class Gift implements Discount {
    private static final String EMPTY = "없음";
    private final Menu item;
    private final int quantity;

    private Gift(Menu item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public static Gift of(Menu item, int quantity) {

        return new Gift(item, quantity);
    }

    public static Gift copyOf(Gift gift) {
        return new Gift(gift.item, gift.quantity);
    }

    public static Gift empty() {
        return new Gift(null, 0);
    }

    public boolean isEmpty() {
        return item == null || quantity <= 0;
    }

    @Override
    public DiscountType getDiscountType() {
        return DiscountType.GIFT_EVENT;
    }

    @Override
    public int getDiscountAmount() {
        if (isEmpty()) {
            return 0;
        }
        return item.getPrice() * quantity;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return EMPTY;
        }
        return String.format("%s %d개", item.getName(), quantity) ;
    }
}