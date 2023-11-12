package christmas.controller;

import christmas.dto.request.DayInputDto;
import christmas.dto.request.OrderInputDto;
import christmas.dto.response.EventDetailsDto;
import christmas.dto.response.OrderDto;
import christmas.dto.response.ReservationDayDto;
import christmas.model.EventPlanningService;
import christmas.view.InputView;
import christmas.view.OutputView;

import java.util.function.Supplier;

public class ChristmasController {
    private final InputView inputView;
    private final OutputView outputView;
    private final EventPlanningService eventPlanningService;

    public ChristmasController(InputView inputView, OutputView outputView, EventPlanningService eventPlanningService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.eventPlanningService = eventPlanningService;
    }

    public void run() {
        outputView.printWelcomeMessage();

        ReservationDayDto reservationDayDto = retryOnException(this::processReservationDayInput);
        OrderDto orderDto = retryOnException(this::processOrderInput);
        EventDetailsDto eventDetailsDto = processEventDetails(reservationDayDto, orderDto);

        outputView.printEventBenefitsMessage(reservationDayDto.getDay());
        displayEventDetails(orderDto, eventDetailsDto);
    }

    private void displayEventDetails(OrderDto orderDto, EventDetailsDto eventDetailsDto) {
        // 주문 메뉴
        outputView.printOrderDetails(orderDto.getOrderDetails());
        // 할인 전 총주문 금액
        outputView.printTotalAmountBeforeDiscount(orderDto.getTotalAmount());
        // 증정 메뉴
        outputView.printGift(eventDetailsDto.getGift());
        // 혜택 내역
        outputView.printEventDetails(eventDetailsDto.getEventDetails());
        // 총혜택 금액
        outputView.printTotalDiscount(eventDetailsDto.getTotalDiscountAmount());
        // 할인 후 예상 결제 금액
        outputView.printTotalAmountAfterDiscount(orderDto.getTotalAmount() - eventDetailsDto.getTotalDiscountAmount());
        // 12월 이벤트 배지
        outputView.printBadge(eventDetailsDto.getBadge());
    }

    private DayInputDto getReservationDayFromView() {
        outputView.printReservationMessage();
        return inputView.readReservationDay();
    }

    private OrderInputDto getOrderFromView() {
        outputView.printOrderMessage();
        return inputView.readOrder();
    }

    private ReservationDayDto processReservationDayInput() {
        DayInputDto dayInputDto = getReservationDayFromView();
        return eventPlanningService.createValidReservationDay(dayInputDto);
    }

    private OrderDto processOrderInput() {
        OrderInputDto orderInputDto = getOrderFromView();
        return eventPlanningService.createValidOrder(orderInputDto);
    }

    private EventDetailsDto processEventDetails(ReservationDayDto reservationDayDto, OrderDto orderDto) {
        return eventPlanningService.createEventDetails(reservationDayDto, orderDto);
    }

    private <T> T retryOnException(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException e) {
            outputView.printExceptionMessage(e);
            return retryOnException(supplier);
        }
    }
}
