package christmas.model;

import christmas.config.DiscountType;
import christmas.config.Menu;
import org.junit.jupiter.api.*;
import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.*;

class WeekendDiscountTest {

    @Nested
    @DisplayName("of 메소드는")
    class Describe_of {

        @Nested
        @DisplayName("주말에 해당하는 날짜와 주문이 주어졌을 때")
        class Context_with_weekend_and_order {

            @Test
            @DisplayName("주말 할인 객체를 반환한다")
            void it_returns_weekend_discount_object() {
                // 준비
                ReservationDay weekend = ReservationDay.of(2023, 12, 22);
                Order order = createSampleOrder();

                // 실행
                WeekendDiscount discount = WeekendDiscount.of(weekend, order);

                // 검증
                assertThat(discount).isNotNull();
                assertThat(discount.getDiscountAmount())
                        .isEqualTo(DiscountType.WEEKEND.getAmount() * countMainItems(order));
            }

            private int countMainItems(Order order) {
                return order.countItemsByCategory(Menu.Category.MAIN);
            }

            private Order createSampleOrder() {
                Map<Menu, Integer> orderItems = new HashMap<>();
                orderItems.put(Menu.T_BONE_STEAK, 1); // 메인 카테고리
                orderItems.put(Menu.CAESAR_SALAD, 2); // 애피타이저 카테고리
                return Order.from(orderItems);
            }
        }

        @Nested
        @DisplayName("평일에 해당하는 날짜와 주문이 주어졌을 때")
        class Context_with_weekday_and_order {

            @Test
            @DisplayName("할인 금액이 0인 할인 객체를 반환한다")
            void it_returns_discount_with_zero_amount() {
                // 준비
                ReservationDay weekday = ReservationDay.of(2023, 12, 21);
                Order order = createSampleOrder();

                // 실행
                WeekendDiscount discount = WeekendDiscount.of(weekday, order);

                // 검증
                assertThat(discount).isNotNull();
                assertThat(discount.getDiscountAmount()).isEqualTo(0);
            }

            private Order createSampleOrder() {
                Map<Menu, Integer> orderItems = new HashMap<>();
                orderItems.put(Menu.T_BONE_STEAK, 1); // 메인 카테고리
                orderItems.put(Menu.CAESAR_SALAD, 2); // 애피타이저 카테고리
                return Order.from(orderItems);
            }
        }
    }

    @Nested
    @DisplayName("getDiscountType 메소드는")
    class Describe_getDiscountType {

        @Test
        @DisplayName("할인 타입 'WEEKEND'를 반환한다")
        void it_returns_discount_type_weekend() {
            ReservationDay reservationDay = ReservationDay.of(2023, 12, 22);
            Order order = Order.from("양송이수프-1");
            WeekendDiscount discount = WeekendDiscount.of(reservationDay, order);

            DiscountType discountType = discount.getDiscountType();

            assertThat(discountType).isEqualTo(DiscountType.WEEKEND);
        }
    }
}