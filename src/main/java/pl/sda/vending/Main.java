package pl.sda.vending;

import pl.sda.vending.controller.CustomerOperationController;
import pl.sda.vending.model.VendingMachine;
import pl.sda.vending.util.Configuration;

import java.util.Scanner;

public class Main {
    private Configuration configuration = new Configuration();
    private VendingMachine vendingMachine = new VendingMachine(configuration);
    private CustomerOperationController customerOperationController = new CustomerOperationController(vendingMachine);

    public static void main(String[] args) {
        new Main().startApplication();
    }

    private void startApplication() {
        while (true) {
            customerOperationController.printMachine();
            printMenu();
            UserMenuSelection userSelection = getUserSelection();
            switch (userSelection) {
                case BUY_PRODUCT:
                    ///
                    break;
                case EXIT:
                    System.out.println("Bye");
                    return;
                default:
                    System.out.println("Invalid selection");
            }
        }
    }

    private UserMenuSelection getUserSelection() {
        String userSelection = new Scanner(System.in).nextLine();
        try {
            Integer menuNumber = Integer.valueOf(userSelection);

        } catch (NumberFormatException e) {
            return null;
        }
        return null; ///
    }

    private void printMenu() {
        UserMenuSelection[] allPossibleSelections = UserMenuSelection.values();
        for (UserMenuSelection menuPosistion : allPossibleSelections) {
            System.out.println(menuPosistion.getOptionNumber() + ". " + menuPosistion.getOptionText());
        }
    }
}
