package christmas.model;

import christmas.config.DiscountType;
import christmas.config.ErrorMessage;
import christmas.config.EventConfig;
import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;

class SpecialDiscountTest {

    @Nested
    @DisplayName("from 메소드는")
    class Describe_from {

        @Nested
        @DisplayName("특별 할인이 적용되는 날짜일 때")
        class Context_with_special_day {

            @Test
            @DisplayName("특별 할인 객체를 반환한다")
            void it_returns_special_discount_object() {
                // 준비
                ReservationDay specialDay = ReservationDay.of(EventConfig.EVENT_YEAR.getValue(), EventConfig.EVENT_MONTH.getValue(), EventConfig.EVENT_DAY.getValue());

                // 실행
                SpecialDiscount discount = SpecialDiscount.from(specialDay);

                // 검증
                assertThat(discount).isNotNull();
                assertThat(discount.getDiscountAmount()).isEqualTo(DiscountType.SPECIAL.getAmount());
            }
        }

        @Nested
        @DisplayName("특별 할인이 적용되지 않는 날짜일 때")
        class Context_with_non_special_day {

            @Test
            @DisplayName("할인 금액이 0인 할인 객체를 반환한다")
            void it_returns_discount_with_zero_amount() {
                // 준비
                ReservationDay nonSpecialDay = ReservationDay.of(EventConfig.EVENT_YEAR.getValue(), EventConfig.EVENT_MONTH.getValue(), EventConfig.EVENT_DAY.getValue() + 1);

                // 실행
                SpecialDiscount discount = SpecialDiscount.from(nonSpecialDay);

                // 검증
                assertThat(discount).isNotNull();
                assertThat(discount.getDiscountAmount()).isEqualTo(0);
            }
        }

        @Nested
        @DisplayName("예외 상황일 때")
        class Context_with_exceptional_condition {

            @Test
            @DisplayName("잘못된 날짜 입력으로 인해 IllegalArgumentException을 발생시킨다")
            void it_throws_illegalArgumentException() {
                // 준비
                int year = EventConfig.EVENT_YEAR.getValue();
                int month = EventConfig.EVENT_MONTH.getValue();
                int invalidDay = 32;

                // 실행 및 검증
                assertThatThrownBy(() -> SpecialDiscount.from(ReservationDay.of(year, month, invalidDay)))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining(ErrorMessage.INVALID_DAY_INPUT.getMessage());
            }
        }
    }
}