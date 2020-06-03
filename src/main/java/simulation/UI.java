package simulation;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UI {

    static String formatKey(int[] key){
        StringBuilder output = new StringBuilder();
        for(int i = 0; i < key.length; i++) {
            if(key[i] < 16) output.append("0").append(Integer.toHexString(key[i])).append(" ");
            else output.append(Integer.toHexString(key[i])).append(" ");
        }
        return output.toString();
    }

    static String convertToString(List<Integer> message){
        String output = "";
        for(Integer i : message){
            if(i < 16) output += "0" + Integer.toHexString(i) + " ";
            else output += Integer.toHexString(i) + " ";
        }
        return output;
    }

    public static void main(String[] args) {
        EncryptionController controller = new EncryptionController();
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font bigFont = new Font("Verdana", Font.PLAIN, 20);
        Font middleFont = new Font("Verdana", Font.PLAIN, 16);
        Font smallFont = new Font("Verdana", Font.PLAIN, 12);
        Font extraSmallFont = new Font("Verdana", Font.PLAIN, 8);

        JFrame frame = new JFrame();
        frame.setMaximizedBounds(env.getMaximumWindowBounds());
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        frame.setLayout(null);

        JPanel alicePanel = new JPanel();
        alicePanel.setBounds(10, 10, 930, 980);
        alicePanel.setBackground(Color.gray);
        alicePanel.setLayout(null);

        JLabel aliceLabel = new JLabel("ALICE");
        JLabel aliceKeyLabel = new JLabel("Key");
        JTextPane aliceCodeKey = new JTextPane();
        JLabel aliceDecodeKeyLabel = new JLabel("Decode Key");
        JTextPane aliceDecodeKey = new JTextPane();
        JLabel aliceMessageLabel = new JLabel("Message");
        JTextField aliceMessageBox = new JTextField();
        JLabel aliceMessagesLabel = new JLabel("Received messages");
        JTextArea aliceMessages = new JTextArea();
        JButton aliceEncryptButton = new JButton("Send");
        JButton aliceDecryptButton = new JButton("Decrypt");

        JPanel bobPanel = new JPanel();
        bobPanel.setBounds(960, 10, 930, 980);
        bobPanel.setBackground(Color.gray);
        bobPanel.setLayout(null);

        JLabel bobLabel = new JLabel("BOB");
        JLabel bobKeyLabel = new JLabel("Key");
        JTextPane bobCodeKey = new JTextPane();
        JLabel bobDecodeKeyLabel = new JLabel("Decode Key");
        JTextPane bobDecodeKey = new JTextPane();
        JLabel bobMessageLabel = new JLabel("Message");
        JTextField bobMessageBox = new JTextField();
        JLabel bobMessagesLabel = new JLabel("Received messages");
        JTextArea bobMessages = new JTextArea();
        JButton bobEncryptButton = new JButton("Send");
        JButton bobDecryptButton = new JButton("Decrypt");

        aliceLabel.setBounds(20, 20, 100, 20);
        aliceLabel.setFont(bigFont);
        alicePanel.add(aliceLabel);

        aliceKeyLabel.setBounds(20, 55, 100, 20);
        aliceKeyLabel.setFont(middleFont);
        alicePanel.add(aliceKeyLabel);

        aliceCodeKey.setBounds(20, 75, 425, 245);
        aliceCodeKey.setFont(smallFont);
        aliceCodeKey.setBackground(Color.white);
        appendToPane(aliceCodeKey, formatKey(controller.getKeyController().getFirstHalf()), Color.black);
        aliceCodeKey.setEditable(false);
        alicePanel.add(aliceCodeKey);

        aliceDecodeKeyLabel.setBounds(20, 330, 100, 20);
        aliceDecodeKeyLabel.setFont(middleFont);
        alicePanel.add(aliceDecodeKeyLabel);

        aliceDecodeKey.setBounds(20, 350, 425, 245);
        aliceDecodeKey.setFont(smallFont);
        aliceDecodeKey.setBackground(Color.white);
        appendToPane(aliceDecodeKey, formatKey(controller.getKeyController().getSecondHalf()), Color.black);
        aliceDecodeKey.setEditable(false);
        alicePanel.add(aliceDecodeKey);

        aliceMessageLabel.setBounds(20, 610, 100, 20);
        aliceMessageLabel.setFont(middleFont);
        alicePanel.add(aliceMessageLabel);

        aliceMessageBox.setBounds(20, 630, 425, 30);
        aliceMessageBox.setFont(smallFont);
        aliceMessageBox.setBackground(Color.white);
        alicePanel.add(aliceMessageBox);

        aliceMessagesLabel.setBounds(470, 55, 400, 20);
        aliceMessagesLabel.setFont(middleFont);
        alicePanel.add(aliceMessagesLabel);

        aliceMessages.setBounds(470, 75, 425, 800);
        alicePanel.add(aliceMessages);

        aliceEncryptButton.setBounds(20, 680, 100, 30);
        aliceEncryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = aliceMessageBox.getText();
                message = convertToString(controller.encryptMessage(message, "Alice"));
                System.out.println(controller.getAliceEncodeOffset());
                aliceCodeKey.setEditable(true);
                aliceCodeKey.setText("");
                int[] key = controller.getKeyController().getFirstHalf();
                int[] consumedKey = new int[controller.getAliceEncodeOffset()];
                int[] restKey = new int[320 - controller.getAliceEncodeOffset()];
                System.arraycopy(key, 0, consumedKey, 0, controller.getAliceEncodeOffset());
                System.arraycopy(key, controller.getAliceEncodeOffset(), restKey, 0, 320 - controller.getAliceEncodeOffset());
                appendToPane(aliceCodeKey, formatKey(consumedKey), Color.orange);
                appendToPane(aliceCodeKey, formatKey(restKey), Color.black);
                aliceMessageBox.setText(message);
                aliceEncryptButton.setEnabled(false);
            }
        });
        alicePanel.add(aliceEncryptButton);

        aliceDecryptButton.setBounds(240, 680, 100, 30);
        aliceDecryptButton.setEnabled(false);
        alicePanel.add(aliceDecryptButton);

        bobLabel.setBounds(20, 20, 100, 20);
        bobLabel.setFont(bigFont);
        bobPanel.add(bobLabel);

        bobKeyLabel.setBounds(20, 55, 100, 20);
        bobKeyLabel.setFont(middleFont);
        bobPanel.add(bobKeyLabel);

        bobCodeKey.setBounds(20, 75, 425, 245);
        bobCodeKey.setFont(smallFont);
        bobCodeKey.setBackground(Color.white);
        appendToPane(bobCodeKey, formatKey(controller.getKeyController().getSecondHalf()), Color.black);
        bobCodeKey.setEditable(false);
        bobPanel.add(bobCodeKey);

        bobDecodeKeyLabel.setBounds(20, 330, 100, 20);
        bobDecodeKeyLabel.setFont(middleFont);
        bobPanel.add(bobDecodeKeyLabel);

        bobDecodeKey.setBounds(20, 350, 425, 245);
        bobDecodeKey.setFont(smallFont);
        bobDecodeKey.setBackground(Color.white);
        appendToPane(bobDecodeKey, formatKey(controller.getKeyController().getFirstHalf()), Color.black);
        bobDecodeKey.setEditable(false);
        bobPanel.add(bobDecodeKey);

        bobMessageLabel.setBounds(20, 610, 100, 20);
        bobMessageLabel.setFont(middleFont);
        bobPanel.add(bobMessageLabel);

        bobMessageBox.setBounds(20, 630, 425, 30);
        bobMessageBox.setFont(smallFont);
        bobMessageBox.setBackground(Color.white);
        bobPanel.add(bobMessageBox);

        bobMessagesLabel.setBounds(470, 55, 400, 20);
        bobMessagesLabel.setFont(middleFont);
        bobPanel.add(bobMessagesLabel);

        bobMessages.setBounds(470, 75, 425, 800);
        bobPanel.add(bobMessages);

        bobEncryptButton.setBounds(20, 680, 100, 30);
        bobEncryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = aliceMessageBox.getText();
                message = convertToString(controller.encryptMessage(message, "Alice"));
                aliceMessageBox.setText(message);
                aliceEncryptButton.setEnabled(false);
            }
        });
        bobPanel.add(bobEncryptButton);

        bobDecryptButton.setBounds(240, 680, 100, 30);
        bobDecryptButton.setEnabled(false);
        bobPanel.add(bobDecryptButton);

        frame.add(alicePanel);
        frame.add(bobPanel);

        frame.setVisible(true);
    }

    static void appendToPane(JTextPane tp, String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Verdana");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }
}
