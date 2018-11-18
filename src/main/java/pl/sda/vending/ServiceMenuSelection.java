package pl.sda.vending;

public enum ServiceMenuSelection {
    ADD_TRAY(1, "Add new tray to machine"),
    REMOVE_TRAY(2, "Remove existing tray"),
    ADD_PRODUCT(3, "Add product(s) to tray"),
    REMOVE_PRODUCT(4, "Remove product(s) from tray"),
    CHANGE_PRICE(5, "Change tray price"),
    EXIT(9, "Exit");

    private final Integer optionNumber;
    private final String optionMessage;

    ServiceMenuSelection(Integer optionNumber, String optionMessage) {
        this.optionNumber = optionNumber;
        this.optionMessage = optionMessage;
    }

    public static ServiceMenuSelection selectionForOptionNumber(Integer requestedOptionNumber) {
        ServiceMenuSelection[] values = values();
        for (int i = 0; i < values.length; i++) {
            if (values[i].getOptionNumber().equals(requestedOptionNumber)) {
                return values[i];
            }
        }
        throw new IllegalArgumentException("Unknown option number: " + requestedOptionNumber);
    }

    public Integer getOptionNumber() {
        return optionNumber;
    }

    public String getOptionMessage() {
        return optionMessage;
    }
}
