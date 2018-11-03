package pl.sda.vending.model;

import pl.sda.vending.util.Configuration;

public class VendingMachine {
    private final Configuration configuration;
    private final Long rowsCount;
    private final Long colsCount;

    public VendingMachine(Configuration configuration) {
        this.configuration = configuration;
        rowsCount = configuration.getLongProperty("machine.size.rows", 6L);
        colsCount = configuration.getLongProperty("machine.size.cols", 4L);
        if (rowsCount <= 0 || colsCount <= 0 || rowsCount > 26 || colsCount > 9) {
            throw new IllegalArgumentException("Row count " + rowsCount + " is invalid");
        }
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
