package christmas.model;

import christmas.config.DiscountType;

public interface Discount {

    DiscountType getDiscountType();
    int getDiscountAmount();

}
