package pl.sda.vending.service;

import pl.sda.vending.controller.service.CustomerService;
import pl.sda.vending.model.Product;
import pl.sda.vending.model.VendingMachine;
import pl.sda.vending.model.VendingMachineSnapshot;
import pl.sda.vending.service.repository.VendingMachineRepository;

import java.util.Optional;

public class DefaultCustomerService implements CustomerService {
    private final VendingMachineRepository machineRepository;

    public DefaultCustomerService(VendingMachineRepository machineRepository) {
        this.machineRepository = machineRepository;
    }

    @Override
    public Optional<VendingMachineSnapshot> loadMachineToPrint() {
        Optional<VendingMachine> loadedMachine = machineRepository.load();
        return loadedMachine.map(VendingMachine::snapshot);
    }

    @Override
    public Optional<Product> buyProductFromTray(String traySymbol) {
        Optional<VendingMachine> loadedMachine = machineRepository.load();
        if (loadedMachine.isPresent()) {
            VendingMachine machine = loadedMachine.get();
            Optional<Product> boughtProduct = machine.buyProductWithSymbol(traySymbol);
            machineRepository.save(machine);
            return boughtProduct;
        } else {
            return Optional.empty();
        }
    }
}
