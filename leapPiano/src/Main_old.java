
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;

import com.leapmotion.leap.Controller;

public class Main_old {
	public final static MusicClock clock = new MusicClock();
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
    					clock.startClock();
    					
    					controller.addListener(listener);
    				}
    			});
    			ActionListener exit = new ActionListener() {
    				public void actionPerformed(ActionEvent e) {
    					if(e.getID() == KeyEvent.KEY_PRESSED) {
    						clock.stopClock();
    						String path = "C:/GitHub/LeapPiano/leapPiano/res/RecordedSound";
    						 try {
    							FileWriter write = new FileWriter(path, false);
    							write.write(pianoComponent.record.toString());
    							write.close();
    						} catch (IOException exception) {
    							// TODO Auto-generated catch block
    							exception.printStackTrace();
    							System.out.println("ERROR");
    						
    						}
    						controller.removeListener(listener);
    					}
    				}
    			};
	}
}
