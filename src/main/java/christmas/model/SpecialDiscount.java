package christmas.model;

import christmas.config.DiscountType;
import christmas.config.EventConfig;

import java.time.DayOfWeek;

public class SpecialDiscount implements Discount {

    private static final DayOfWeek SPECIAL_DAY_OF_WEEK = DayOfWeek.SUNDAY;
    private static final int SPECIAL_DAY = EventConfig.EVENT_DAY.getValue();
    private final int discountAmount;

    private SpecialDiscount(int discountAmount) {
        this.discountAmount = discountAmount;
    }

    public static SpecialDiscount from(ReservationDay reservationDay) {
        return new SpecialDiscount(calculateDiscountAmount(reservationDay));
    }

    private static int calculateDiscountAmount(ReservationDay reservationDay) {
        if (isSpecialDay(reservationDay)) {
            return DiscountType.SPECIAL.getAmount();
        }
        return 0;
    }

    private static boolean isSpecialDay(ReservationDay reservationDay) {
        return reservationDay.getDayOfWeek() == SPECIAL_DAY_OF_WEEK
                || reservationDay.getDay() == SPECIAL_DAY;
    }

    @Override
    public DiscountType getDiscountType() {
        return DiscountType.SPECIAL;
    }

    @Override
    public int getDiscountAmount() {
        return discountAmount;
    }
}
