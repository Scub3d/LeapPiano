
public class Key {
		public Key(String name, int octave, int width, int xVal) {
			this.name = name;
			this.octave = octave;
			this.xVal = xVal;
			this.width = width;
		}

		private String name;
		private int octave;
		private int xVal;
		private int width;
		private boolean hovering = false;
		private boolean pressed = false;
		public boolean hover(){
			return hovering;
		}
		public boolean press(){
			return pressed;
		}
		public void setHover(boolean newHover){
			hovering = newHover;
		}
		public void setPressed(boolean newPress) {
			pressed = newPress;
		}
		public String getName(){
			return name;
		}
		public int getOctave(){
			return octave;
		}
		public int getXVal(){
			return xVal;
		}
		public int getWidth(){
			return width;
		}
	}