package simulation;

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
        /*String message = "This is a test";
        for(int i = 0 ; i < message.length(); i++){
            String binaryChar = getBinary(message.charAt(i));
            System.out.println(message.charAt(i) + ": " + binaryChar);
        }
        for(int i = 0 ; i < message.length(); i++){
            String binaryChar = getBinary(message.charAt(i));
            System.out.println(message.charAt(i) + ": " + (char)Integer.parseInt( binaryChar, 2));
        }*/
        KeyController controller = new KeyController();
        System.out.println(controller.getByte(340));

        int x = 304;
        System.out.println(x);
        x ^= controller.getByte(340);
        System.out.println(x);
        x ^= controller.getByte(340);
        System.out.println(x);

    }
}
