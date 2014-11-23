
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.*;

import com.leapmotion.leap.Controller;

public class Main_old {
	
	public static void main(String[] args) {

    			final JFrame mainFrame = new JFrame();
    			mainFrame.setSize(400, 300);
    			mainFrame.setVisible(true);
    			JButton startInterfaceButton = new JButton("Start Jamming");
    			
    			JPanel buttonPanel = new JPanel();
    			buttonPanel.add(startInterfaceButton);
    			
    			mainFrame.add(buttonPanel, BorderLayout.CENTER);
    			
    			final leapListener listener = new leapListener();
				final Controller controller = new Controller();
    			
    			startInterfaceButton.addActionListener(new ActionListener() {
    				@Override
    				public void actionPerformed(ActionEvent e) {
    					mainFrame.setVisible(false);
    					
    					controller.addListener(listener);
    				}
    			});
    			ActionListener exit = new ActionListener() {
    				public void actionPerformed(ActionEvent e) {
    					if(e.getID() == KeyEvent.KEY_PRESSED) {
    						controller.removeListener(listener);
    					}
    				}
    			};
	}
}
