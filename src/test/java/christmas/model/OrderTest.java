package christmas.model;

import christmas.config.ErrorMessage;
import christmas.config.Menu;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;
import java.util.Map;
import java.util.HashMap;

class OrderTest {

    @Nested
    @DisplayName("from 메소드는")
    class Describe_from {

        @Nested
        @DisplayName("유효한 주문 문자열이 주어졌을 때")
        class Context_with_valid_order_string {

            private final String validOrderString = "양송이수프-2,티본스테이크-1";

            @Test
            @DisplayName("Order 객체를 반환한다")
            void it_returns_order_object() {
                // 준비
                // 실행
                Order order = Order.from(validOrderString);

                // 검증
                assertThat(order).isNotNull();
                assertThat(order.getOrderItems()).hasSize(2)
                        .containsEntry(Menu.MUSHROOM_SOUP, 2)
                        .containsEntry(Menu.T_BONE_STEAK, 1);
            }
        }

        @Nested
        @DisplayName("유효하지 않은 주문 문자열이 주어졌을 때")
        class Context_with_invalid_order_string {

            private final String invalidOrderString = "양송이수프-2,없는메뉴-1";

            @Test
            @DisplayName("IllegalArgumentException을 발생시킨다")
            void it_throws_illegalArgumentException() {
                // 준비
                // 실행 및 검증
                assertThatThrownBy(() -> Order.from(invalidOrderString))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining(ErrorMessage.INVALID_ORDER_INPUT.getMessage());
            }
        }

        @Nested
        @DisplayName("메뉴 수량이 주문 가능 수량을 범위를 벗어난 경우")
        class Context_with_invalid_quantity {

            private final String orderInputWithTooFewQuantity = "양송이수프-0";
            private final String orderInputWithTooManyQuantity = "양송이수프-21";
            private final String orderInputWithTooManyQuantityVariousMenu
                    = "시저샐러드-5,티본스테이크-5,크리스마스파스타-5,제로콜라-5,아이스크림-1";

            @ParameterizedTest
            @ValueSource(strings = {orderInputWithTooFewQuantity, orderInputWithTooManyQuantity
                    , orderInputWithTooManyQuantityVariousMenu})
            @DisplayName("IllegalArgumentException을 발생시킨다")
            void it_throws_illegalArgumentException(String orderInput) {
                // 준비
                // 실행 및 검증
                assertThatThrownBy(() -> Order.from(orderInput))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining(ErrorMessage.INVALID_ORDER_INPUT.getMessage());
            }
        }

        @Nested
        @DisplayName("주문 문자열이 비어있는 경우")
        class Context_with_empty_order_string {

            private final String emptyOrderString = "";

            @Test
            @DisplayName("IllegalArgumentException을 발생시킨다")
            void it_throws_illegalArgumentException() {
                // 준비
                // 실행 및 검증
                assertThatThrownBy(() -> Order.from(emptyOrderString))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining(ErrorMessage.INVALID_ORDER_INPUT.getMessage());
            }
        }

        @Nested
        @DisplayName("메뉴판에 없는 메뉴를 입력한 경우")
        class Context_with_nonexistent_menu {

            private final String orderInputWithNonexistentMenu = "없는메뉴-1,티본스테이크-1";

            @Test
            @DisplayName("IllegalArgumentException을 발생시킨다")
            void it_throws_illegalArgumentException() {
                // 준비
                // 실행 및 검증
                assertThatThrownBy(() -> Order.from(orderInputWithNonexistentMenu))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining(ErrorMessage.INVALID_ORDER_INPUT.getMessage());
            }
        }

        @Nested
        @DisplayName("지정되지 않은 형식으로 주문한 경우")
        class Context_with_invalid_format {

            private final String invalidFormatOrderInput = "양송이수프@2,티본스테이크#1";

            @Test
            @DisplayName("IllegalArgumentException을 발생시킨다")
            void it_throws_illegalArgumentException() {
                // 준비
                // 실행 및 검증
                assertThatThrownBy(() -> Order.from(invalidFormatOrderInput))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining(ErrorMessage.INVALID_ORDER_INPUT.getMessage());
            }
        }

        @Nested
        @DisplayName("동일한 메뉴를 중복하여 주문한 경우")
        class Context_with_duplicate_menu {

            private final String duplicateMenuOrderInput = "양송이수프-2,양송이수프-1";

            @Test
            @DisplayName("IllegalArgumentException을 발생시킨다")
            void it_throws_illegalArgumentException() {
                // 준비
                // 실행 및 검증
                assertThatThrownBy(() -> Order.from(duplicateMenuOrderInput))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining(ErrorMessage.INVALID_ORDER_INPUT.getMessage());
            }
        }

        @Nested
        @DisplayName("음료만 주문한 경우")
        class Context_with_only_beverages {

            private final String beveragesOnlyOrderInput = "제로콜라-2,레드와인-1";

            @Test
            @DisplayName("IllegalArgumentException을 발생시킨다")
            void it_throws_illegalArgumentException() {
                // 준비
                // 실행 및 검증
                assertThatThrownBy(() -> Order.from(beveragesOnlyOrderInput))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining(ErrorMessage.INVALID_ORDER_INPUT.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("getTotalAmount 메소드는")
    class Describe_getTotalAmount {

        @Test
        @DisplayName("주문의 총 금액을 계산하여 반환한다")
        void it_returns_the_total_amount_of_the_order() {
            // 준비
            Map<Menu, Integer> orderItems = new HashMap<>();
            orderItems.put(Menu.MUSHROOM_SOUP, 2);
            orderItems.put(Menu.T_BONE_STEAK, 1);
            Order order = Order.from(orderItems);

            // 실행
            int totalAmount = order.getTotalAmount();

            // 검증
            assertThat(totalAmount).isEqualTo(
                    Menu.MUSHROOM_SOUP.getPrice() * 2 + Menu.T_BONE_STEAK.getPrice() * 1
            );
        }
    }
}