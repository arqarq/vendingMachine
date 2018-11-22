package pl.sda.vending.controller;

import pl.sda.vending.controller.service.EmployeeService;
import pl.sda.vending.model.Tray;

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
        Tray newTray = Tray.builder(traySymbol)
                .price(trayPrice)
                .build();
        Optional<String> errorMessage = employeeService.addTray(newTray);
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

    private Integer getHowManyToAdd() {
        Integer quantity = null;
        while (quantity == null) {
            System.out.print(" > Provide product quantity: ");
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
        Integer quantity = getHowManyToAdd();
        Optional<String> errorMessage = employeeService.addProduct(traySymbol, productName, quantity);
        System.out.println(errorMessage.orElse("   All products have been added."));
    }

    public void changePrice() {
        String traySymbol = getTraySymbolFromUser();
        Long newPrice = getTrayPriceFromUser();
        Optional<String> errorMessage = employeeService.changePrice(traySymbol, newPrice);
        System.out.println(errorMessage.orElse("   Tray price updated."));
    }

    public void removeProducts() {
        String traySymbol = getTraySymbolFromUser();
        String productNameToRemove = getProductNameFromUser();
        Integer howManyToRemove = getHowManyToAdd();
        Optional<String> errorMessage = employeeService.removeProduct(traySymbol, productNameToRemove, howManyToRemove);
        System.out.println(errorMessage.orElse("   " + howManyToRemove + " product(s) with name: "
                + productNameToRemove + " successfully removed from tray " + traySymbol + "."));
    }
}
