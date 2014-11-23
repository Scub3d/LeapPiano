import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class PianoUI extends JFrame {
	public int octaveNum = 3;
	JPanel pianoPanel;
	JLabel octaveTitle;
	JTextField octaves;
	JButton refreshPiano;
	JComponent pianoCanvas;
	static Keys keys;

	static PianoUI instance;
	
	public PianoUI()
	{
		instance = this;
		final int canvasWidth = 600;
		this.keys = new Keys(octaveNum,canvasWidth);
		final ArrayList<Key> keysList = keys.getKeyList();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(30, 30, 650, 300);
		pianoPanel = new JPanel() {
			
			int height = 200;
			int width = canvasWidth/(7*octaveNum);
			public void paintComponent(Graphics g)
			{
				System.out.println("HERE");
				g.setColor(Color.BLACK);
				int ystart = 50;
				// drawRect( x, y, width, height)
				for(Key note:keysList) {
					//if(!note.hover()&&!note.press()){
						g.drawRect(note.getXVal(), ystart, note.getWidth(), height);
						g.setColor(Color.WHITE);
						g.fillRect(note.getXVal() + 1, ystart + 1, note.getWidth() -2, height -2);
						g.setColor(Color.BLACK);
					//}
					if(note.press()){
						g.setColor(Color.RED);
						g.fillRect(note.getXVal(), ystart, note.getWidth(), height);
						g.setColor(Color.black);
					}
					else if(note.hover()){
						g.setColor(Color.YELLOW);
						g.fillRect(note.getXVal(), ystart, note.getWidth(), height);
						g.setColor(Color.black);
					}
					g.drawString(note.getName()+note.getOctave(),note.getXVal()+(note.getWidth()/2)-8,height-15);

				}
			}
			
		};
		

		octaveTitle = new JLabel("# of Octaves");
		octaves = new JTextField();
		refreshPiano = new JButton("Update Keyboard");
		
//		pianoPanel.add(octaveTitle);
//		pianoPanel.add(octaves);
//		pianoPanel.add(refreshPiano);
	}
	
	public void JPanelReDraw(){
		System.out.println("HELLO");
		pianoPanel.repaint();
	}
	
}
