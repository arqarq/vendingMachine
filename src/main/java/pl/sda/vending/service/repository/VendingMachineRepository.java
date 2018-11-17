package pl.sda.vending.service.repository;

import pl.sda.vending.model.VendingMachine;

import java.util.Optional;

public interface VendingMachineRepository {
    VendingMachine save(VendingMachine machine); // identyfikator jest zawarty w VendingMachine

    Optional<VendingMachine> load();
}
