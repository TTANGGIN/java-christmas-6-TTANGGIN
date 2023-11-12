package christmas.dto.response;

import christmas.model.BadgeDeterminer;
import christmas.model.Discounts;
import christmas.model.Gift;

public class EventDetailsDto {
    private final String gift;
    private final String eventDetails;
    private final String badge;
    private final int totalDiscountAmount;

    private EventDetailsDto(String gift, String eventDetails, String badge, int totalDiscountAmount) {
        this.eventDetails = eventDetails;
        this.badge = badge;
        this.gift = gift;
        this.totalDiscountAmount = totalDiscountAmount;
    }

    public static EventDetailsDto of(Gift gift, Discounts discounts, BadgeDeterminer badgeDeterminer) {
        return new EventDetailsDto(gift.toString(), discounts.toString()
                , badgeDeterminer.toString(), discounts.getTotalDiscountAmount());
    }

    public String getGift() {
        return gift;
    }

    public String getEventDetails() {
        return eventDetails;
    }

    public String getBadge() {
        return badge;
    }

    public int getTotalDiscountAmount() {
        return totalDiscountAmount;
    }
}
