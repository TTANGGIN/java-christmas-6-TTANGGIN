package christmas.model;

import christmas.config.DiscountType;
import christmas.config.Menu;
import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;

class GiftTest {

    @Nested
    @DisplayName("of 메소드는")
    class Describe_of {

        @Nested
        @DisplayName("유효한 메뉴 아이템과 수량이 주어졌을 때")
        class Context_with_valid_menu_item_and_quantity {

            @Test
            @DisplayName("해당 아이템과 수량으로 Gift 객체를 생성한다")
            void it_creates_gift_object_with_given_item_and_quantity() {
                // 준비
                Menu item = Menu.CHAMPAGNE;
                int quantity = 1;

                // 실행
                Gift gift = Gift.of(item, quantity);

                // 검증
                assertThat(gift).isNotNull();
                assertThat(gift.getDiscountType()).isEqualTo(DiscountType.GIFT_EVENT);
                assertThat(gift.getDiscountAmount()).isEqualTo(item.getPrice() * quantity);
                assertThat(gift.toString()).isEqualTo(item.getName() + " " + quantity + "개");
            }
        }
    }

    @Nested
    @DisplayName("copyOf 메소드는")
    class Describe_copyOf {

        @Nested
        @DisplayName("유효한 Gift 객체가 주어졌을 때")
        class Context_with_valid_gift {

            @Test
            @DisplayName("동일한 아이템과 수량을 가진 새로운 Gift 객체를 생성한다")
            void it_creates_a_new_gift_object_with_same_item_and_quantity() {
                // 준비
                Gift original = Gift.of(Menu.CHAMPAGNE, 1);

                // 실행
                Gift copy = Gift.copyOf(original);

                // 검증
                assertThat(copy).isNotSameAs(original);
                assertThat(copy.getDiscountType()).isEqualTo(original.getDiscountType());
                assertThat(copy.getDiscountAmount()).isEqualTo(original.getDiscountAmount());
                assertThat(copy.toString()).isEqualTo(original.toString());
            }
        }
    }

    @Nested
    @DisplayName("empty 메소드는")
    class Describe_empty {

        @Test
        @DisplayName("빈 Gift 객체를 생성한다")
        void it_creates_an_empty_gift_object() {
            // 실행
            Gift emptyGift = Gift.empty();

            // 검증
            assertThat(emptyGift.isEmpty()).isTrue();
            assertThat(emptyGift.getDiscountAmount()).isEqualTo(0);
            assertThat(emptyGift.toString()).isEqualTo("없음");
        }
    }

    @Nested
    @DisplayName("isEmpty 메소드는")
    class Describe_isEmpty {

        @Nested
        @DisplayName("빈 Gift 객체가 주어졌을 때")
        class Context_with_empty_gift {

            @Test
            @DisplayName("true를 반환한다")
            void it_returns_true() {
                // 준비
                Gift emptyGift = Gift.empty();

                // 검증
                assertThat(emptyGift.isEmpty()).isTrue();
            }
        }

        @Nested
        @DisplayName("유효한 Gift 객체가 주어졌을 때")
        class Context_with_valid_gift {

            @Test
            @DisplayName("false를 반환한다")
            void it_returns_false() {
                // 준비
                Gift validGift = Gift.of(Menu.CHAMPAGNE, 1);

                // 검증
                assertThat(validGift.isEmpty()).isFalse();
            }
        }
    }
}