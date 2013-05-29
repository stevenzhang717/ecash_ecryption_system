package Main;

import Users.Bank;
import Users.Customer;
import Users.Merchant;

import java.util.Scanner;

/**
 * Created by IntelliJ IDEA.
 * User: stevenzhang717
 * Date: 1/05/12
 * Time: 10:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class MainApplication {
    Customer customer;
    Bank bank = new Bank();
    Merchant merchant = new Merchant();

    public static void main(String[] args) {
        MainApplication app = new MainApplication();
        app.menu();
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        int select = 0;

        while (true) {
            System.out.println("Please select sub-system you want to use:");
            System.out.println("1. Customer");
            System.out.println("2. Bank");
            System.out.println("3. Merchant");
            System.out.println("4. Exit");
            System.out.println("Selection: ");
            select = scanner.nextInt();

            switch (select) {

                case 1:
                    customerMenu();
                    break;


                case 2:
                    bankMenu();
                    break;

                case 3:
                    merchantMenu();
                    break;


                case 4:
                    System.exit(0);

                default:
                    System.out.println("Please enter a valid selection.");

            }

        }
    }

    private void merchantMenu() {
        Scanner scanner = new Scanner(System.in);
        int select = 0;

        while (true) {
            System.out.println("Please select functions you want to use:");
            System.out.println("1. Generation of selector");
            System.out.println("2. Back");
            System.out.println("3. Exit");
            System.out.println("Selection: ");
            select = scanner.nextInt();

            switch (select) {

                case 1:
                    System.out.println(merchant.getSelector());
                    break;


                case 2:
                    menu();
                    break;

                case 3:
                    System.exit(0);

                default:
                    System.out.println("Please enter a valid selection.");

            }

        }

    }

    private void bankMenu() {
        Scanner scanner = new Scanner(System.in);
        int select = 0;

        while (true) {
            System.out.println("Please select functions you want to use:");
            System.out.println("1. random number selection for the one that remains blind");
            System.out.println("2. verify");
            System.out.println("3. Generation of commitment String");
            System.out.println("4. Check whether the cash is legal");
            System.out.println("5. Exit");
            System.out.println("6. Back");
            System.out.println("Selection: ");
            select = scanner.nextInt();

            switch (select) {

                case 1:
                    System.out.println(bank.ramdon());
                    break;


                case 2:
                    bank.verify();
                    break;

                case 3:
                    System.out.println(bank.getCommitment());
                    break;
                
                case 4:
                    bank.checkCachReuse();
                    break;

                case 5:
                    System.exit(0);
                    break;

                case 6:
                    menu();

                default:
                    System.out.println("Please enter a valid selection.");

            }
        }
    }

    private void customerMenu() {
        Scanner scanner = new Scanner(System.in);
        int select = 0;

        while (true) {
            System.out.println("Please select the function you want to use:");
            System.out.println("1. Generate the order");
            System.out.println("2. Blind the order");
            System.out.println("3. unblind n-1 order");
            System.out.println("4. Show identity String");
            System.out.println("5. Decrypt half of the identity String");
            System.out.println("6. Back");
            System.out.println("7. Exit");
            System.out.println("Selection: ");
            select = scanner.nextInt();

            switch (select) {

                case 1:
                    generateOrder();
                    break;

                case 2:
                    blindOrders();
                    break;

                case 3:
                    unblindOrders();
                    break;

                case 4:
                    showIdentityString();
                    break;

                case 5:
                    decryptHalf();
                    break;

                case 6:
                    menu();
                    break;

                case 7:
                    System.exit(0);

                default:
                    System.out.println("Please enter a valid selection.");

            }

        }

    }

    private void decryptHalf() {
        customer.decryptHalf(merchant.getSelector());
    }

    private void showIdentityString() {
        customer.showIdentityString();
    }

    private void unblindOrders() {
        customer.unblindOrders(bank.ramdon());
    }

    private void blindOrders() {
        customer.blindOrders();
    }

    private void generateOrder() {
        Scanner scanner = new Scanner(System.in);
        String identity;
        String comitment;
        int amount;
        System.out.println("Please enter the identity String of yours:");
        identity = scanner.nextLine();
        System.out.println("Please enter amount of cash you want to generate:");
        amount = scanner.nextInt();
        System.out.println("Getting commitment String from the bank.");
        comitment = bank.getCommitment();
        System.out.println("Creating the order...");
        customer = new Customer(identity, comitment);
        customer.generateOrder(amount);
        System.out.println("Complete!");

    }

}

