package Users;

import eCash.Order;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: stevenzhang717
 * Date: 2/05/12
 * Time: 12:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class Bank {
    ArrayList<Order> usedCash = new ArrayList<Order>();

    public Bank() {

    }

    public int ramdon() {
        Random generator = new Random();
        return generator.nextInt(6) + 0;
    }

    public String getCommitment() {
        return "BankAU";
    }

    public void verify() {
        System.out.println("Showing identity Strings for n-1 orders: ");
        for (int i = 0; i < 6; i++) {
            File f = new File("unblindOrder" + i);
            if (f.exists()) {
                System.out.println("Verifying unblind order " + i);
                System.out.println("Valid: " + readOrderFile("unblindOrder" + i).verify());
            }
        }
    }

    public void checkCachReuse() {
        Order order = readOrderFile("eCash");
        if (usedCash.size() == 0) {
            usedCash.add(order);
            System.out.println("Cash Accepted!");
        } else {

            for (int i = 0; i < usedCash.size(); i++) {
                if (usedCash.get(i).getID().equals(order.getID())) {
                    System.out.println("Cash unique number already exists, you are trying to cheat!");
                    if (usedCash.get(i).getIdentityStrings().equals(order.getIdentityStrings())) {
                        System.out.println("The merchant copied the money order!");
                    }
                    System.out.println("The customer is trying to cheat!");
                    return;
                }
                System.out.println("Cash Accepted");

            }
        }

    }


    public Order readOrderFile(String fileName) {
        try {
            FileInputStream fin = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fin);
            return (Order) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private String recoverSplitting(String key, String infoCipher) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < key.length(); i++)
            sb.append((char) (key.charAt(i) ^ infoCipher.charAt(i)));
        return sb.toString();
    }


}

