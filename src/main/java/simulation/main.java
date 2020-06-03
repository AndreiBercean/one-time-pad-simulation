package simulation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class main {

    static String getBinary(char c) {
        //System.out.println(c + " to " + (int) c);
        String binary = Integer.toBinaryString((int) c);
        while(binary.length() < 8){
            binary = "0" + binary;
        }
        return binary;
    }

    public static void main(String[] args) {
        EncryptionController controller = new EncryptionController();
        String message1 = "This is a test1";
        String message2 = "This is a test2";
        String message3 = "This is a test3";

        System.out.println(controller.decryptMessage(controller.encryptMessage(message1, "Alice"), "Alice"));
        System.out.println(controller.decryptMessage(controller.encryptMessage(message2, "Alice"), "Alice"));
        System.out.println(controller.decryptMessage(controller.encryptMessage(message3, "Bob"), "Bob"));
        System.out.println(controller.decryptMessage(controller.encryptMessage(message1, "Alice"), "Alice"));
        System.out.println(controller.decryptMessage(controller.encryptMessage(message2, "Alice"), "Alice"));
        System.out.println(controller.decryptMessage(controller.encryptMessage(message1, "Bob"), "Bob"));
        System.out.println(Arrays.toString(controller.getKeyController().getFirstHalf()));
        System.out.println(Arrays.toString(controller.getKeyController().getSecondHalf()));
    }
}
