package Users;

import eCash.Order;

import java.io.*;
import java.math.BigInteger;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: stevenzhang717
 * Date: 1/05/12
 * Time: 9:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class Customer {

    private String info;
    private String commitKey;
    private String commitment;
    private BigInteger blindFactor;
    private int unopen;
    public static final String keyAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public Customer(String info, String commitment) {
        this.info = info;
        this.commitment = commitment;
        commitKey = getSecretKey();
        Random random = new Random();
        blindFactor = new BigInteger(256, random);

    }


    public void generateOrder(int amount) {
        System.out.println("Generation of the order files: ");
        for (int i = 0; i < 6; i++) {
            Order order = new Order(this.info, amount, this.commitment, this.commitKey);
            order.getInfo();
            createOrderFile(order, "order" + i);
        }

    }

    public void showIdentityString() {
        System.out.println("Showing identity Strings for n-1 orders: ");
        for (int i = 0; i < 6; i++) {
            if (i != unopen) {
                Order order = readOrderFile("unblindOrder" + i);
                order.showIdentityStrings(commitKey);
                order.getInfo();
                createOrderFile(order, "identityShownOrder" + i);
            }
        }
    }

    public void decryptHalf(String selector) {
        System.out.println("Decrypting half of the cash:");
        System.out.println("The selector is " + selector);
        createOrderFile(unblind((readBlindFile("blindCash" + unopen))), "eCash");
        Order cash = readOrderFile("eCash");
        cash.decryptHalf(selector, commitKey);
        cash.getInfo();
        createOrderFile(unblind((readBlindFile("blindCash" + unopen))), "spendeCash");
    }

    public void unblindOrders(int num) {
        unopen = num;
        System.out.println("unblinding orders except for number " + num + ": ");
        for (int i = 0; i < 6; i++) {
            if (i != num)
                createOrderFile(unblind((readBlindFile("blindCash" + i))), "unblindOrder" + i);
        }

    }


    public void blindOrders() {
        System.out.println("Reading in the order files: ");
        for (int i = 0; i < 6; i++) {
            createBlindedFile(blind(readOrderFile("order" + i)), "blindCash" + i);
        }

    }

    private String getSecretKey() {
        Random random = new Random();
        char[] key = new char[info.length()];
        for (int i = 0; i < info.length(); i++) {
            key[i] = keyAlphabet.charAt(random.nextInt(keyAlphabet.length()));
        }
        return new String(key);
    }

    public void createOrderFile(Order order, String fileName) {
        try {
            FileOutputStream fout = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(order);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    public void createBlindedFile(BigInteger bi, String fileName) {
        try {
            FileOutputStream fout = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(bi);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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

    public BigInteger readBlindFile(String fileName) {
        try {
            FileInputStream fin = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fin);
            return (BigInteger) ois.readObject();
        } catch (Exception e) {
        }
        return null;
    }

    public BigInteger blind(Order order) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(bos);
            out.writeObject(order);
            byte[] orderBytes = bos.toByteArray();
            BigInteger blinedMessage = new BigInteger(orderBytes);
            return blinedMessage.multiply(blindFactor);

        } catch (Exception e) {
        }
        return null;
    }

    public Order unblind(BigInteger bi) {
        try {
            bi = bi.divide(blindFactor);
            ByteArrayInputStream in = new ByteArrayInputStream(bi.toByteArray());
            ObjectInputStream is = new ObjectInputStream(in);
            return (Order) is.readObject();
        } catch (Exception e) {
        }
        return null;
    }

    public int getD(int an, int e) {
        return (an + 1) / e;

    }

    public int gcdValue(int an) {
        int e = new Random().nextInt(100) + 0;
        if (BigInteger.valueOf(an).gcd(BigInteger.valueOf(e)).intValue() == 1)
            return e;
        return gcdValue(an);

    }

    public int getPrimeNumber() {
        Random generator = new Random();
        int num = generator.nextInt(100) + 0;
        if (isPrime(num)) {
            return num;
        }
        return getPrimeNumber();
    }

    private boolean isPrime(int num) {
        if (num % 2 == 0) {
            return false;
        } else {
            for (int i = 3; i * i <= num; i += 2) {
                if (num % i == 0)
                    return false;
            }
        }
        System.out.println(num);
        return true;
    }


}
