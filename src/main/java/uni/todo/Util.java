package uni.todo;

import javafx.scene.paint.Color;

public class Util {

	public static boolean isDarkColor(String color) {
		final Color c = Color.web(color);
		return 0.299 * (c.getRed() * 255) + 0.587 * (c.getGreen() * 255) + 0.114 * (c.getBlue() * 255) < 15d;
	}

}
