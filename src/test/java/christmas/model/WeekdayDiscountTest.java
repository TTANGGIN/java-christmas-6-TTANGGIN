package christmas.model;

import christmas.config.DiscountType;
import christmas.config.Menu;
import org.junit.jupiter.api.*;
import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.*;

class WeekdayDiscountTest {

    @Nested
    @DisplayName("of 메소드는")
    class Describe_of {

        @Nested
        @DisplayName("평일에 해당하는 날짜와 주문이 주어졌을 때")
        class Context_with_weekday_and_order {

            @Test
            @DisplayName("평일 할인 객체를 반환한다")
            void it_returns_weekday_discount_object() {
                // 준비
                ReservationDay weekday = ReservationDay.of(2023, 12, 20); // 예: 수요일
                Order order = createSampleOrder();

                // 실행
                WeekdayDiscount discount = WeekdayDiscount.of(weekday, order);

                // 검증
                assertThat(discount).isNotNull();
                assertThat(discount.getDiscountAmount())
                        .isEqualTo(DiscountType.WEEKDAY.getAmount() * countDessertItems(order));
            }

            private int countDessertItems(Order order) {
                return order.countItemsByCategory(Menu.Category.DESSERT);
            }

            private Order createSampleOrder() {
                Map<Menu, Integer> orderItems = new HashMap<>();
                orderItems.put(Menu.CHOCO_CAKE, 2); // 디저트 카테고리
                orderItems.put(Menu.T_BONE_STEAK, 1); // 메인 카테고리
                return Order.from(orderItems);
            }
        }

        @Nested
        @DisplayName("주말에 해당하는 날짜와 주문이 주어졌을 때")
        class Context_with_weekend_and_order {

            @Test
            @DisplayName("할인 금액이 0인 할인 객체를 반환한다")
            void it_returns_discount_with_zero_amount() {
                // 준비
                ReservationDay weekend = ReservationDay.of(2023, 12, 23);
                Order order = createSampleOrder();

                // 실행
                WeekdayDiscount discount = WeekdayDiscount.of(weekend, order);

                // 검증
                assertThat(discount).isNotNull();
                assertThat(discount.getDiscountAmount()).isEqualTo(0);
            }

            private Order createSampleOrder() {
                Map<Menu, Integer> orderItems = new HashMap<>();
                orderItems.put(Menu.CHOCO_CAKE, 2); // 디저트 카테고리
                orderItems.put(Menu.T_BONE_STEAK, 1); // 메인 카테고리
                return Order.from(orderItems);
            }
        }
    }
}