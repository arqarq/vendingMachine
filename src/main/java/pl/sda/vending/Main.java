package pl.sda.vending;

import pl.sda.vending.controller.CustomerOperationController;
import pl.sda.vending.controller.EmployeeOperationController;
import pl.sda.vending.controller.service.CustomerService;
import pl.sda.vending.controller.service.EmployeeService;
import pl.sda.vending.repository.HardDriveVendingMachineRepository;
import pl.sda.vending.service.DefaultCustomerService;
import pl.sda.vending.service.DefaultEmployeeService;
import pl.sda.vending.service.repository.VendingMachineRepository;
import pl.sda.vending.util.Configuration;

import java.util.Scanner;

public class Main {
    private final Configuration configuration =
            new Configuration();
    private final VendingMachineRepository vendingMachineRepository =
            new HardDriveVendingMachineRepository(configuration);
    private final EmployeeService employeeService =
            new DefaultEmployeeService(vendingMachineRepository, configuration);
    private final CustomerService customerService =
            new DefaultCustomerService(vendingMachineRepository);
    private final EmployeeOperationController employeeOperationController =
            new EmployeeOperationController(employeeService);
    private final CustomerOperationController customerOperationController =
            new CustomerOperationController(customerService, configuration);

    public static void main(String[] args) {
        new Main().startApplication();
    }

    private void startApplication() {
        while (true) {
            customerOperationController.printMachine();
            printMenu();
            try {
                UserMenuSelection userSelection = getUserSelection();
                switch (userSelection) {
                    case BUY_PRODUCT:
                        customerOperationController.buyProduct();
                        break;
                    case EXIT:
                        System.out.println("   Bye");
                        return;
                    case SERVICE_MENU:
                        handleServiceUser();
                        break;
                    default:
                        System.out.println("   Invalid selection");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private UserMenuSelection getUserSelection() {
        System.out.print(" > Your selection: ");
        String userSelection = new Scanner(System.in).nextLine();
        try {
            Integer menuNumber = Integer.valueOf(userSelection);
            return UserMenuSelection.selectionForOptionNumber(menuNumber);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("   Invalid selection format");
        }
    }

    private ServiceMenuSelection getServiceUserSelection() {
        System.out.print(" > Your selection: ");
        String userSelection = new Scanner(System.in).nextLine();
        try {
            Integer menuNumber = Integer.valueOf(userSelection);
            return ServiceMenuSelection.selectionForOptionNumber(menuNumber);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("   Invalid selection format");
        }
    }

    private void printMenu() {
        UserMenuSelection[] allPossibleSelections = UserMenuSelection.values();
        for (UserMenuSelection menuPosistion : allPossibleSelections) {
            System.out.println(menuPosistion.getOptionNumber() + ". " + menuPosistion.getOptionText());
        }
    }

    private void handleServiceUser() {
        while (true) {
            customerOperationController.printMachine();
            printServiceMenu();
            try {
                ServiceMenuSelection serviceUserSelection = getServiceUserSelection();
                switch (serviceUserSelection) {
                    case ADD_TRAY:
                        employeeOperationController.addTray();
                        break;
                    case REMOVE_TRAY:
                        employeeOperationController.removeTray();
                        break;
                    case ADD_PRODUCT:
                        employeeOperationController.addProducts();
                        break;
                    case REMOVE_PRODUCT:
                        employeeOperationController.removeProducts();
                        break;
                    case CHANGE_PRICE:
                        employeeOperationController.changePrice();
                        break;
                    case EXIT:
                        System.out.println("   Going back to user menu");
                        return;
                    default:
                        System.out.println("   Invalid selection");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void printServiceMenu() {
        ServiceMenuSelection[] allPossibleSelections = ServiceMenuSelection.values();
        for (ServiceMenuSelection menuPosistion : allPossibleSelections) {
            System.out.println(menuPosistion.getOptionNumber() + ". " + menuPosistion.getOptionMessage());
        }
    }
}
