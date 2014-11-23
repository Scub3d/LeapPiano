import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

import javax.swing.text.html.HTMLDocument.Iterator;


public class printListener implements ActionListener{
	private PlayBack record;

	public printListener(PlayBack record){
		this.record = record;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		 String path = "C:/GitHub/LeapPiano/leapPiano/res/RecordedSound";
		 try {
			FileWriter write = new FileWriter(path, false);
			write.write(this.record.toString());
			write.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("ERROR");
		
		}

	}
}
