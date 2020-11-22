package uni.todo;

import javafx.scene.paint.Color;
import org.apache.commons.lang3.SystemUtils;

public class Util {

	public static boolean isDarkColor(String color) {
		final Color c = Color.web(color);
		return 0.299 * (c.getRed() * 255) + 0.587 * (c.getGreen() * 255) + 0.114 * (c.getBlue() * 255) < 15d;
	}

	public static <T> T osSwitch(T windows, T linux, T macos) {
		if (SystemUtils.IS_OS_WINDOWS) return windows;
		if (SystemUtils.IS_OS_LINUX) return linux;
		if (SystemUtils.IS_OS_MAC_OSX) return macos;
		throw new IllegalArgumentException("Operating system wasn't detected");
	}

}