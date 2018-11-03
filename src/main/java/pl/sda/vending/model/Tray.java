package pl.sda.vending.model;

public class Tray {
    private String symbol;

    public Tray(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol + ' ';
    }
}
