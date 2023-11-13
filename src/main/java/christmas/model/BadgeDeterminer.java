package christmas.model;

import christmas.config.EventBadge;

public class BadgeDeterminer {
    private final String eventBadge;

    private BadgeDeterminer(String eventBadge) {
        this.eventBadge = eventBadge;
    }

    public static BadgeDeterminer from(int totalDiscountAmount) {
        return new BadgeDeterminer(determineEventBadge(totalDiscountAmount));
    }

    public static BadgeDeterminer empty() {
        return new BadgeDeterminer(EventBadge.NONE.getBadge());
    }

    private static String determineEventBadge(int totalDiscountAmount) {
        if (totalDiscountAmount >= EventBadge.SANTA.getAmount()) {
            return EventBadge.SANTA.getBadge();
        }
        if (totalDiscountAmount >= EventBadge.TREE.getAmount()) {
            return EventBadge.TREE.getBadge();
        }
        if (totalDiscountAmount >= EventBadge.STAR.getAmount()) {
            return EventBadge.STAR.getBadge();
        }

        return EventBadge.NONE.getBadge();
    }

    @Override
    public String toString() {
        return eventBadge;
    }
}
