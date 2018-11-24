package pl.sda.vending.controller;

import pl.sda.vending.controller.service.EmployeeService;

import java.util.Optional;
import java.util.Scanner;

public class EmployeeOperationController {
    private final EmployeeService employeeService;

    public EmployeeOperationController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void addTray() {
        String traySymbol = getTraySymbolFromUser();
        Long trayPrice = getTrayPriceFromUser();
//        Tray newTray = Tray.builder(traySymbol)
//                .price(trayPrice)
//                .build();
//        Optional<String> errorMessage = employeeService.addTray(newTray);
        Optional<String> errorMessage = employeeService.addTray(traySymbol, trayPrice);
        System.out.println(errorMessage.orElse("   Tray has been added."));
        // ask for tray symbol
        // ask for tray price
        // build new tray
        // delegate tray save to service
        // Print confirmation or error
    }

    public void removeTray() {
        // zapytac uzytkownika o symbol tacki do usuniecia
        String traySymbol = getTraySymbolFromUser();
        // wykonac odpowiednia metode w serwisie
        Optional<String> errorMessage = employeeService.removeTrayWithSymbol(traySymbol);
        // wyswietlamy blad lub potwierdzenie operacji
        System.out.println(errorMessage.orElse("   Tray has been removed."));
    }

    private Long getTrayPriceFromUser() {
        Long price = null;
        while (price == null) {
            System.out.print(" > Provide tray price: ");
            try {
                price = Long.parseLong(getUserInput());
            } catch (NumberFormatException e) {
                System.out.println("   Invalid price. Try again.");
            }
        }
        return price;
    }

    private String getTraySymbolFromUser() {
        System.out.print(" > Provide tray symbol: ");
        return getUserInput().toUpperCase();
    }

    private String getProductNameFromUser() {
        System.out.print(" > Provide product name: ");
        return getUserInput();
    }

    private Integer getHowManyTo() {
        Integer quantity = null;

        while (quantity == null) {
            System.out.print(" > Provide products quantity: ");
            try {
                quantity = Integer.parseInt(getUserInput());
            } catch (NumberFormatException e) {
                System.out.println("   Invalid products quantity. Try again.");
            }
        }
        return quantity;
    }

    private String getUserInput() {
        return new Scanner(System.in).nextLine();
    }

    public void addProducts() {
        String traySymbol = getTraySymbolFromUser();
        String productName = getProductNameFromUser();
        Integer quantity = getHowManyTo();
        Optional<String> errorMessage = employeeService.addProduct(traySymbol, productName, quantity);
        System.out.println(errorMessage.orElse("   All products have been added."));
    }

    public void changePrice() {
        Optional<String> errorMessage;

        String traySymbol = getCorrectTraySymbolFromUser();
        Long newPrice = getTrayPriceFromUser();
        errorMessage = employeeService.changePrice(traySymbol, newPrice);
        System.out.println(errorMessage.orElse("   Tray price updated."));
    }

    private String getCorrectTraySymbolFromUser() {
        String traySymbol;
        Optional<String> errorMessage;

        while (true) {
            traySymbol = getTraySymbolFromUser();
            errorMessage = employeeService.checkTrayAvailability(traySymbol);
            if (!errorMessage.isPresent()) {
                break;
            } else {
                System.out.println(errorMessage.get());
            }
        }
        return traySymbol;
    }

    public void removeProducts() {
        String traySymbol = getCorrectTraySymbolFromUser();
//        String productNameToRemove = getProductNameFromUser();
        Integer howManyToRemove = getHowManyTo();
        Optional<String> errorMessage = employeeService.removeProduct(traySymbol, /*productNameToRemove,*/ howManyToRemove);
        System.out.println(errorMessage.orElse("   " + howManyToRemove + " product(s)" +
                /*+ productNameToRemove + */" successfully removed from tray."));
    }
}
