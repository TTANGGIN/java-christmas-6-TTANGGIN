package christmas;

import christmas.controller.ChristmasController;
import christmas.model.EventPlanningService;
import christmas.view.InputView;
import christmas.view.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = InputView.getInstance();
        OutputView outputView = OutputView.getInstance();
        EventPlanningService eventPlanningService = EventPlanningService.getInstance();
        ChristmasController controller = new ChristmasController(inputView, outputView, eventPlanningService);
        controller.run();
    }
}
