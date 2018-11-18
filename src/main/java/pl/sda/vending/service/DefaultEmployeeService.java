package pl.sda.vending.service;

import pl.sda.vending.controller.service.EmployeeService;
import pl.sda.vending.model.Tray;
import pl.sda.vending.model.VendingMachine;
import pl.sda.vending.service.repository.VendingMachineRepository;
import pl.sda.vending.util.Configuration;

import java.util.Optional;

public class DefaultEmployeeService implements EmployeeService {
    private final VendingMachineRepository machineRepository;
    private final Configuration configuration;

    public DefaultEmployeeService(VendingMachineRepository machineRepository, Configuration configuration) {
        this.machineRepository = machineRepository;
        this.configuration = configuration;
    }

    @Override
    public Optional<String> addTray(Tray tray) {
        Optional<VendingMachine> loadedVendingMachine = machineRepository.load();
        VendingMachine vendingMachine = loadedVendingMachine.orElseGet(() -> new VendingMachine(configuration));
        if (vendingMachine.placeTray(tray)) {
            machineRepository.save(vendingMachine);
            return Optional.empty();
        }
        return Optional.of("Could not add tray, check provided position");
        // loading VendingMachine
        // add tray
        // check if added
        // if added then save
        // else return error message
    }

    @Override
    public Optional<String> removeTrayWithSymbol(String traySymbol) {
        Optional<VendingMachine> loadedMachine = machineRepository.load();
        if (loadedMachine.isPresent()) {
            VendingMachine machine = loadedMachine.get();
            Optional<Tray> removedTray = machine.removeTrayWithSymbol(traySymbol);
            if (removedTray.isPresent()) {
                machineRepository.save(machine);
                return Optional.empty();
            } else {
                return Optional.of("Tray could not be removed.");
            }
        } else {
            return Optional.of("There is no vending machine");
        }
    }
}
