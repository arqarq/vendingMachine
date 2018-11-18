package pl.sda.vending;

import pl.sda.vending.controller.CustomerOperationController;
import pl.sda.vending.controller.EmployeeOperationController;
import pl.sda.vending.controller.service.EmployeeService;
import pl.sda.vending.model.Product;
import pl.sda.vending.repository.HardDriveVendingMachineRepository;
import pl.sda.vending.service.DefaultEmployeeService;
import pl.sda.vending.service.repository.VendingMachineRepository;
import pl.sda.vending.util.Configuration;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    private Configuration configuration = new Configuration();
    private VendingMachineRepository vendingMachineRepository = new HardDriveVendingMachineRepository(configuration);
    private EmployeeService employeeService = new DefaultEmployeeService(vendingMachineRepository, configuration);
    private EmployeeOperationController employeeOperationController = new EmployeeOperationController(employeeService);
    //    private VendingMachine vendingMachine = new VendingMachine(configuration);
    private CustomerOperationController customerOperationController =
            new CustomerOperationController(vendingMachineRepository, configuration);

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
                        System.out.print(" > Please, input tray symbol: ");
                        String traySymbol = new Scanner(System.in).nextLine().toUpperCase();
                        Optional<Product> productBought = customerOperationController.buyProductForSymbol(traySymbol);
                        if (productBought.isPresent()) {
                            System.out.println("   Success, you bought: " + productBought.get().getName());
                        } else {
                            System.out.println("   Product N/A");
                        }
//                        System.out.println();
                        // 1. pobrac od uzytkownika symbol tacki
                        // 2. wywolac odpowiednia metode z kontrolera
                        //    Optional buyProductForSymbol(String traySymbol)
                        // 3. Jezeli udalo sie kupic produkt
                        //    To wypisz na ekran potwierdzenie oraz nazwe produktu
                        // 4. Jezeli nie udalo sie kupic, to wyswietlamy komunikat o braku produktu
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
            throw new IllegalArgumentException("Invalid selection format");
        }
    }

    private ServiceMenuSelection getServiceUserSelection() {
        System.out.print(" > Your selection: ");
        String userSelection = new Scanner(System.in).nextLine();
        try {
            Integer menuNumber = Integer.valueOf(userSelection);
            return ServiceMenuSelection.selectionForOptionNumber(menuNumber);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid selection format");
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
//                    System.out.println();
                        break;
                    case REMOVE_TRAY:
                        break;
                    case ADD_PRODUCT:
                        break;
                    case REMOVE_PRODUCT:
                        break;
                    case CHANGE_PRICE:
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
            // wyswietl menu uzytkownika serwisowego
            // odczytac, ktora opcje wybral serwisant
            //   mozna wzorowac sie na getUserSelection
            // za pomoca switch-case, obsluzyc jego wybor
            //   dla case ADD_TRAY wywolac metode addTray
            //   znajdujaca sie w kontrolerze dla serwisanta
        }
    }

    private void printServiceMenu() {
        ServiceMenuSelection[] allPossibleSelections = ServiceMenuSelection.values();
        for (ServiceMenuSelection menuPosistion : allPossibleSelections) {
            System.out.println(menuPosistion.getOptionNumber() + ". " + menuPosistion.getOptionMessage());
        }
    }
}
