package christmas.model;

import christmas.config.ErrorMessage;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class ReservationDay {

    private final int day;
    private final DayOfWeek dayOfWeek;

    private ReservationDay(LocalDate localDate) {
        this.day = localDate.getDayOfMonth();
        this.dayOfWeek = localDate.getDayOfWeek();
    }

    private ReservationDay(int day, DayOfWeek dayOfWeek) {
        this.day = day;
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * 사용자 입력을 기반으로 ReservationDay 객체 생성
     * 입력된 날짜가 유효하지 않으면 예외를 발생시킨다.
     *
     * @param year 입력된 년도
     * @param month 입력된 월
     * @param day 입력된 일
     * @return ReservationDay 객체
     */
    public static ReservationDay of(int year, int month, int day) {
        LocalDate localDate = createValidatedLocalDate(year, month, day);
        return new ReservationDay(localDate);
    }

    /**
     * 이미 검증된 날짜 정보를 바탕으로 ReservationDay 객체 생성
     *
     * @param day 예약일
     * @param dayOfWeek 예약 요일
     * @return ReservationDay 객체
     */
    public static ReservationDay of(int day, DayOfWeek dayOfWeek) {
        return new ReservationDay(day, dayOfWeek);
    }

    private static LocalDate createValidatedLocalDate(int year, int month, int day) {
        try {
            return LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_DAY_INPUT.getMessage());
        }
    }

    public int getDay() {
        return day;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }
}

