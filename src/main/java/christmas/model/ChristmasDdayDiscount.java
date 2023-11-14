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

    /**
     * 주어진 예약일을 기준으로 크리스마스 D-day 할인 객체 생성
     * 할인 적용 여부는 예약일과 크리스마스 D-day의 차이에 따라 결정된다.
     *
     * @param reservationDay 할인 계산을 위한 예약일
     * @return 크리스마스 D-day 할인 객체
     */
    public static ChristmasDdayDiscount from(ReservationDay reservationDay) {
        int day = reservationDay.getDay();
        validateDayInRange(day);
        return new ChristmasDdayDiscount(calculateDiscountAmount(day));
    }

    private static void validateDayInRange(int day) {
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
