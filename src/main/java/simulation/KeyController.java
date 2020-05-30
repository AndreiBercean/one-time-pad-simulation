package simulation;

import java.io.*;

public class KeyController {

    private int[]key;

    public KeyController() {
        try (BufferedReader br = new BufferedReader(new FileReader("key.txt"))) {
            key = new int[640];
            int index = 0;
            String line;
            while ((line = br.readLine()) != null) {
                String[] bytes = line.split(" ");
                for(String s : bytes)
                    key[index++] = Integer.parseInt(s,2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int getByte(int index) {
        return key[index];
    }
}
