package christmas.model;

import christmas.config.DiscountType;
import christmas.config.Menu;

import java.time.DayOfWeek;

public class WeekendDiscount implements Discount {
    private final int discountAmount;

    private WeekendDiscount(int discountAmount) {
        this.discountAmount = discountAmount;
    }

    public static WeekendDiscount of(ReservationDay reservationDay, Order order) {
        return new WeekendDiscount(calculateDiscountAmount(reservationDay, order));
    }

    private static int calculateDiscountAmount(ReservationDay reservationDay, Order order) {
        if (isWeekend(reservationDay)){
            int mainCount = order.countItemsByCategory(Menu.Category.MAIN);
            return DiscountType.WEEKEND.getAmount() * mainCount;
        }

        return 0;
    }

    private static boolean isWeekend(ReservationDay reservationDay) {
        DayOfWeek dayOfWeek = reservationDay.getDayOfWeek();
        return dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY;
    }

    @Override
    public DiscountType getDiscountType() {
        return DiscountType.WEEKEND;
    }

    @Override
    public int getDiscountAmount() {
        return discountAmount;
    }
}
