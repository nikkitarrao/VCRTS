import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ClickListener implements ActionListener {
    private JFrame parentFrame;

    // Constructor to initialize the parent frame
    public ClickListener(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();

        if (command.equals("Create an Account")) {
            System.out.print("Create Account");
        } else if (command.equals("Sign in")) {
        	 System.out.print("Sign in");
        }
    }
}

