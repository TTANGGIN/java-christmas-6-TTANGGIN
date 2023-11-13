package christmas.model;

import christmas.config.DiscountType;
import christmas.config.ErrorMessage;
import christmas.config.EventConfig;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.DayOfWeek;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class ChristmasDdayDiscountTest {

    @Nested
    @DisplayName("from 메소드는")
    class Describe_from {

        @Nested
        @DisplayName("유효한 날짜가 주어졌을 때")
        class Context_with_valid_date {

            // 1부터 25까지의 날짜를 생성하는 메소드
            static Stream<String> validDayProvider() {
                return IntStream.rangeClosed(1, 25).mapToObj(String::valueOf);
            }

            @ParameterizedTest
            @MethodSource("validDayProvider")
            @DisplayName("크리스마스 디데이 할인 객체를 반환한다")
            void it_returns_christmas_dday_discount_object(int day) {
                // 준비
                final int ADDITIONAL_DISCOUNT = 100;
                ReservationDay reservationDay = ReservationDay.of(EventConfig.EVENT_YEAR.getValue()
                        , EventConfig.EVENT_MONTH.getValue(), day);

                // 실행
                ChristmasDdayDiscount discount = ChristmasDdayDiscount.from(reservationDay);

                // 검증
                assertThat(discount).isNotNull();
                assertThat(discount.getDiscountAmount())
                        .isEqualTo(DiscountType.CHRISTMAS_D_DAY.getAmount() + ADDITIONAL_DISCOUNT * (day - 1));
            }
        }

        @Nested
        @DisplayName("유효하지 않은 날짜가 주어졌을 때")
        class Context_with_invalid_date {

            @ParameterizedTest
            @ValueSource(ints = {0, 32}) // 경계값 분석
            @DisplayName("IllegalArgumentException을 발생시킨다")
            void it_throws_illegalArgumentException(int day) {
                // 준비
                ReservationDay reservationDay
                        = ReservationDay.of(day, DayOfWeek.FRIDAY);

                // 실행 및 검증
                assertThatThrownBy(() -> ChristmasDdayDiscount.from(reservationDay))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining(ErrorMessage.INVALID_DAY_INPUT.getMessage());
            }
        }

        @Nested
        @DisplayName("이벤트 기간 이후의 날짜가 주어졌을 때")
        class Context_after_event_period {

            // 1부터 25까지의 날짜를 생성하는 메소드
            static Stream<String> invalidDayProvider() {
                return IntStream.rangeClosed(26, 31).mapToObj(String::valueOf);
            }

            @ParameterizedTest
            @MethodSource("invalidDayProvider")
            @DisplayName("할인 금액이 0인 할인 객체를 반환한다")
            void it_returns_discount_with_zero_amount(int dayAfterEvent) {
                // 준비
                ReservationDay reservationDay = ReservationDay.of(EventConfig.EVENT_YEAR.getValue()
                        , EventConfig.EVENT_MONTH.getValue(), dayAfterEvent);

                // 실행
                ChristmasDdayDiscount discount = ChristmasDdayDiscount.from(reservationDay);

                // 검증
                assertThat(discount).isNotNull();
                assertThat(discount.getDiscountAmount()).isEqualTo(0);
            }
        }
    }
}