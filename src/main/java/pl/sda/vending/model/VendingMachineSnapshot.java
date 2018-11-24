package pl.sda.vending.model;

import java.util.Optional;

public class VendingMachineSnapshot {
    private final TraySnapshot[][] trays;
    private final int rows;
    private final int cols;

    private VendingMachineSnapshot(Builder builder) {
        this.trays = builder.trays;
        this.rows = builder.rows;
        this.cols = builder.cols;
    }

    public static Builder builder(int rows, int cols) {
        return new Builder(rows, cols);
    }

    public Optional<TraySnapshot> getTray(int rowNo, int colNo) {
        return Optional.ofNullable(trays[rowNo][colNo]);
    }

    public int getRowsCount() {
        return rows;
    }

    public int getColsCount() {
        return cols;
    }

    public static class Builder {
        private TraySnapshot[][] trays;
        private int rows;
        private int cols;

        private Builder(int rows, int cols) {
            trays = new TraySnapshot[rows][cols];
            this.rows = rows;
            this.cols = cols;
        }

        public Builder tray(int rowNo, int colNo, TraySnapshot tray) {
            trays[rowNo][colNo] = tray;
            return this;
        }

        public VendingMachineSnapshot build() {
            return new VendingMachineSnapshot(this);
        }
    }
}
