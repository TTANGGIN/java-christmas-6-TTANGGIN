package christmas.model;

import christmas.config.ErrorMessage;
import christmas.config.EventConfig;
import christmas.config.Menu;
import christmas.dto.request.DayInputDto;
import christmas.dto.request.OrderInputDto;
import christmas.dto.response.EventDetailsDto;
import christmas.dto.response.OrderDto;
import christmas.dto.response.ReservationDayDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventPlanningService {
    private static final Menu EVENT_GIFT = Menu.CHAMPAGNE;
    private static final int QUANTITY = 1;

    private EventPlanningService() {
    }

    public static EventPlanningService getInstance() {
        return new EventPlanningService();
    }

    public ReservationDayDto createValidReservationDay(DayInputDto dayInputDto) {
        int year = EventConfig.EVENT_YEAR.getValue();
        int month = EventConfig.EVENT_MONTH.getValue();
        int day = validateAndParseInteger(dayInputDto.getDayInput());

        ReservationDay reservationDay = ReservationDay.of(year, month, day);
        return ReservationDayDto.from(reservationDay);
    }

    public OrderDto createValidOrder(OrderInputDto orderInputDto) {
        Order order = Order.from(orderInputDto.getOrderInput());
        return OrderDto.from(order);
    }

    private static int validateAndParseInteger(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_DAY_INPUT.getMessage());
        }
    }

    public EventDetailsDto createEventDetails(ReservationDayDto reservationDayDto, OrderDto orderDto) {
        ReservationDay reservationDay
                = ReservationDay.of(reservationDayDto.getDay(), reservationDayDto.getDayOfWeek());
        Order order = Order.from(orderDto.getOrderItems());
        int totalAmount = orderDto.getTotalAmount();

        if (canParticipateEvent(totalAmount)) {
            return EventDetailsDto.of(Gift.empty(), Discounts.empty(),BadgeDeterminer.empty());
        }
        Gift gift = getGift(orderDto.getTotalAmount());
        Discounts discounts = calculateDiscounts(reservationDay, order, gift);
        BadgeDeterminer badge = BadgeDeterminer.from(discounts.getTotalDiscountAmount());

        return EventDetailsDto.of(gift, discounts, badge);
    }

    private static boolean canParticipateEvent(int totalAmount) {
        if (totalAmount >= EventConfig.EVENT_THRESHOLD_AMOUNT.getValue()) {
            return true;
        }
        return false;
    }

    private static Discounts calculateDiscounts(ReservationDay reservationDay, Order order, Gift gift) {
        List<Discount> discountList = new ArrayList<>(Arrays.asList(
                ChristmasDdayDiscount.from(reservationDay),
                WeekdayDiscount.of(reservationDay, order),
                WeekendDiscount.of(reservationDay, order),
                SpecialDiscount.from(reservationDay)
        ));

        if (!gift.isEmpty()) {
            discountList.add(Gift.copyOf(gift));
        }

        return Discounts.from(discountList);
    }

    private static Gift getGift(int totalAmount) {
        if (totalAmount >= EventConfig.GIFT_THRESHOLD_AMOUNT.getValue()) {
            return Gift.of(EVENT_GIFT,QUANTITY);
        }
        return Gift.empty();
    }
}
