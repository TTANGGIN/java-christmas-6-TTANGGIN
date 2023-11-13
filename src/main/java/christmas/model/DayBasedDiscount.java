package christmas.model;

import christmas.config.DiscountType;
import christmas.config.Menu;

import java.time.DayOfWeek;

public class DayBasedDiscount implements Discount {
    private final int discountAmount;
    private final DiscountType discountType;

    private DayBasedDiscount(int discountAmount, DiscountType discountType) {
        this.discountAmount = discountAmount;
        this.discountType = discountType;
    }

    public static DayBasedDiscount of(ReservationDay reservationDay, Order order) {
        DayOfWeek dayOfWeek = reservationDay.getDayOfWeek();
        if (isWeekday(dayOfWeek)) {
            return calculateWeekdayDiscount(order);
        }
        if (isWeekend(dayOfWeek)) {
            return calculateWeekendDiscount(order);
        }
        return empty();
    }

    public static DayBasedDiscount empty() {
        return new DayBasedDiscount(0, null);
    }

    public boolean isEmpty(DayBasedDiscount dayBasedDiscount) {
        return this.discountType == null;
    }

    private static DayBasedDiscount calculateWeekdayDiscount(Order order) {
        int dessertCount = order.countItemsByCategory(Menu.Category.DESSERT);
        return new DayBasedDiscount(DiscountType.WEEKDAY.getAmount() * dessertCount, DiscountType.WEEKDAY);
    }

    private static DayBasedDiscount calculateWeekendDiscount(Order order) {
        int mainCount = order.countItemsByCategory(Menu.Category.MAIN);
        return new DayBasedDiscount(DiscountType.WEEKEND.getAmount() * mainCount, DiscountType.WEEKEND);
    }

    public static boolean isWeekday(DayOfWeek dayOfWeek) {
        return !(dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY);
    }

    public static boolean isWeekend(DayOfWeek dayOfWeek) {
        return dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY;
    }

    @Override
    public DiscountType getDiscountType() {
        return discountType;
    }

    @Override
    public int getDiscountAmount() {
        return discountAmount;
    }
}