package eCash;

import java.io.Serializable;
import java.util.Random;

/**
 * This is the class of Identity pair object, each identity pair object have left packet and right packet.
 * One packet store the identity information after encrypted by secret Splitting
 * The other packet store the encryption key of secret splitting.
 */
public class IdentityPair implements Serializable {
    private CommitmentPacket left;
    private CommitmentPacket right;
    public static final String keyAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public IdentityPair(String info, String commitment, String commitKey) {
        String key = generateKey(info.length());   //generate the key of secret splitting and store it as an local variable
        left = new CommitmentPacket(secretSplitting(info, key), commitment, commitKey);  //the first argument is the Xor encrypted identity information.
        right = new CommitmentPacket(key.getBytes(), commitment, commitKey);
    }

    private byte[] secretSplitting(String information, String originalKey) {
        //All the content will be converted to byte before encryption, coz, directly Xor two Strings may result in informaiton lost.
        byte[] info = information.getBytes();
        byte[] key = originalKey.getBytes();
        byte[] result = new byte[info.length];
        for (int i = 0; i < info.length; i++)
            result[i] = (byte) (info[i] ^ key[i]);
        return result;
    }

    private String generateKey(int length) {
        Random random = new Random();
        char[] key = new char[length];
        for (int i = 0; i < length; i++) {
            key[i] = keyAlphabet.charAt(random.nextInt(keyAlphabet.length()));
        }
        return new String(key);
    }

    public boolean isLeftOpened() {
        return left.isOpened();
    }

    public boolean isRightOpened() {
        return right.isOpened();
    }

    private String revertXor(byte[] info, byte[] result) {
        byte[] revertedString = new byte[info.length];
        for (int i = 0; i < info.length; i++)
            revertedString[i] = (byte) (info[i] ^ result[i]);
        return new String(revertedString);
    }

    public void printInfo() {
        System.out.println("Left: " + left.getEncryptedContent());
        System.out.println("right: " + right.getEncryptedContent());
    }

    public void openLeft(String commitKey) {
        //To change body of created methods use File | Settings | File Templates.
        left.decrypt(commitKey);
    }

    public void openRight(String commitKey) {
        //To change body of created methods use File | Settings | File Templates.
        right.decrypt(commitKey);
    }
}
