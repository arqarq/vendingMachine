package pl.sda.vending;

public enum UserMenuSelection {
    BUY_PRODUCT(1, "Buy product"),
    EXIT(9, "Exit");

    private final Integer optionNumber;
    private final String optionText;

    UserMenuSelection(Integer optionNumber, String optionText) {
        this.optionNumber = optionNumber;
        this.optionText = optionText;
    }

    public Integer getOptionNumber() {
        return optionNumber;
    }

    public String getOptionText() {
        return optionText;
    }
}
