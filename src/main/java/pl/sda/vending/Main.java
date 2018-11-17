package pl.sda.vending;

import pl.sda.vending.controller.CustomerOperationController;
import pl.sda.vending.model.Product;
import pl.sda.vending.model.VendingMachine;
import pl.sda.vending.util.Configuration;

import java.util.Optional;
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
            try {
                UserMenuSelection userSelection = getUserSelection();
                switch (userSelection) {
                    case BUY_PRODUCT:
                        System.out.print(" > Please, input tray symbol: ");
                        String traySymbol = new Scanner(System.in).nextLine().toUpperCase();
                        Optional<Product> productBought = customerOperationController.buyProductForSymbol(traySymbol);
                        if (productBought.isPresent()) {
                            System.out.println("   Success, you bought: " + productBought.get().getName());
                        } else {
                            System.out.println("   Product N/A");
                        }
                        System.out.println();
                        // 1. pobrac od uzytkownika symbol tacki
                        // 2. wywolac odpowiednia metode z kontrolera
                        //    Optional buyProductForSymbol(String traySymbol)
                        // 3. Jezeli udalo sie kupic produkt
                        //    To wypisz na ekran potwierdzenie oraz nazwe produktu
                        // 4. Jezeli nie udalo sie kupic, to wyswietlamy komunikat o braku produktu
                        break;
                    case EXIT:
                        System.out.println("Bye");
                        return;
                    default:
                        System.out.println("Invalid selection");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private UserMenuSelection getUserSelection() {
        System.out.print(" > Your selection: ");
        String userSelection = new Scanner(System.in).nextLine();
        try {
            Integer menuNumber = Integer.valueOf(userSelection);
            return UserMenuSelection.selectionForOptionNumber(menuNumber);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid selection format");
        }
    }

    private void printMenu() {
        UserMenuSelection[] allPossibleSelections = UserMenuSelection.values();
        for (UserMenuSelection menuPosistion : allPossibleSelections) {
            System.out.println(menuPosistion.getOptionNumber() + ". " + menuPosistion.getOptionText());
        }
    }
}
