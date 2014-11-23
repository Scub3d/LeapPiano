import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;


public class timerButtonListener implements ActionListener{
	private MusicClock clock;
	private PlayBack record;

	public timerButtonListener(MusicClock clock, PlayBack record){
		this.clock = clock;
		this.record = record;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.record.addNote(this.clock.getTime(), "A");
	}
}
