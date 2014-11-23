import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;


public class startButtonListener implements ActionListener {
	private JButton button;
	private MusicClock clock;
	
	startButtonListener(JButton button, MusicClock clock){
		this.button = button;
		this.clock = clock;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String text = this.button.getText();
		if (text.equals("Start")){
			this.button.setText("Stop");
			this.clock.startClock();
		} else {
			this.button.setText("Start");
			this.clock.stopClock();
		}
		
	}
	
	

}
