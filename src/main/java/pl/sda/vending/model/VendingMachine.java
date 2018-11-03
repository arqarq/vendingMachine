package pl.sda.vending.model;

import pl.sda.vending.util.Configuration;

import java.util.Optional;

public class VendingMachine {
    private final Configuration configuration;
    private final Long rowsCount;
    private final Long colsCount;
    private final Tray[][] trays;

    public VendingMachine(Configuration configuration) {
        this.configuration = configuration;
        rowsCount = configuration.getLongProperty("machine.size.rows", 6L);
        colsCount = configuration.getLongProperty("machine.size.cols", 4L);
        if (rowsCount <= 0 || colsCount <= 0 || rowsCount > 26 || colsCount > 9) {
            throw new IllegalArgumentException("Row count " + rowsCount + " is invalid");
        }
        // stworz tablice 2-wym.
        // do kazdeo pola tablicy wpisac nowy obiekt tacki
        // obiekt tacki musi miec ustawiony poprawny symbol
        trays = new Tray[rowsCount.intValue()][colsCount.intValue()];
        char letter = 'A';
        for (Tray[] tray : trays) {
            for (int i = 0; i < tray.length; i++) {
//                tray[i] = new Tray(letter + String.valueOf(i + 1));
                String symbol = letter + String.valueOf(i);
                tray[i] = Tray.builder(symbol).build();
            }
            letter++;
        }
    }

    public Optional<Tray> getTrayAtPosition(int rowNo, int colNo) {
        // zwroc tacke opakowana w Optional
        // a jezel nie istnieje, to pusty optional
        Tray tray;
        try {
            tray = trays[rowNo][colNo];
        } catch (ArrayIndexOutOfBoundsException e) { // tablice to wyrzucaja; kolekcje wyrzucacj index out of bound
//            throw new IndexOutOfBoundsException("nie pasuja indeksy"); // ale to przerwie
            return Optional.empty();
        }
        return tray == null ? Optional.empty() : Optional.of(tray);
    }

    public Long rowsCount() {
//        return configuration.getLongProperty("machine.size.rows", 6L);
        return rowsCount;
    }

    public Long colsCount() {
//        return configuration.getLongProperty("machine.size.cols", 4L);
        return colsCount;
    }
}
