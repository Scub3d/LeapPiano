import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.text.html.HTMLDocument.Iterator;


public class populateListListener implements ActionListener{
	private PlayBack record;

	public populateListListener(PlayBack record){
		this.record = record;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		 String path = "C:/Users/efronbs/git/LeapPiano/leapPiano/res/RecordedSound";
		 File file = new File(path);
		 try {

		        Scanner sc = new Scanner(file);

		        while (sc.hasNext()) {
		            Double time = Double.parseDouble(sc.next());
		            String note = sc.next();
		            this.record.addNote(time, note);
		        }
		        sc.close();
		        System.out.println(this.record.toString());
		    } 
		    catch (FileNotFoundException e) {
		        e.printStackTrace();
		    }

	}
}