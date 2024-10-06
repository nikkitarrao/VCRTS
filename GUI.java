import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {

	public static void main(String[] args) throws FileNotFoundException {
		
		 JFrame frame = new JFrame();
	      
	      JButton button = new JButton("Click me!");
	      JLabel label = new JLabel("Hello, World!");

	      JPanel panel = new JPanel();
	      panel.add(button);
	      panel.add(label);
	      frame.add(panel);

	      final int FRAME_WIDTH = 400;
	      final int FRAME_HEIGHT = 500;
	      frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
	      frame.setTitle("Vehicular Cloud Real Time System");
	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      frame.setVisible(true);
	      
	      //printing out the information
	      File file = new File("data.txt");
			
			 Scanner input = new Scanner (file);
			
			while (input.hasNextDouble()) {
	            double number = input.nextDouble();
	            System.out.println(number);
	        }
			
			
			PrintStream output = new PrintStream(new File("out.txt"));

	}

}
