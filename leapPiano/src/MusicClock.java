
public class MusicClock {

	private double startTime = 0.0, endTime = 0.0, currentTime = 0.0;
	private boolean running = false;
	
	public MusicClock(){
		
	}
	
	public void startClock(){
		this.startTime = System.nanoTime();
		this.running = true;
	}
	
	public void stopClock(){
		this.endTime = System.nanoTime();
		this.running = false;
	}
	
	public double getTime(){
		if (this.running){
			return (System.nanoTime() - this.startTime) / 10000;
		}
		return (this.endTime - this.startTime) / 10000;
	}
}
