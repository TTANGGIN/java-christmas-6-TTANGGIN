package christmas.model;

import christmas.config.ErrorMessage;
import christmas.config.Menu;
import christmas.dto.request.DayInputDto;
import christmas.dto.response.EventDetailsDto;
import christmas.dto.response.OrderDto;
import christmas.dto.response.ReservationDayDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class EventPlanningServiceTest {

    private EventPlanningService eventPlanningService;

    @BeforeEach
    void setUp() {
        eventPlanningService = EventPlanningService.getInstance();
    }

    @Nested
    @DisplayName("createValidReservationDay 메소드는")
    class Describe_createValidReservationDay {

        @Nested
        @DisplayName("유효한 날짜 입력이 주어지면")
        class Context_with_valid_date_input {

            private final String validDayInput = "15";

            @Test
            @DisplayName("올바른 ReservationDayDto를 반환한다")
            void it_returns_valid_ReservationDayDto() {
                DayInputDto dayInputDto = DayInputDto.from(validDayInput);

                ReservationDayDto result = eventPlanningService.createValidReservationDay(dayInputDto);

                assertThat(result).isNotNull();
                assertThat(result.getDay()).isEqualTo(Integer.parseInt(validDayInput));
            }
        }

        @Nested
        @DisplayName("유효하지 않은 날짜 입력이 주어지면")
        class Context_with_invalid_date_input {

            private final String invalidDayInput = "32";

            @Test
            @DisplayName("IllegalArgumentException을 던진다")
            void it_throws_IllegalArgumentException() {
                DayInputDto dayInputDto = DayInputDto.from(invalidDayInput);

                assertThatThrownBy(() -> eventPlanningService.createValidReservationDay(dayInputDto))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining(ErrorMessage.INVALID_DAY_INPUT.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("createEventDetails 메소드는")
    class Describe_createEventDetails {

        @Nested
        @DisplayName("이벤트 참여 조건을 만족하지 않을 때")
        class Context_when_event_participation_condition_is_not_met {

            @Test
            @DisplayName("비어있는 EventDetailsDto를 반환한다")
            void it_returns_empty_EventDetailsDto() {
                ReservationDayDto reservationDayDto
                        = ReservationDayDto.from(ReservationDay.of(11, DayOfWeek.MONDAY));
                OrderDto orderDto = OrderDto.from(Order.from("양송이수프-1")); // 이벤트 참여 조건 미달

                EventDetailsDto result = eventPlanningService.createEventDetails(reservationDayDto, orderDto);

                assertThat(result).isNotNull();
                assertThat(result.getGift()).isEqualTo(Gift.empty().toString());
                assertThat(result.getTotalDiscountAmount()).isEqualTo(Discounts.empty().getTotalDiscountAmount());
                assertThat(result.getBadge()).isEqualTo(BadgeDeterminer.empty().toString());
            }
        }

        @Nested
        @DisplayName("이벤트 참여 조건을 만족할 때")
        class Context_when_event_participation_condition_is_met {

            @Test
            @DisplayName("적절한 EventDetailsDto를 반환한다")
            void it_returns_proper_EventDetailsDto() {
                ReservationDayDto reservationDayDto
                        = ReservationDayDto.from(ReservationDay.of(25, DayOfWeek.MONDAY));
                OrderDto orderDto = OrderDto.from(Order.from("티본스테이크-5"));

                EventDetailsDto result = eventPlanningService.createEventDetails(reservationDayDto, orderDto);

                assertThat(result).isNotNull();
                assertThat(result.getGift()).isNotEqualTo(Gift.empty().toString());
                assertThat(result.getTotalDiscountAmount()).isNotEqualTo(Discounts.empty().getTotalDiscountAmount());
                assertThat(result.getBadge()).isNotEqualTo(BadgeDeterminer.empty().toString());
            }
        }

        @Nested
        @DisplayName("이벤트 참여 조건을 만족하고, 할인이 적용되며, 증정품이 포함될 때")
        class Context_with_event_participation_condition_met_with_discounts_and_gift {

            @Test
            @DisplayName("적절한 EventDetailsDto를 반환한다")
            void it_returns_proper_EventDetailsDto() {
                ReservationDayDto reservationDayDto
                        = ReservationDayDto.from(ReservationDay.of(25, DayOfWeek.MONDAY));
                Map<Menu, Integer> orderItems = new HashMap<>();
                orderItems.put(Menu.T_BONE_STEAK, 3);
                OrderDto orderDto = OrderDto.from(Order.from("티본스테이크-3")); // 이벤트 참여 조건 충족

                EventDetailsDto result = eventPlanningService.createEventDetails(reservationDayDto, orderDto);

                assertThat(result).isNotNull();
                assertThat(result.getGift()).isNotEqualTo(Gift.empty().toString());
                assertThat(result.getTotalDiscountAmount()).isNotEqualTo(Discounts.empty().getTotalDiscountAmount());
                assertThat(result.getBadge()).isNotEqualTo(BadgeDeterminer.empty().toString());
            }
        }

        @Nested
        @DisplayName("이벤트 참여 조건을 만족하지만 할인과 증정품이 적용되지 않을 때")
        class Context_with_event_participation_condition_met_but_no_discounts_and_gift {

            @Test
            @DisplayName("EventDetailsDto에 증정품과 할인이 없음을 확인한다")
            void it_returns_EventDetailsDto_with_no_gifts_and_discounts() {
                ReservationDayDto reservationDayDto
                        = ReservationDayDto.from(ReservationDay.of(29, DayOfWeek.WEDNESDAY));
                Map<Menu, Integer> orderItems = new HashMap<>();
                orderItems.put(Menu.T_BONE_STEAK, 2);
                OrderDto orderDto = OrderDto.from(Order.from("티본스테이크-2")); // 이벤트 참여 조건 충족

                EventDetailsDto result = eventPlanningService.createEventDetails(reservationDayDto, orderDto);

                assertThat(result).isNotNull();
                assertThat(result.getGift()).isEqualTo(Gift.empty().toString());
                assertThat(result.getTotalDiscountAmount()).isEqualTo(Discounts.empty().getTotalDiscountAmount());
                assertThat(result.getBadge()).isEqualTo(BadgeDeterminer.empty().toString());
            }
        }
    }
}