package pl.sda.vending;

import pl.sda.vending.controller.CustomerOperationController;

public class Main {
    public static void main(String[] args) {
        new CustomerOperationController().printMachine();
    }
}
