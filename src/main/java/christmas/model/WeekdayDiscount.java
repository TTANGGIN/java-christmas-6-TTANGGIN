package christmas.model;

import christmas.config.DiscountType;
import christmas.config.Menu;

import java.time.DayOfWeek;

public class WeekdayDiscount implements Discount {
    private final int discountAmount;

    private WeekdayDiscount(int discountAmount) {
        this.discountAmount = discountAmount;
    }

    public static WeekdayDiscount of(ReservationDay reservationDay, Order order) {
        return new WeekdayDiscount(calculateDiscountAmount(reservationDay, order));
    }

    private static int calculateDiscountAmount(ReservationDay reservationDay, Order order) {
        if (isWeekday(reservationDay)){
            int dessertCount = order.countItemsByCategory(Menu.Category.DESSERT);
            return DiscountType.WEEKDAY.getAmount() * dessertCount;
        }

        return 0;
    }

    private static boolean isWeekday(ReservationDay reservationDay) {
        DayOfWeek dayOfWeek = reservationDay.getDayOfWeek();
        return !(dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY);
    }

    @Override
    public DiscountType getDiscountType() {
        return DiscountType.WEEKDAY;
    }

    @Override
    public int getDiscountAmount() {
        return discountAmount;
    }
}
