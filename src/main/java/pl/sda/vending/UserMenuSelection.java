package pl.sda.vending;

import java.util.Arrays;

public enum UserMenuSelection {
    BUY_PRODUCT(1, "Buy product"),
    EXIT(9, "Exit");

    private final Integer optionNumber;
    private final String optionText;

    UserMenuSelection(Integer optionNumber, String optionText) {
        this.optionNumber = optionNumber;
        this.optionText = optionText;
    }

    public static UserMenuSelection selectionForOptionNumber(Integer requestedOptionNumber) {
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
