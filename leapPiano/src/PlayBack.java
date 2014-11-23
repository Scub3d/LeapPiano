import java.util.Iterator;
import java.util.LinkedList;

public class PlayBack {

	class MusicNode {

		private double time;
		private String note;

		public MusicNode(double time, String note) {
			this.time = time;
			this.note = note;
		}
		
		public String toString(){
			return time + " " + note + "\n";
		}
	}

	private LinkedList<MusicNode> music;

	public PlayBack() {
		this.music = new LinkedList<MusicNode>();
	}

	public void addNote(double time, String note) {
		this.music.add(new MusicNode(time, note));
	}
	
	public String toString(){
		StringBuilder s = new StringBuilder();
		for (MusicNode note : this.music){
			s.append(note);
		}
		return s.toString();
	}
	

}
