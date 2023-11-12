package christmas.dto.request;

public class DayInputDto {
    private final String dayInput;

    private DayInputDto(String dayInput) {
        this.dayInput = dayInput;
    }

    public static DayInputDto from(String dayInput) {
        return new DayInputDto(dayInput);
    }

    public String getDayInput() {
        return dayInput;
    }
}
