package pl.sda.vending;

import java.util.Arrays;

public enum UserMenuSelection {
    BUY_PRODUCT(1, "Buy product"),
    EXIT(9, "Exit"),
    SERVICE_MENU(0, "Service menu");

    private final Integer optionNumber;
    private final String optionText;

    UserMenuSelection(Integer optionNumber, String optionText) {
        this.optionNumber = optionNumber;
        this.optionText = optionText;
    }

    public static UserMenuSelection selectionForOptionNumber(Integer requestedOptionNumber) {
//        UserMenuSelection[] values = values();
//        for (UserMenuSelection value : values) {
//            if (value.getOptionNumber().equals(requestedOptionNumber)) {
//                return value;
//            }
//        }
//        throw new IllegalArgumentException("Unknown option number: " + requestedOptionNumber);
        return Arrays.stream(values())
                .filter(x -> x.getOptionNumber().equals(requestedOptionNumber))
                .findFirst()
//                .orElseGet(() -> {
//                    throw new IllegalArgumentException();
//                });
                .orElseThrow(() -> new IllegalArgumentException("Unknown option number: " + requestedOptionNumber));
    }

    public Integer getOptionNumber() {
        return optionNumber;
    }

    public String getOptionText() {
        return optionText;
    }
}
