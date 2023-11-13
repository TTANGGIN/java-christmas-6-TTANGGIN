package christmas.view;

import christmas.config.EventMessage;
import christmas.config.Header;

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

    public void printEventBenefitsMessage(int reservationDay) {
        printMessage(EventMessage.PREVIEW_EVENT_BENEFITS.getFormattedMessage(reservationDay));
    }

    public void printOrderDetails(String orderDetails) {
        printMessage(Header.ORDER.getHeader() + orderDetails);
    }

    public void printTotalAmountBeforeDiscount(int amount) {
        printMessage(formatCurrencyWithHeader(Header.TOTAL_AMOUNT_BEFORE_DISCOUNT, amount));
    }

    public void printTotalDiscount(int discountAmount) {
        printMessage(formatCurrencyWithHeader(Header.TOTAL_DISCOUNT_AMOUNT, (discountAmount * -1)));
    }

    public void printTotalAmountAfterDiscount(int amount) {
        printMessage(formatCurrencyWithHeader(Header.TOTAL_AMOUNT_AFTER_DISCOUNT, amount));
    }

    public void printGift(String gift) {
        printMessage(Header.GIFT.getHeader() + gift);
    }

    public void printEventDetails(String eventDetails) {
        printMessage(Header.DISCOUNT_DETAILS.getHeader() + eventDetails);
    }

    public void printBadge(String badge) {
        printMessage(Header.EVENT_BADGE.getHeader() + badge);
    }

    public void printExceptionMessage(Exception e) {
        printMessage(e.getMessage());
    }

    private static String formatCurrencyWithHeader(Header header, int money) {
        return String.format("%s%,d원", header.getHeader(), money);
    }
    private static void printMessage(Object message) {
        System.out.println(message);
    }
}
