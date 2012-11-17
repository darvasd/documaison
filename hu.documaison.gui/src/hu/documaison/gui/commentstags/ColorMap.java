package hu.documaison.gui.commentstags;

import java.util.HashMap;
import java.util.Set;

public class ColorMap {

	private static ColorMap instance = null;
	private static HashMap<String, int[]> map;

	private ColorMap() {
		map = new HashMap<String, int[]>();
		map.put("Black", new int[] { 0, 0, 0 });
		map.put("Dark red", new int[] { 128, 0, 0 });
		map.put("Dark green", new int[] { 0, 128, 0 });
		map.put("Dark yellow", new int[] { 128, 128, 0 });
		map.put("Dark blue", new int[] { 0, 0, 128 });
		map.put("Dark magenta", new int[] { 128, 0, 128 });
		map.put("Dark cyan", new int[] { 0, 128, 128 });
		map.put("Dark gray", new int[] { 128, 128, 128 });
		map.put("Gray", new int[] { 192, 192, 192 });
		map.put("Red", new int[] { 255, 0, 0 });
		map.put("Green", new int[] { 0, 255, 0 });
		map.put("Yellow", new int[] { 255, 255, 0 });
		map.put("Blue", new int[] { 0, 0, 255 });
		map.put("Magenta", new int[] { 255, 0, 255 });
		map.put("Cyan", new int[] { 0, 255, 255 });
		map.put("White", new int[] { 255, 255, 255 });
	}

	public static ColorMap get() {
		if (instance == null) {
			instance = new ColorMap();
		}
		return instance;
	}

	public int[] getRGB(String name) {
		return map.get(name);
	}

	public Set<String> getColors() {
		return map.keySet();
	}
}
