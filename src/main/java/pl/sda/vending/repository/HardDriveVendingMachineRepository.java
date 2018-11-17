package pl.sda.vending.repository;

import pl.sda.vending.model.VendingMachine;
import pl.sda.vending.service.repository.VendingMachineRepository;
import pl.sda.vending.util.Configuration;

import java.io.*;
import java.util.Optional;

public class HardDriveVendingMachineRepository implements VendingMachineRepository {
    private final String repoLocation;

    public HardDriveVendingMachineRepository(Configuration configuration) {
        repoLocation = configuration.getStringProperty(
                "repository.hardrive.vm.path", "VendingMachine.ser");
    }

    @Override
    public VendingMachine save(VendingMachine machine) {
        try (ObjectOutputStream objectOutputStream = // automatyczny close() z resource blokiem
                     new ObjectOutputStream(
                             new FileOutputStream(repoLocation))) {
            objectOutputStream.writeObject(machine);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return machine;
    }

    @Override
    public Optional<VendingMachine> load() {
        try (ObjectInputStream objectInputStream =
                     new ObjectInputStream(
                             new FileInputStream(repoLocation))) {
            VendingMachine deserialized = (VendingMachine) objectInputStream.readObject();
            return Optional.ofNullable(deserialized);
        } catch (IOException e) {
            System.out.println("Vending Machine Repo file not found");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not find Vending Machine class");
        }
        return Optional.empty();
    }
}
