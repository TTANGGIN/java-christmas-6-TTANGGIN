package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.dto.request.DayInputDto;
import christmas.dto.request.OrderInputDto;

import java.util.List;

public class InputView {
    private InputView() {
    }

    public static InputView getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final InputView INSTANCE = new InputView();
    }

    public DayInputDto readReservationDay() {
        String dayInput = Console.readLine();
        return DayInputDto.from(dayInput);
    }

    public OrderInputDto readOrder() {
        String orderInput = Console.readLine();
        return OrderInputDto.from(orderInput);
    }
}
