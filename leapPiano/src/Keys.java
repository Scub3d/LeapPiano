import java.util.ArrayList;

public class Keys {
	private ArrayList<Key> KeyList;
	private int octaves;
	private String[] keyNames = { "C", "D", "E", "F", "G", "A", "B" };

	public Keys(int octaves, int canvasWidth) {
		int width = canvasWidth / (7 * octaves);
		KeyList = new ArrayList<Key>();
		this.octaves = octaves;
		for (int i = 0; i < octaves; i++) {
			int j = 0;
			for (String keyName : keyNames) {
				KeyList.add(new Key(keyName, i,width, 10 + width * (i * 7 + j)));
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

	

}