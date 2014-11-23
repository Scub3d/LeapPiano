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
	private double[] keyLocations = {-262.5, -237.5, -212.5,-187.5,-162.5,-137.5,-112.5,-87.5,-62.5,-37.5,-12.5,12.5,37.5,62.5,87.5,112.5,137.5,162.5,187.5,212.5,237.5,262.5};
	private String[] allSounds = {"C3","D3", "E3", "F3", "G3", "A3", "B3", "C4","D4", "E4", "F4", "G4", "A4", "B4", "C5","D5", "E5", "F5", "G5", "A5", "B5"};
	private boolean[] keysPressedLastFrame = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	ExecutorService exec;
	Map<String, FutureTask<?>> map = new ConcurrentHashMap<String, FutureTask<?>>();
	static PlayBack record;
	
	public pianoComponent() {
		this.exec = Executors.newFixedThreadPool(11);
		this.record = new PlayBack();
	}
	
	public void updateData(FingerList currentFingers, FingerList previousFingers) {
		this.currentFingers = currentFingers;
		this.previousFingers = previousFingers;
		
		boolean[] keysPressedThisFrame = new boolean[21];
		
		for(Finger finger : this.currentFingers) {
				if(Math.abs(finger.tipPosition().getY() - 165) > 35) {
					for(int keyLocation = 0; keyLocation < this.keyLocations.length - 1; keyLocation++) {
						if(finger.tipPosition().getX() > this.keyLocations[keyLocation] && finger.tipPosition().getX() < this.keyLocations[keyLocation + 1]) {
							if(this.keysPressedLastFrame[keyLocation] == false) {
								keysPressedThisFrame[keyLocation] = true;
								playSound(this.allSounds[keyLocation]);
								//this.record.addNote(Main_old.clock.getTime(), (keyLocation - 1));//NOTE);
							}
							keysPressedThisFrame[keyLocation] = true;
						} else {
							keysPressedThisFrame[keyLocation] = false;
						}
					}
				}
			
		}
		for(int leftovers = 0; leftovers < this.keysPressedLastFrame.length; leftovers++) {
			if(keysPressedThisFrame[leftovers] == false && this.keysPressedLastFrame[leftovers] == true) {
				stopSound(this.allSounds[leftovers]);
			}
		}
		this.keysPressedLastFrame = keysPressedThisFrame;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
	
	public class playASound implements Runnable {
		private String sound;
		public playASound(String sound) {
			this.sound = sound;
		}

		@Override
		public void run() {
			try {
		        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("res/notes/Piano.pp." + sound + ".wav").getAbsoluteFile());
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
		//System.out.println("start: " + sound);
		map.put(sound, task);
		exec.execute(task);
    }
	
	public void stopSound(String sound) {
		FutureTask<?> task = map.get(sound);
		if(task != null) {
			//System.out.println("stop: " + sound);
			task.cancel(true);
		}
	}
}


