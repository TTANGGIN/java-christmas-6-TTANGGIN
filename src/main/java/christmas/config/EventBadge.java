package christmas.config;

public enum EventBadge {
    SANTA("산타", 20_000),
    TREE("트리", 10_000),
    STAR("별", 5_000),
    NONE("없음", 0);

    private final String badge;
    private final int amount;

    EventBadge(String badge, int amount) {
        this.badge = badge;
        this.amount = amount;
    }

    public String getBadge() {
        return badge;
    }

    public int getAmount() {
        return amount;
    }
}
