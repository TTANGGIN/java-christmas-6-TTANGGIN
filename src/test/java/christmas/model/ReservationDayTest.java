package christmas.model;

import christmas.config.ErrorMessage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class ReservationDayTest {

    @Nested
    @DisplayName("of 메소드는")
    class Describe_of {

        @Nested
        @DisplayName("유효한 날짜가 주어졌을 때")
        class Context_with_valid_date {

            // 1부터 31까지의 날짜를 생성하는 메소드
            static Stream<String> validDayProvider() {
                return IntStream.rangeClosed(1, 31).mapToObj(String::valueOf);
            }

            @ParameterizedTest
            @MethodSource("validDayProvider")
            @DisplayName("ReservationDay 객체를 반환한다")
            void it_returns_reservation_day_object(int day) {
                // 준비
                int year = 2023;
                int month = 12;

                // 실행
                ReservationDay reservationDay = ReservationDay.of(year, month, day);

                // 검증
                assertThat(reservationDay).isNotNull();
                assertThat(reservationDay.getDay()).isEqualTo(day);
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

        // 1부터 31까지의 날짜와 2023년 12월 해당 날짜의 요일을 생성하는 메소드
        static Stream<Arguments> validDayAndDayOfWeekProvider() {
            return IntStream.rangeClosed(1, 31).mapToObj(day -> {
                LocalDate date = LocalDate.of(2023, 12, day);
                return Arguments.of(day, date.getDayOfWeek());
            });
        }

        @ParameterizedTest
        @MethodSource("validDayAndDayOfWeekProvider")
        @DisplayName("주어진 일과 요일에 대한 ReservationDay 객체를 반환한다")
        void it_returns_reservation_day_object(int day, DayOfWeek dayOfWeek) {

            // 실행
            ReservationDay reservationDay = ReservationDay.of(day, dayOfWeek);

            // 검증
            assertThat(reservationDay).isNotNull();
            assertThat(reservationDay.getDay()).isEqualTo(day);
            assertThat(reservationDay.getDayOfWeek()).isEqualTo(dayOfWeek);
        }
    }
}