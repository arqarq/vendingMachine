package pl.sda.vending.controller.service;

import pl.sda.vending.model.Product;
import pl.sda.vending.model.VendingMachineSnapshot;

import java.util.Optional;

public interface CustomerService {
    Optional<VendingMachineSnapshot> loadMachineToPrint();

    Optional<Product> buyProductFromTray(String traySymbol); // TODO
}
