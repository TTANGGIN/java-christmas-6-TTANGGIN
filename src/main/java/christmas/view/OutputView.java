package christmas.view;

import christmas.config.EventMessage;
import christmas.config.Header;
import christmas.dto.response.EventDetailsDto;
import christmas.dto.response.OrderDto;
import christmas.dto.response.ReservationDayDto;

public class OutputView {
    public OutputView() {
    }

    public static OutputView getInstance() {
        return OutputView.LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final OutputView INSTANCE = new OutputView();
    }

    public void printWelcomeMessage() {
        printMessage(EventMessage.WELCOME_MESSAGE.getMessage());
    }

    public void printReservationMessage() {
        printMessage(EventMessage.ASK_RESERVATION_DAY.getMessage());
    }

    public void printOrderMessage() {
        printMessage(EventMessage.ASK_ORDER.getMessage());
    }

    public void printEventBenefitsMessage(ReservationDayDto reservationDayDto) {
        int reservationDay = reservationDayDto.getDay();
        printMessage(EventMessage.PREVIEW_EVENT_BENEFITS.getFormattedMessage(reservationDay));
    }

    public void printOrderDetails(OrderDto orderDto) {
        String orderDetails = orderDto.getOrderDetails();
        printMessage(Header.ORDER.getHeader() + orderDetails);
    }

    public void printTotalAmountBeforeDiscount(OrderDto orderDto) {
        int totalAmount = orderDto.getTotalAmount();
        printMessage(formatCurrencyWithHeader(Header.TOTAL_AMOUNT_BEFORE_DISCOUNT, totalAmount));
    }

    public void printTotalDiscount(EventDetailsDto eventDetailsDto) {
        int totalDiscount = eventDetailsDto.getTotalDiscountAmount();
        printMessage(formatCurrencyWithHeader(Header.TOTAL_DISCOUNT_AMOUNT, (totalDiscount * -1)));
    }

    public void printTotalAmountAfterDiscount(OrderDto orderDto, EventDetailsDto eventDetailsDto) {
        int amount = orderDto.getTotalAmount() - eventDetailsDto.getTotalDiscountAmount();
        printMessage(formatCurrencyWithHeader(Header.TOTAL_AMOUNT_AFTER_DISCOUNT, amount));
    }

    public void printGift(EventDetailsDto eventDetailsDto) {
        String gift = eventDetailsDto.getGift();
        printMessage(Header.GIFT.getHeader() + gift);
    }

    public void printEventDetails(EventDetailsDto eventDetailsDto) {
        String eventDetails = eventDetailsDto.getEventDetails();
        printMessage(Header.DISCOUNT_DETAILS.getHeader() + eventDetails);
    }

    public void printBadge(EventDetailsDto eventDetailsDto) {
        String badge = eventDetailsDto.getBadge();
        printMessage(Header.EVENT_BADGE.getHeader() + badge);
    }

    public void printExceptionMessage(Exception e) {
        printMessage(e.getMessage());
    }

    private static String formatCurrencyWithHeader(Header header, int money) {
        return String.format("%s%,d원", header.getHeader(), money);
    }
    private static void printMessage(String message) {
        System.out.println(message);
    }
}
