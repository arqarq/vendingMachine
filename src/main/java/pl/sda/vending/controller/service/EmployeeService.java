package pl.sda.vending.controller.service;

import pl.sda.vending.model.Tray;

import java.util.Optional;

public interface EmployeeService {
    Optional<String> addTray(Tray tray);

    Optional<String> removeTrayWithSymbol(String traySymbol);

    Optional<String> addProduct(String traySymbol, String productName, Integer howManyToAdd);
}
