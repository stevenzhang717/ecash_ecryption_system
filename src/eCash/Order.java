package eCash;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: stevenzhang717
 * Date: 1/05/12
 * Time: 8:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class Order implements Serializable {

    private double amount;
    private UUID id;
    private IdentityPair[] identityStrings;

    public Order(String info, double amount, String commitment, String commitkey) {
        this.amount = amount;
        this.id = UUID.randomUUID();
        identityStrings = new IdentityPair[12];
        for (int i = 0; i < identityStrings.length; i++) {
            identityStrings[i] = new IdentityPair(info, commitment, commitkey);
        }
        System.out.println("Order created");
    }

    public void showIdentityStrings(String commitKey) {
        for (int i = 0; i < identityStrings.length; i++) {
            identityStrings[i].openLeft(commitKey);
            identityStrings[i].openRight(commitKey);
        }
    }

    public void getInfo() {
        System.out.println("Order Information:");
        System.out.println("Amount: " + this.amount);
        System.out.println("Uniqueness String: " + this.id.toString());
        System.out.println("Identity Strings: ");
        for (int i = 0; i < identityStrings.length; i++) {
            System.out.println("I" + i + ":  ");
            identityStrings[i].printInfo();
        }
    }

    public void decryptHalf(String numbers, String commitkey) {
        for (int i = 0; i < numbers.length(); i++) {
            if (numbers.charAt(i) == '0') {
                identityStrings[i].openLeft(commitkey);
            }

            if (numbers.charAt(i) == '1') {
                identityStrings[i].openRight(commitkey);
            }
        }
        System.out.println("After unblinding half of the identity Strings");
        getInfo();
    }

    public IdentityPair[] getIdentityStrings() {
        return identityStrings;

    }

    public UUID getID() {
        return id;
    }

    public boolean verify() {
        return (this.amount > 0);
    }
}
