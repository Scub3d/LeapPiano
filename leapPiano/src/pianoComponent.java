import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
	Map<String, playASound> map = new ConcurrentHashMap<String, playASound>();
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
			for(int t = 0; t < this.keyLocations.length - 1; t++) {
				if(finger.tipPosition().getX() > this.keyLocations[t] && finger.tipPosition().getX() < this.keyLocations[t + 1]) {
					PianoUI.keys.getKeyBindings().get(this.allSounds[t]).setHover(true);
					PianoUI.keys.getKeyBindings().get(this.allSounds[t]).setPressed(false);
					PianoUI.instance.JPanelReDraw();
				} else {
					System.out.println(t);
					System.out.println(PianoUI.keys.getKeyBindings().get(this.allSounds[t]));
					PianoUI.keys.getKeyBindings().get(this.allSounds[t]).setHover(false);
					PianoUI.instance.JPanelReDraw();
				}
			}
			for(Finger prevFinger : this.previousFingers) {
				if(finger.tipPosition().getY() - prevFinger.tipPosition().getY() < 0 && Math.abs(finger.tipPosition().getY() - prevFinger.tipPosition().getY()) > 20) {
					for(int keyLocation = 0; keyLocation < this.keyLocations.length - 1; keyLocation++) {
						if(finger.tipPosition().getX() > this.keyLocations[keyLocation] && finger.tipPosition().getX() < this.keyLocations[keyLocation + 1]) {
							if(this.keysPressedLastFrame[keyLocation] == false) {
								keysPressedThisFrame[keyLocation] = true;
								playSound(this.allSounds[keyLocation]);
								PianoUI.keys.getKeyBindings().get(this.allSounds[keyLocation]).setHover(false);
								PianoUI.keys.getKeyBindings().get(this.allSounds[keyLocation]).setPressed(true);
								PianoUI.instance.JPanelReDraw();
								
								//this.record.addNote(Main_old.clock.getTime(), (keyLocation - 1));//NOTE);
							}
							keysPressedThisFrame[keyLocation] = true;
						} else {
							keysPressedThisFrame[keyLocation] = false;
						}
					}
				}
			}
		}
		for(int leftovers = 0; leftovers < this.keysPressedLastFrame.length; leftovers++) {
			if(keysPressedThisFrame[leftovers] == false && this.keysPressedLastFrame[leftovers] == true) {
				stopSound(this.allSounds[leftovers]);
				PianoUI.keys.getKeyBindings().get(this.allSounds[leftovers]).setHover(false);
				PianoUI.keys.getKeyBindings().get(this.allSounds[leftovers]).setPressed(false);
				PianoUI.instance.JPanelReDraw();
			}
		}
		this.keysPressedLastFrame = keysPressedThisFrame;
	}
	
	
	
	public class playASound implements Runnable {
		private String sound;
		private Clip clip;
		
		public playASound(String sound) {
			this.sound = sound;
		}

		public void stop() {
			if(clip!=null)
				clip.stop();
		}
		
		@Override
		public void run() {
			try {
		        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("res/notes/Piano.pp." + sound + ".wav").getAbsoluteFile());
		        clip = AudioSystem.getClip();
		        clip.open(audioInputStream);
		        clip.start();
			 } catch(Exception ex) {
		        System.out.println("Error with playing sound.");
		        ex.printStackTrace();
		    }	
		}	
	}
	
	public void playSound(String sound) { 
		playASound playSound = map.get(sound);
		if (playSound == null) {
			playSound = new playASound(sound);
			map.put(sound, playSound);
			exec.execute(playSound);
		}
		
		
		//FutureTask<?> task = new FutureTask(new playASound(sound), null);
		//System.out.println("start: " + sound);
		//map.put(sound, task);
		//exec.execute(task);
    }
	
	public void stopSound(String sound) {
		playASound playSound = map.remove(sound);
		if (playSound != null) {
			playSound.stop();
		}
		
		//FutureTask<?> task = map.get(sound);
		//if(task != null) {
	//	/	//System.out.println("stop: " + sound);
	//		task.cancel(true);
		//}
	}
}


