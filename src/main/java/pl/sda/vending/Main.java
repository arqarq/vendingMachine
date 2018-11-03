package pl.sda.vending;

import pl.sda.vending.controller.CustomerOperationController;
import pl.sda.vending.model.VendingMachine;
import pl.sda.vending.util.Configuration;

public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        VendingMachine vendingMachine = new VendingMachine(configuration);
//        new CustomerOperationController().printMachine();
        CustomerOperationController customerOperationController = new CustomerOperationController(vendingMachine);
        customerOperationController.printMachine();


    }
}
