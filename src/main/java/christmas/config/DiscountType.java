package christmas.config;

public enum DiscountType {
    CHRISTMAS_D_DAY("크리스마스 디데이 할인", 1000),
    WEEKDAY("평일 할인", 2023),
    WEEKEND("주말 할인", 2023),
    SPECIAL("특별 할인", 1000),
    GIFT_EVENT("증정 이벤트", 0);

    private final String type;
    private final int amount;

    DiscountType(String type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }
    public int getAmount() {
        return amount;
    }
}
