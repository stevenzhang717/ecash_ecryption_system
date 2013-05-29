package Users;

import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 * User: stevenzhang717
 * Date: 6/05/12
 * Time: 9:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class Merchant {
    public static final String ramdonAlphabet = "01";

    public String getSelector() {
        Random random = new Random();
        char[] selector = new char[12];
        for (int i = 0; i < 12; i++) {
            selector[i] = ramdonAlphabet.charAt(random.nextInt(ramdonAlphabet.length()));
        }

        return new String(selector);
    }


}
