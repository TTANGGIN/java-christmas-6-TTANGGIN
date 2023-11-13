package christmas.model;

import christmas.config.DiscountType;
import christmas.config.Menu;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class DayBasedDiscountTest {

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
                ReservationDay weekday = ReservationDay.of(2023, 12, 20);
                Order order = createSampleOrderWithDessert();

                // 실행
                DayBasedDiscount discount = DayBasedDiscount.of(weekday, order);

                // 검증
                assertThat(discount).isNotNull();
                assertThat(discount.getDiscountAmount())
                        .isEqualTo(DiscountType.WEEKDAY.getAmount() * countDessertItems(order));
                assertThat(discount.getDiscountType()).isEqualTo(DiscountType.WEEKDAY);
            }
        }

        @Nested
        @DisplayName("주말에 해당하는 날짜와 주문이 주어졌을 때")
        class Context_with_weekend_and_order {

            @Test
            @DisplayName("주말 할인 객체를 반환한다")
            void it_returns_weekend_discount_object() {
                // 준비
                ReservationDay weekend = ReservationDay.of(2023, 12, 22);
                Order order = createSampleOrderWithMain();

                // 실행
                DayBasedDiscount discount = DayBasedDiscount.of(weekend, order);

                // 검증
                assertThat(discount).isNotNull();
                assertThat(discount.getDiscountAmount())
                        .isEqualTo(DiscountType.WEEKEND.getAmount() * countMainItems(order));
                assertThat(discount.getDiscountType()).isEqualTo(DiscountType.WEEKEND);
            }
        }

        @Nested
        @DisplayName("12월의 모든 날에 해당하는 날짜와 주문이 주어졌을 때")
        class Context_with_all_dates_and_order {

            // 1부터 31까지의 날짜를 생성하는 메소드
            static Stream<String> validDayProvider() {
                return IntStream.rangeClosed(1, 31).mapToObj(String::valueOf);
            }

            @ParameterizedTest
            @MethodSource("validDayProvider")
            @DisplayName("1일부터 31일까지 각 날짜에 대해 올바른 할인 유형을 반환한다")
            void it_returns_correct_discount_for_each_day(int day) {
                ReservationDay reservationDay = ReservationDay.of(2023, 12, day);
                Order order = createSampleOrderWithDessert();

                DayBasedDiscount discount = DayBasedDiscount.of(reservationDay, order);

                DayOfWeek dayOfWeek = reservationDay.getDayOfWeek();
                if (DayBasedDiscount.isWeekday(dayOfWeek)) {
                    assertThat(discount.getDiscountAmount())
                            .isEqualTo(DiscountType.WEEKDAY.getAmount() * countDessertItems(order));
                    assertThat(discount.getDiscountType()).isEqualTo(DiscountType.WEEKDAY);
                } else if (DayBasedDiscount.isWeekend(dayOfWeek)) {
                    assertThat(discount.getDiscountAmount())
                            .isEqualTo(DiscountType.WEEKEND.getAmount() * countMainItems(order));
                    assertThat(discount.getDiscountType()).isEqualTo(DiscountType.WEEKEND);
                } else {
                    assertThat(discount).isEqualTo(DayBasedDiscount.empty());
                }
            }
        }
    }

    @Nested
    @DisplayName("empty 메소드는")
    class Describe_empty {

        @Test
        @DisplayName("할인 금액이 0이고 할인 유형이 null인 객체를 반환한다")
        void it_returns_an_empty_object() {
            DayBasedDiscount emptyDiscount = DayBasedDiscount.empty();

            assertThat(emptyDiscount).isNotNull();
            assertThat(emptyDiscount.getDiscountAmount()).isEqualTo(0);
            assertThat(emptyDiscount.getDiscountType()).isNull();
        }
    }

    private int countDessertItems(Order order) {
        return order.countItemsByCategory(Menu.Category.DESSERT);
    }

    private int countMainItems(Order order) {
        return order.countItemsByCategory(Menu.Category.MAIN);
    }

    private Order createSampleOrderWithDessert() {
        Map<Menu, Integer> orderItems = new HashMap<>();
        orderItems.put(Menu.CHOCO_CAKE, 2); // 디저트 카테고리
        orderItems.put(Menu.T_BONE_STEAK, 1); // 메인 카테고리
        return Order.from(orderItems);
    }

    private Order createSampleOrderWithMain() {
        Map<Menu, Integer> orderItems = new HashMap<>();
        orderItems.put(Menu.T_BONE_STEAK, 1); // 메인 카테고리
        orderItems.put(Menu.CAESAR_SALAD, 2); // 애피타이저 카테고리
        return Order.from(orderItems);
    }
}