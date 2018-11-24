package pl.sda.vending.controller;

import pl.sda.vending.controller.service.CustomerService;
import pl.sda.vending.model.Product;
import pl.sda.vending.model.TraySnapshot;
import pl.sda.vending.model.VendingMachineSnapshot;
import pl.sda.vending.util.Configuration;
import pl.sda.vending.util.StringUtil;

import java.util.Optional;
import java.util.Scanner;

public class CustomerOperationController {
    //    private final VendingMachine machine;
//    private final VendingMachineRepository machineRepository;
    private final CustomerService customerService;
    private final Integer trayWidth; // do properties

    public CustomerOperationController(CustomerService customerService, Configuration configuration) {
//        this.machine = machine;
//        this.trayWidth = machine.trayWidth();
        this.customerService = customerService;
        trayWidth = configuration.getIntProperty("machine.display.trayWidth", 12);
    }

//    public static String convert(TraySnapshot value) {
//        return value.getProduct();
//    }

    public void printMachine() {
        Optional<VendingMachineSnapshot> loadedMachine = customerService.loadMachineToPrint();
        if (!loadedMachine.isPresent()) {
            System.out.println("Vending Machine out of service");
            return;
        }
        VendingMachineSnapshot machine = loadedMachine.get();
        for (int rowNo = 0; rowNo < machine.getRowsCount(); rowNo++) {
            for (int colNo = 0; colNo < machine.getColsCount(); colNo++) {
                printUpperBoundary(machine, rowNo, colNo);
            }
            System.out.println();
            for (int colNo = 0; colNo < machine.getColsCount(); colNo++) {
                printSymbol(machine, rowNo, colNo);
            }
            System.out.println();
            for (int colNo = 0; colNo < machine.getColsCount(); colNo++) {
                printName(machine, rowNo, colNo);
            }
            System.out.println();
            for (int colNo = 0; colNo < machine.getColsCount(); colNo++) {
                printPrice(machine, rowNo, colNo);
            }
            System.out.println();
            for (int colNo = 0; colNo < machine.getColsCount(); colNo++) {
                printLowerBoundary(machine, rowNo, colNo);
            }
            System.out.println();
        }
    }

    public void buyProduct() {
        System.out.print(" > Please input tray symbol: ");
        String traySymbol = new Scanner(System.in).nextLine();
        Optional<Product> productBought = customerService.buyProductFromTray(traySymbol);
        if (productBought.isPresent()) {
            System.out.println("   Success, you bought: " + productBought.get().getName());
        } else {
            System.out.println("   Out of stock");
        }

/*        Optional<VendingMachine> loadedMachine = machineRepository.load();
        if (loadedMachine.isPresent()) {
            VendingMachine machine = loadedMachine.get();
            Optional<Product> boughtProduct = machine.buyProductWithSymbol(traySymbol);
            machineRepository.save(machine);
            return boughtProduct;
        } else {
            System.out.println("Vending Machine out of service");
            return Optional.empty();
        }*/
    }

    private void printUpperBoundary(VendingMachineSnapshot machine, int rowNo, int colNo) {
        System.out.print("+" + StringUtil.duplicateText("-", trayWidth) + "+");
    }

    private void printSymbol(VendingMachineSnapshot machine, int rowNo, int colNo) {
        Optional<TraySnapshot> tray = machine.getTray(rowNo, colNo);
        String traySymbol = tray.map(TraySnapshot::getSymbol).orElse("--");
//        .map(CustomerOperationController::convert)
//        char symbolLetter = (char) ('A' + rowNo);
//        int symbolNumber = colNo + 1;
//        System.out.print("|   " + symbolLetter + symbolNumber + "   |");
        System.out.print("|" + StringUtil.adjustText(traySymbol, trayWidth) + "|");
    }

    private void printName(VendingMachineSnapshot machine, int rowNo, int colNo) {
        String formattedName = machine.getTray(rowNo, colNo)
                .map(TraySnapshot::getProduct)
                .orElse("--");
        System.out.print("|" + StringUtil.adjustText(formattedName, trayWidth) + "|");
    }

    private void printPrice(VendingMachineSnapshot machine, int rowNo, int colNo) {
        Long trayPrice = machine.getTray(rowNo, colNo)
                .map(TraySnapshot::getPrice)
                .orElse(0L);
//        Optional<Tray> tray = machine.getTrayAtPosition(rowNo, colNo);
//        Long trayPrice = tray.map(Tray::getPrice).orElse(0L);
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

    private void printLowerBoundary(VendingMachineSnapshot machine, int rowNo, int colNo) {
        System.out.print("+" + StringUtil.duplicateText("-", trayWidth) + "+");
//        System.out.print("+--------+");
    }
}
