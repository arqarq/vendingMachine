package pl.sda.vending.controller;

import pl.sda.vending.model.VendingMachine;

public class CustomerOperationController {
    private VendingMachine machine;

    public CustomerOperationController() {
        machine = new VendingMachine();
    }

    public void printMachine() {
        for (int rowNo = 0; rowNo < machine.rowsCount(); rowNo++) {
            for (int colNo = 0; colNo < machine.colsCount(); colNo++) {
                printUpperBoundary(rowNo, colNo);
            }
            System.out.println();
            for (int colNo = 0; colNo < machine.colsCount(); colNo++) {
                printSymbol(rowNo, colNo);
            }
            System.out.println();
            for (int colNo = 0; colNo < machine.colsCount(); colNo++) {
                printLowerBoundary(rowNo, colNo);
            }
            System.out.println();
        }
    }

    private void printUpperBoundary(int rowNo, int colNo) {
        System.out.print("+--------+");
    }

    private void printSymbol(int rowNo, int colNo) {
        char symbolLetter = (char) ('A' + rowNo);
        int symbolNumber = colNo + 1;
        System.out.print("|   " + symbolLetter + symbolNumber + "   |");    }

    private void printLowerBoundary(int rowNo, int colNo) {
        System.out.print("+--------+");
    }
}
