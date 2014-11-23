import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JComponent;

import com.leapmotion.leap.Finger;
import com.leapmotion.leap.FingerList;


public class pianoComponent extends JComponent {
	
	private FingerList currentFingers;
	private FingerList previousFingers;
	//private String[] allSounds = {"C3","D3", "E3", "F3", "G3", "A3", "B3", "C4","D4", "E4", "F4", "G4", "A4", "B4", "C5","D5", "E5", "F5", "G5", "A5", "B5"};
	ExecutorService exec;
	Map<String, FutureTask<?>> map = new ConcurrentHashMap<String, FutureTask<?>>();
	
	public pianoComponent() {
		this.exec = Executors.newFixedThreadPool(11);
	}
	
	public void updateData(FingerList currentFingers, FingerList previousFingers) {
		this.currentFingers = currentFingers;
		this.previousFingers = previousFingers;
		
		for(Finger finger : this.currentFingers) {
			System.out.println(finger.tipPosition());
			for(Finger prevFinger : this.previousFingers) {
				if(Math.abs(finger.tipPosition().getY() - prevFinger.tipPosition().getY()) > 35) {
					System.out.println("YOU PRESSED IT. HOORAY");
				}
			}
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if(this.currentFingers.count() == 0) {
			System.out.println("no fingers detected");
		} else {
			System.out.println("do something");
		}
    }
	
	public class playASound implements Runnable {
		private String sound;
		public playASound(String sound) {
			this.sound = sound;
		}

		@Override
		public void run() {
			try {
		        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(sound + ".wav").getAbsoluteFile());
		        Clip clip = AudioSystem.getClip();
		        clip.open(audioInputStream);
		        clip.loop(Clip.LOOP_CONTINUOUSLY);
		        clip.start();
			 } catch(Exception ex) {
		        System.out.println("Error with playing sound.");
		        ex.printStackTrace();
		    }	
		}	
	}
	
	public void playSound(String sound) {
		FutureTask<?> task = new FutureTask(new playASound(sound), null);
		map.put(sound, task);
		exec.execute(task);
    }
	
	public void stopSound(String sound) {
		FutureTask<?> task = map.get(sound);
		task.cancel(false);
	}
}


