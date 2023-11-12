package christmas.config;

public enum Header {
    ORDER("<주문 메뉴>"),
    TOTAL_AMOUNT_BEFORE_DISCOUNT("<할인 전 총주문 금액>"),
    GIFT("<증정 메뉴>"),
    DISCOUNT_DETAILS("<혜택 내역>"),
    TOTAL_DISCOUNT_AMOUNT("<총혜택 금액>"),
    TOTAL_AMOUNT_AFTER_DISCOUNT("<할인 후 예상 결제 금액>"),
    EVENT_BADGE("<12월 이벤트 배지>");

    private final String header;

    Header(String header) {
        this.header = header;
    }

    public String getHeader() {
        return System.lineSeparator() + header + System.lineSeparator();
    }
}
