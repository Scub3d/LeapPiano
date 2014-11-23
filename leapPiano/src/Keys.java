import java.util.ArrayList;
import java.util.HashMap;

public class Keys {
	private ArrayList<Key> KeyList;
	private HashMap<String,Key> KeyBindings;
	private int octaves;
	private String[] keyNames = { "C", "D", "E", "F", "G", "A", "B" };

	public Keys(int octaves, int canvasWidth) {
		int width = canvasWidth / (7 * octaves);
		KeyList = new ArrayList<Key>();
		KeyBindings = new HashMap<String,Key>();
		this.octaves = octaves;
		for (int i = 3; i < octaves + 3; i++) {
			int j = 0;
			for (String keyName : keyNames) {
				Key newKey = new Key(keyName, i,width, 10 + width * (i * 7 + j));
				KeyList.add(newKey);
				KeyBindings.put(keyName+i, newKey);
				j++;
			}
		}

	}

	public ArrayList<Key> getKeyList() {
		return KeyList;
	}

	public int getOctaves() {
		return octaves;
	}

	public HashMap<String, Key> getKeyBindings(){
		return KeyBindings;
	}

}