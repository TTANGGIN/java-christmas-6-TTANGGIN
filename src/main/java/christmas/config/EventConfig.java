package christmas.config;

public enum EventConfig {
    EVENT_YEAR(2023),
    EVENT_MONTH(12),
    EVENT_DAY(25),
    MIN_ORDER(1),
    MAX_ORDER(20),
    EVENT_THRESHOLD_AMOUNT(10_000),
    GIFT_THRESHOLD_AMOUNT(120_000);

    private final int value;

    EventConfig(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
