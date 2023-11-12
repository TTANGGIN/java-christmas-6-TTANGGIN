package christmas.model;

import christmas.config.ErrorMessage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class ReservationDayTest {

    @Nested
    @DisplayName("of 메소드는")
    class Describe_of {

        @Nested
        @DisplayName("유효한 날짜가 주어졌을 때")
        class Context_with_valid_date {

            @Test
            @DisplayName("ReservationDay 객체를 반환한다")
            void it_returns_reservation_day_object() {
                // 준비
                int year = 2023;
                int month = 3;
                int day = 15;

                // 실행
                ReservationDay reservationDay = ReservationDay.of(year, month, day);

                // 검증
                assertThat(reservationDay).isNotNull();
                assertThat(reservationDay.getDay()).isEqualTo(15);
                assertThat(reservationDay.getDayOfWeek()).isEqualTo(LocalDate.of(year, month, day).getDayOfWeek());
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
                int year = 2023;
                int month = 3;

                // 실행 및 검증
                assertThatThrownBy(() -> ReservationDay.of(year, month, day))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining(ErrorMessage.INVALID_DAY_INPUT.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("of 메소드는 (DayOfWeek 버전)")
    class Describe_of_with_dayOfWeek {

        @Test
        @DisplayName("주어진 일과 요일에 대한 ReservationDay 객체를 반환한다")
        void it_returns_reservation_day_object() {
            // 준비
            int day = 15;
            DayOfWeek dayOfWeek = DayOfWeek.WEDNESDAY;

            // 실행
            ReservationDay reservationDay = ReservationDay.of(day, dayOfWeek);

            // 검증
            assertThat(reservationDay).isNotNull();
            assertThat(reservationDay.getDay()).isEqualTo(day);
            assertThat(reservationDay.getDayOfWeek()).isEqualTo(dayOfWeek);
        }
    }
}