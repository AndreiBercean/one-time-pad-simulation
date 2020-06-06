package simulation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EncryptionController {
    private Integer bobEncodeOffset = 320;
    private Integer aliceEncodeOffset = 0;
    private Integer bobDecodeOffset = 320;
    private Integer aliceDecodeOffset = 0;
    private KeyController controller = new KeyController();
    public int[] stateOfEncodeKey = new int[640];
    public int[] stateOfDecodeKey = new int[640];

    public EncryptionController() {
        Arrays.fill(stateOfEncodeKey, 0, 639, 0);
        Arrays.fill(stateOfDecodeKey, 0, 639, 0);
    }

    public List<Integer> encryptMessage(String message, String user){
        if(aliceEncodeOffset < 320 && bobEncodeOffset < 640 && aliceDecodeOffset <  320 && bobDecodeOffset < 640 ) {
            int index = 0;
            if(user.equals("Alice"))index = aliceEncodeOffset;
            else index = bobEncodeOffset;

            List<Integer> output = new ArrayList<>();
            for(int i = 0; i < message.length(); i++){
                int value = message.charAt(i);
                value ^= controller.getByte(index++);
                output.add(value);
            }

            if(user.equals("Alice")) {
                for(int i = aliceEncodeOffset; i < index; i++)
                    stateOfEncodeKey[i] = 1;
                aliceEncodeOffset = index;
            }
            else {
                for(int i = bobEncodeOffset; i < index; i++)
                    stateOfEncodeKey[i] = 1;
                bobEncodeOffset = index;
            }

            return output;
        }
        else {
            return null;
        }
    }

    public String decryptMessage(List<Integer> message, String user){
        if(aliceEncodeOffset < 320 && bobEncodeOffset < 640 && aliceDecodeOffset <  320 && bobDecodeOffset < 640 ) {
            int index = 0;
            if(user.equals("Alice"))index = aliceDecodeOffset;
            else index = bobDecodeOffset;

            StringBuilder decodedMessage = new StringBuilder();
            for(Integer value : message){
                value ^= controller.getByte(index++);
                decodedMessage.append((char) value.intValue());
            }

            if(user.equals("Alice"))aliceDecodeOffset = index;
            else bobDecodeOffset = index;

            return decodedMessage.toString();
        }
        else {
            return "";
        }
    }

    public void loseMessage(String message, String user){
        if(aliceEncodeOffset < 320 && bobEncodeOffset < 640 && aliceDecodeOffset <  320 && bobDecodeOffset < 640 ) {
            int index = message.length();
            if(user.equals("Alice")) {
                index += aliceEncodeOffset;
                for(; aliceEncodeOffset < index; aliceEncodeOffset++)
                    stateOfEncodeKey[aliceEncodeOffset] = 2;
                aliceDecodeOffset = aliceEncodeOffset;
            }
            else {
                index += bobEncodeOffset;
                for(; bobEncodeOffset < index; bobEncodeOffset++)
                    stateOfEncodeKey[bobEncodeOffset] = 2;
                bobDecodeOffset = bobEncodeOffset;
            }
        }
        else {
            return;
        }
    }

    public Integer getBobEncodeOffset() {
        return bobEncodeOffset;
    }

    public Integer getAliceEncodeOffset() {
        return aliceEncodeOffset;
    }

    public Integer getBobDecodeOffset() {
        return bobDecodeOffset;
    }

    public Integer getAliceDecodeOffset() {
        return aliceDecodeOffset;
    }

    public KeyController getKeyController() {
        return controller;
    }

    public int[] getStateOfEncodeKey() {
        return stateOfEncodeKey;
    }

    public int[] getStateOfDecodeKey() {
        return stateOfDecodeKey;
    }
}
