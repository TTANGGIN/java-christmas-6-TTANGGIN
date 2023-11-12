package christmas.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Discounts {

    private static final String EMPTY = "없음";
    private final List<Discount> discounts;

    private Discounts(List<Discount> discounts) {
        this.discounts = new ArrayList<>(discounts);
    }

    public static Discounts from(List<Discount> discounts) {
        return new Discounts(discounts);
    }

    public static Discounts empty() {
        return new Discounts(new ArrayList<>());
    }

    public int getTotalDiscountAmount() {
        return discounts.stream()
                .mapToInt(Discount::getDiscountAmount)
                .sum();
    }

    @Override
    public String toString() {
        String summary = discounts.stream()
                .filter(d -> d.getDiscountAmount() > 0)
                .map(d -> String.format("%s: -%,d원", d.getDiscountType().getType(), d.getDiscountAmount()))
                .collect(Collectors.joining(System.lineSeparator()));

        if (summary.isEmpty()) {
            return EMPTY;
        }
        return summary;
    }
}
