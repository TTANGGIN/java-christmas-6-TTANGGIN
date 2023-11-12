package christmas.dto.response;

import christmas.model.ReservationDay;

import java.time.DayOfWeek;

public class ReservationDayDto {
    private final int day;
    private final DayOfWeek dayOfWeek;

    private ReservationDayDto(int day, DayOfWeek dayOfWeek) {
        this.day = day;
        this.dayOfWeek = dayOfWeek;
    }

    public static ReservationDayDto from(ReservationDay reservationDay) {
        return new ReservationDayDto(reservationDay.getDay(), reservationDay.getDayOfWeek());
    }

    public int getDay() {
        return day;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }
}