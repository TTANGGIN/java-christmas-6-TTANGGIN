package christmas.model;

import christmas.config.EventBadge;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

class BadgeDeterminerTest {

    @Nested
    @DisplayName("from 메소드는")
    class Describe_from {

        @Nested
        @DisplayName("할인 금액에 따라")
        class Context_with_various_discount_amounts {

            @ParameterizedTest
            @CsvSource({
                    "200000, 산타",
                    "20000, 산타",
                    "19999, 트리",
                    "10000, 트리",
                    "9999, 별",
                    "5000, 별",
                    "4999, 없음",
                    "0, 없음"
            })
            @DisplayName("적절한 이벤트 배지를 반환한다")
            void it_returns_correct_event_badge(int totalDiscountAmount, String expectedBadge) {
                // 준비
                // 실행
                BadgeDeterminer badgeDeterminer = BadgeDeterminer.from(totalDiscountAmount);

                // 검증
                assertThat(badgeDeterminer.toString()).isEqualTo(expectedBadge);
            }
        }

        @Nested
        @DisplayName("할인 금액이 음수인 경우")
        class Context_with_negative_discount_amount {

            @Test
            @DisplayName("없음 배지를 반환한다")
            void it_returns_none_badge() {
                // 준비
                int negativeDiscountAmount = -1000;

                // 실행
                BadgeDeterminer badgeDeterminer = BadgeDeterminer.from(negativeDiscountAmount);

                // 검증
                assertThat(badgeDeterminer.toString()).isEqualTo(EventBadge.NONE.getBadge());
            }
        }
    }

    @Nested
    @DisplayName("empty 메소드는")
    class Describe_empty {

        @Test
        @DisplayName("없음 배지를 가진 BadgeDeterminer를 반환한다")
        void it_returns_badge_determiner_with_none_badge() {
            // 준비
            // 실행
            BadgeDeterminer badgeDeterminer = BadgeDeterminer.empty();

            // 검증
            assertThat(badgeDeterminer.toString()).isEqualTo(EventBadge.NONE.getBadge());
        }
    }
}