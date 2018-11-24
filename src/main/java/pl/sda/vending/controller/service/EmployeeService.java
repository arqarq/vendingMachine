package pl.sda.vending.controller.service;

import java.util.Optional;

public interface EmployeeService {
    Optional<String> addTray(String traySymbol, Long price);

    Optional<String> removeTrayWithSymbol(String traySymbol);

    Optional<String> addProduct(String traySymbol, String productName, Integer howManyToAdd);

    Optional<String> removeProduct(String traySymbol, /*String productName,*/ Integer howManyToRemove);

    Optional<String> changePrice(String traySymbol, Long updatedPrice);

    Optional<String> checkTrayAvailability(String traySymbol);
}
