package pl.sda.vending.controller.service;

import java.util.Optional;

public interface EmployeeService {
    Optional<String> addTray(String traySymbol, Long price);
    // addTray(TraySnapshot traySnapshot)
    // serwis konwertuje snapshot na tray do zapisania w automacie
    // VO niezmienne w czasie, do komunikacji w obie strony można używać (wtedy już nie snapshot)
    // DTO zmienne w czasie i problem dla dwóch klientów gdy dostaną DTO - mógł się zmienić

    Optional<String> removeTrayWithSymbol(String traySymbol);

    Optional<String> addProduct(String traySymbol, String productName, Integer howManyToAdd);

    Optional<String> removeProduct(String traySymbol, /*String productName,*/ Integer howManyToRemove);

    Optional<String> changePrice(String traySymbol, Long updatedPrice);

    Optional<String> checkTrayAvailability(String traySymbol);
}
