package pl.sda.vending.service;

import pl.sda.vending.controller.service.EmployeeService;
import pl.sda.vending.model.Product;
import pl.sda.vending.model.Tray;
import pl.sda.vending.model.VendingMachine;
import pl.sda.vending.service.repository.VendingMachineRepository;
import pl.sda.vending.util.Configuration;

import java.util.Optional;

public class DefaultEmployeeService implements EmployeeService {
    private final VendingMachineRepository machineRepository;
    private final Configuration configuration;
    private VendingMachine machine;

    public DefaultEmployeeService(VendingMachineRepository machineRepository, Configuration configuration) {
        this.machineRepository = machineRepository;
        this.configuration = configuration;
    }

    @Override
    public Optional<String> addTray(Tray tray) {
        Optional<VendingMachine> loadedVendingMachine = machineRepository.load();
        VendingMachine machine = loadedVendingMachine.orElseGet(() -> new VendingMachine(configuration));
        if (machine.placeTray(tray)) {
            machineRepository.save(machine);
            return Optional.empty();
        }
        return Optional.of("   Could not add tray, check provided position.");
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
                return Optional.of("   Tray could not be removed.");
            }
        } else {
            return Optional.of("   There is no vending machine.");
        }
    }

    @Override
    public Optional<String> addProduct(String traySymbol, String productName, Integer howManyToAdd) {
        Optional<VendingMachine> loadedMachine = machineRepository.load();
        if (loadedMachine.isPresent()) {
            machine = loadedMachine.get();
            if (machine.getTrayForSymbol(traySymbol).isPresent()) {
                return addProductsWithCheck(traySymbol, productName, howManyToAdd);
            } else {
                return Optional.of("   There is no tray here to add products - install tray.");
            }
        } else {
            return Optional.of("   There is no vending machine, no products added.");
        }
    }

    private Optional<String> addProductsWithCheck(String traySymbol, String productName, Integer howManyToAdd) {
        int counter = 0;

        for (int i = 0; i < howManyToAdd; i++) {
            Product productToAdd = new Product(productName);
            if (machine.addProductToTray(traySymbol, productToAdd)) { // TODO
                machineRepository.save(machine);
                counter++;
            } else if (counter == 0) {
                return Optional.of("   Tray is full, cannot add any product(s).");
            } else {
                return Optional.of("   Not added " + (howManyToAdd - counter) + " product(s).");
            }
        }
        return Optional.empty();
    }

    @Override // TODO
    public Optional<String> removeProduct(String traySymbol, String productName, Integer howManyToRemove) {
        return Optional.empty();
    }

    @Override
    public Optional<String> changePrice(String traySymbol, Long newPrice) {
        Optional<VendingMachine> loadedMachine = machineRepository.load();
        if (loadedMachine.isPresent()) {
            VendingMachine machine = loadedMachine.get();
            if (machine.updatePriceForSymbol(traySymbol, newPrice)) {
                machineRepository.save(machine);
                return Optional.empty();
            } else {
                return Optional.of("   Couldn't change tray price.");
            }
        } else {
            return Optional.of("   There is no vending machine.");
        }
    }

//    @Override
//    public Optional<String> changePrice_(String traySymbol, Long updatedPrice) {
//        Optional<VendingMachine> loadedMachine = machineRepository.load();
//        if (loadedMachine.isPresent()) {
//            VendingMachine machine = loadedMachine.get();
//            boolean successful = machine.updatePriceForSymbol(traySymbol, updatedPrice);
//            machineRepository.save(machine);
//            if (successful) {
//                return Optional.empty();
//            } else {
//                return Optional.of("   Could not change price, check tray symbol.");
//            }
//        }
//        return Optional.of("   There is no Vending Machine, create one first.");
//    }

    @Override
    public Optional<String> checkTrayAvailability(String traySymbol) {
        Optional<VendingMachine> loadedMachine = machineRepository.load();
        if (loadedMachine.isPresent()) {
            VendingMachine machine = loadedMachine.get();
            if (machine.getTrayForSymbol(traySymbol).isPresent()) {
                return Optional.empty();
            } else {
                return Optional.of("   Could not find tray, check provided position.");
            }
        } else {
            return Optional.of("   There is no vending machine.");
        }
    }
}
