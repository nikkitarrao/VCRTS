import javax.swing.*;
import java.util.*;
import java.io.*;

public class GUI {
    private static JFrame frame;
    private static JPanel mainPanel;
    private static ClickListener clickListener;

    public static void main(String[] args) throws FileNotFoundException {
        frame = new JFrame();
        mainPanel = new JPanel();
     
        JLabel label = new JLabel("Welcome to the Vehicular Cloud Real-Time System Console");
        JButton button1 = new JButton("Create an Account");
        JButton button2 = new JButton("Sign in");
       
        mainPanel.add(label);
        mainPanel.add(Box.createVerticalGlue()); //leaves a vertical space
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(button1);
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(button2);
        frame.add(mainPanel);

        final int FRAME_WIDTH = 400;
        final int FRAME_HEIGHT = 500;
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setTitle("Vehicular Cloud Real Time System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
     
        clickListenerPayal = new ClickListenerPayal(frame);
        button1.addActionListener(clickListener);
        button2.addActionListener(clickListener);
     
        //printing out the information
        File file = new File("out.txt");
        Scanner input = new Scanner(file);
        while (input.hasNextDouble()) {
            double number = input.nextDouble();
            System.out.println(number);
        }
        input.close();

        PrintStream output = new PrintStream(new File("out.txt"));
    }

    public static void showPanel(String panelName) {
        mainPanel.removeAll();
        if ("CreateAccount".equals(panelName)) {
            mainPanel.add(clickListener.createAccount());
        } else if ("User2".equals(panelName)) {
            mainPanel.add(clickListener.user2());
        }
        // Add other panel cases as needed
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}