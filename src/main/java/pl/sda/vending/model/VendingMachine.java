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
        if (rowsCount <= 0 || rowsCount > 26) {
            throw new IllegalArgumentException("Row count " + rowsCount + " is invalid");
        }
        if (colsCount <= 0 || colsCount > 9) {
            throw new IllegalArgumentException("Col count " + colsCount + " is invalid");
        }
        // stworz tablice 2-wym.
        // do kazdeo pola tablicy wpisac nowy obiekt tacki
        // obiekt tacki musi miec ustawiony poprawny symbol

        trays = new Tray[rowsCount.intValue()][colsCount.intValue()];

        /*        char letter = 'A';
        for (Tray[] tray : trays) {
            for (int i = 0; i < tray.length; i++) {
//                tray[i] = new Tray(letter + String.valueOf(i + 1));
                String symbol = letter + String.valueOf(i);
                tray[i] = Tray.builder(symbol).build();
            }
            letter++;
        }*/

        for (int rowNo = 0; rowNo < rowsCount; rowNo++) {
            for (int colNo = 0; colNo < colsCount; colNo++) {
                char letter = (char) ('A' + rowNo);
                int number = colNo + 1;
                String symbol = "" + letter + number;
                Tray tray = Tray.builder(symbol).build();
                trays[rowNo][colNo] = tray;
            }
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

/*        try {
            Tray tray = trays[rowNo][colNo];
            return Optional.ofNullable(tray);
        } catch (ArrayIndexOutOfBoundsException e) {
            return Optional.empty();
        }*/
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
