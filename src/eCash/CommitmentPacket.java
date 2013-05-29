package eCash;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: stevenzhang717
 * Date: 1/05/12
 * Time: 2:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommitmentPacket implements Serializable {
    private boolean opened;
    private String packetContent;
    private String encryptedCommitmentString;

    public CommitmentPacket(byte[] info, String commitment, String commitkey) {
        packetContent = encrypt(commitkey, info);
        encryptedCommitmentString = encrypt(commitkey, commitment.getBytes());
        opened = false;
    }


    public String encrypt(String commitkey, byte[] content) {
        byte[] result = new byte[content.length];

        for (int i = 0; i < content.length; i++)
            result[i] = (byte) (content[i] ^ commitkey.getBytes()[i]);
        return new String(result);
    }

    public void decrypt(String commitkey) {
        try {
            byte[] dec = this.packetContent.getBytes();
            byte[] result = new byte[dec.length];
            for (int i = 0; i < dec.length; i++)
                result[i] = (byte) (dec[i] ^ (commitkey.getBytes()[i]));
            this.opened = true;
            packetContent = new String(result);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    public String getEncryptedContent() {
        return packetContent;
    }

    public String getEncryptedCommitmentString() {
        return encryptedCommitmentString;
    }

    public boolean isOpened() {
        return opened;
    }


}
