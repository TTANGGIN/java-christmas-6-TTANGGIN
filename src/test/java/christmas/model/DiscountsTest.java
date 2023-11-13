package christmas.model;

import christmas.config.DiscountType;
import christmas.config.Menu;
import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class DiscountsTest {

    @Nested
    @DisplayName("from 메소드는")
    class Describe_from {

        @Nested
        @DisplayName("유효한 Discount 객체 리스트가 주어졌을 때")
        class Context_with_valid_discount_list {

            @Test
            @DisplayName("Discount 객체들을 포함하는 Discounts 객체를 생성한다")
            void it_creates_discounts_object_containing_given_discounts() {
                // 준비
                ReservationDay reservationDay = ReservationDay.of(2023, 12, 24);
                Order order = Order.from(Collections.singletonMap(Menu.CHOCO_CAKE, 2));

                List<Discount> discountList = Arrays.asList(
                        ChristmasDdayDiscount.from(reservationDay),
                        DayBasedDiscount.of(reservationDay, order),
                        SpecialDiscount.from(reservationDay),
                        Gift.of(Menu.CHAMPAGNE, 1)
                );

                // 실행
                Discounts discounts = Discounts.from(discountList);

                // 검증
                assertThat(discounts).isNotNull();
                assertThat(discounts.getTotalDiscountAmount()).isGreaterThanOrEqualTo(0);
            }
        }
    }

    @Nested
    @DisplayName("empty 메소드는")
    class Describe_empty {

        @Test
        @DisplayName("빈 Discounts 객체를 생성한다")
        void it_creates_an_empty_discounts_object() {
            // 실행
            Discounts emptyDiscounts = Discounts.empty();

            // 검증
            assertThat(emptyDiscounts.getTotalDiscountAmount()).isEqualTo(0);
            assertThat(emptyDiscounts.toString()).isEqualTo("없음");
        }
    }

    @Nested
    @DisplayName("getTotalDiscountAmount 메소드는")
    class Describe_getTotalDiscountAmount {

        @Nested
        @DisplayName("다양한 Discount 객체들이 포함된 Discounts 객체가 주어졌을 때")
        class Context_with_various_discounts {

            @Test
            @DisplayName("모든 할인 금액의 합을 반환한다")
            void it_returns_sum_of_all_discount_amounts() {
                // 준비
                ReservationDay reservationDay = ReservationDay.of(2023, 12, 24);
                Order order = Order.from(Collections.singletonMap(Menu.CHOCO_CAKE, 2));

                List<Discount> discountList = Arrays.asList(
                        ChristmasDdayDiscount.from(reservationDay),
                        DayBasedDiscount.of(reservationDay, order),
                        SpecialDiscount.from(reservationDay),
                        Gift.of(Menu.CHAMPAGNE, 1)
                );
                Discounts discounts = Discounts.from(discountList);

                // 실행
                int totalDiscountAmount = discounts.getTotalDiscountAmount();

                // 검증
                assertThat(totalDiscountAmount).isGreaterThanOrEqualTo(0);
            }
        }
    }

    @Nested
    @DisplayName("toString 메소드는")
    class Describe_toString {

        @Nested
        @DisplayName("할인 금액이 있는 Discounts 객체가 주어졌을 때")
        class Context_with_discounts_having_discount_amounts {

            @Test
            @DisplayName("할인 금액에 대한 요약 문자열을 반환한다")
            void it_returns_summary_string_of_discount_amounts() {
                // 준비
                ReservationDay reservationDay = ReservationDay.of(2023, 12, 24);
                Order order = Order.from(Collections.singletonMap(Menu.CHOCO_CAKE, 2));

                List<Discount> discountList = Arrays.asList(
                        ChristmasDdayDiscount.from(reservationDay),
                        DayBasedDiscount.of(reservationDay, order),
                        SpecialDiscount.from(reservationDay),
                        Gift.of(Menu.CHAMPAGNE, 1)
                );
                Discounts discounts = Discounts.from(discountList);

                // 실행
                String summary = discounts.toString();

                // 검증
                assertThat(summary).isNotEmpty();
                assertThat(summary).contains(DiscountType.CHRISTMAS_D_DAY.getType());
                assertThat(summary).contains(DiscountType.WEEKDAY.getType());
                assertThat(summary).doesNotContain(DiscountType.WEEKEND.getType());
                assertThat(summary).contains(DiscountType.SPECIAL.getType());
                assertThat(summary).contains(DiscountType.GIFT_EVENT.getType());
            }
        }
    }
}