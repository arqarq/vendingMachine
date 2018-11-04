package pl.sda.vending.controller;

import pl.sda.vending.model.Product;
import pl.sda.vending.model.Tray;
import pl.sda.vending.model.VendingMachine;
import pl.sda.vending.util.StringUtil;

import java.util.Optional;

public class CustomerOperationController {
    private final VendingMachine machine;
    private final Integer trayWidth; // do properties

    public CustomerOperationController(VendingMachine machine) {
        this.machine = machine;
        this.trayWidth = machine.trayWidth();
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
                printName(rowNo, colNo);
            }
            System.out.println();
            for (int colNo = 0; colNo < machine.colsCount(); colNo++) {
                printPrice(rowNo, colNo);
            }
            System.out.println();
            for (int colNo = 0; colNo < machine.colsCount(); colNo++) {
                printLowerBoundary(rowNo, colNo);
            }
            System.out.println();
        }
    }

    public Optional<Product> buyProductForSymbol(String traySymbol) {
        return machine.buyProductWithSymbol(traySymbol);
    }

    private void printUpperBoundary(int rowNo, int colNo) {
        System.out.print("+" + StringUtil.duplicateText("-", trayWidth) + "+");
    }

    private void printSymbol(int rowNo, int colNo) {
        Optional<Tray> tray = machine.getTrayAtPosition(rowNo, colNo);
        String traySymbol = tray.map(Tray::getSymbol).orElse("--");
//        char symbolLetter = (char) ('A' + rowNo);
//        int symbolNumber = colNo + 1;
//        System.out.print("|   " + symbolLetter + symbolNumber + "   |");
        System.out.print("|" + StringUtil.adjustText(traySymbol, trayWidth) + "|");
    }

    private void printName(int rowNo, int colNo) {
        Optional<String> productName = machine.productNameAtPosition(rowNo, colNo);
        String formattedName = productName.orElse("--");
        System.out.print("|" + StringUtil.adjustText(formattedName, trayWidth) + "|");
    }

    private void printPrice(int rowNo, int colNo) {
        Optional<Tray> tray = machine.getTrayAtPosition(rowNo, colNo);
        Long trayPrice = tray.map(Tray::getPrice).orElse(0L);
        String formattedMoney = StringUtil.formatMoney(trayPrice);
//        char symbolLetter = (char) ('A' + rowNo);
//        int symbolNumber = colNo + 1;
//        System.out.print("|   " + symbolLetter + symbolNumber + "   |");
        System.out.print("|" + StringUtil.adjustText(formattedMoney, trayWidth) + "|");
    }

/*  private void printProductNameAtPosition_(int rowNo, int colNo) {
        Optional<Tray> tray = machine.getTrayAtPosition(rowNo, colNo);
//        String trayProduct = tray.map(Tray::firstProductName).map(other -> other.orElse("")); orElse("--")
//        char symbolLetter = (char) ('A' + rowNo);
//        int symbolNumber = colNo + 1;
//        System.out.print("|   " + symbolLetter + symbolNumber + "   |");
        System.out.print("|" + StringUtil.adjustText(trayProduct, trayWidth) + "|");
    }*/

    private void printLowerBoundary(int rowNo, int colNo) {
        System.out.print("+" + StringUtil.duplicateText("-", trayWidth) + "+");
//        System.out.print("+--------+");
    }
}
