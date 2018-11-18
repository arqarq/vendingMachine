package pl.sda.vending.controller;

import pl.sda.vending.model.Product;
import pl.sda.vending.model.Tray;
import pl.sda.vending.model.VendingMachine;
import pl.sda.vending.service.repository.VendingMachineRepository;
import pl.sda.vending.util.Configuration;
import pl.sda.vending.util.StringUtil;

import java.util.Optional;

public class CustomerOperationController {
    //    private final VendingMachine machine;
    private final VendingMachineRepository machineRepository;
    private final Integer trayWidth; // do properties

    public CustomerOperationController(VendingMachineRepository machineRepository, Configuration configuration) {
//        this.machine = machine;
//        this.trayWidth = machine.trayWidth();
        this.machineRepository = machineRepository;
        trayWidth = configuration.getIntProperty("machine.display.trayWidth", 12);
    }

    public void printMachine() {
        Optional<VendingMachine> loadedMachine = machineRepository.load();
        if (!loadedMachine.isPresent()) {
            System.out.println("Vending Machine out of service");
            return;
        }
        VendingMachine machine = loadedMachine.get();
        for (int rowNo = 0; rowNo < machine.rowsCount(); rowNo++) {
            for (int colNo = 0; colNo < machine.colsCount(); colNo++) {
                printUpperBoundary(machine, rowNo, colNo);
            }
            System.out.println();
            for (int colNo = 0; colNo < machine.colsCount(); colNo++) {
                printSymbol(machine, rowNo, colNo);
            }
            System.out.println();
            for (int colNo = 0; colNo < machine.colsCount(); colNo++) {
                printName(machine, rowNo, colNo);
            }
            System.out.println();
            for (int colNo = 0; colNo < machine.colsCount(); colNo++) {
                printPrice(machine, rowNo, colNo);
            }
            System.out.println();
            for (int colNo = 0; colNo < machine.colsCount(); colNo++) {
                printLowerBoundary(machine, rowNo, colNo);
            }
            System.out.println();
        }
    }

    public Optional<Product> buyProductForSymbol(String traySymbol) {
        Optional<VendingMachine> loadedMachine = machineRepository.load();
        if (loadedMachine.isPresent()) {
            VendingMachine machine = loadedMachine.get();
            Optional<Product> boughtProduct = machine.buyProductWithSymbol(traySymbol);
            machineRepository.save(machine);
            return boughtProduct;
        } else {
            System.out.println("Vending Machine out of service");
            return Optional.empty();
        }
    }

    private void printUpperBoundary(VendingMachine machine, int rowNo, int colNo) {
        System.out.print("+" + StringUtil.duplicateText("-", trayWidth) + "+");
    }

    private void printSymbol(VendingMachine machine, int rowNo, int colNo) {
        Optional<Tray> tray = machine.getTrayAtPosition(rowNo, colNo);
        String traySymbol = tray.map(Tray::getSymbol).orElse("--");
//        char symbolLetter = (char) ('A' + rowNo);
//        int symbolNumber = colNo + 1;
//        System.out.print("|   " + symbolLetter + symbolNumber + "   |");
        System.out.print("|" + StringUtil.adjustText(traySymbol, trayWidth) + "|");
    }

    private void printName(VendingMachine machine, int rowNo, int colNo) {
        Optional<String> productName = machine.productNameAtPosition(rowNo, colNo);
        String formattedName = productName.orElse("--");
        System.out.print("|" + StringUtil.adjustText(formattedName, trayWidth) + "|");
    }

    private void printPrice(VendingMachine machine, int rowNo, int colNo) {
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

    private void printLowerBoundary(VendingMachine machine, int rowNo, int colNo) {
        System.out.print("+" + StringUtil.duplicateText("-", trayWidth) + "+");
//        System.out.print("+--------+");
    }
}
