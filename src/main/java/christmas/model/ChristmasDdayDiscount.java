package christmas.model;

import christmas.config.DiscountType;
import christmas.config.ErrorMessage;
import christmas.config.EventConfig;

public class ChristmasDdayDiscount implements Discount {
    private static final int ADDITIONAL_DISCOUNT = 100;
    private static final int FIRST_DAY = 1;
    private static final int LAST_DAY = 31;
    private final int discountAmount;

    private ChristmasDdayDiscount(int discountAmount) {
        this.discountAmount = discountAmount;
    }

    public static ChristmasDdayDiscount from(ReservationDay reservationDay) {
        int day = reservationDay.getDay();
        validate(day);
        return new ChristmasDdayDiscount(calculateDiscountAmount(day));
    }

    private static void validate(int day) {
        if (day < FIRST_DAY || day > LAST_DAY) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_DAY_INPUT.getMessage());
        }
    }

    private static int calculateDiscountAmount(int day) {
        if (day > EventConfig.EVENT_DAY.getValue()) {
            return 0;
        }
        return DiscountType.CHRISTMAS_D_DAY.getAmount()
                + ADDITIONAL_DISCOUNT * (day - 1);
    }

    @Override
    public DiscountType getDiscountType() {
        return DiscountType.CHRISTMAS_D_DAY;
    }
    @Override
    public int getDiscountAmount() {
        return discountAmount;
    }
}
