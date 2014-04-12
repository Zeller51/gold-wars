package zeller51.goldwars.io;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Keyboard implements KeyListener {

	private static ArrayList<String> keys = new ArrayList<String>();
	private static ArrayList<Boolean> clicked = new ArrayList<Boolean>();

	public static void tick() {
		for (int i = 0; i < clicked.size(); i++) {
			if (clicked.get(i).booleanValue() == true) {
				clicked.remove(i);
				clicked.add(i, new Boolean(false));
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		boolean dupe = false;
		for (int i = 0; i < keys.size(); i++) {
			if (keys.get(i).contentEquals(KeyEvent.getKeyText(e.getKeyCode()))) {
				dupe = true;
			}
		}
		if (!dupe) {
			System.out.println("P:" + KeyEvent.getKeyText(e.getKeyCode()));
			keys.add(KeyEvent.getKeyText(e.getKeyCode()));
			clicked.add(new Boolean(true));
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println("R:" + KeyEvent.getKeyText(e.getKeyCode()));
		clicked.remove(keys.indexOf(KeyEvent.getKeyText(e.getKeyCode())));
		keys.remove(KeyEvent.getKeyText(e.getKeyCode()));

	}

	public static boolean down(String key) {
		if (keys.size() > 0) {
			for (int i = 0; i < keys.size(); i++) {
				if (key.toUpperCase().contentEquals(keys.get(i).toUpperCase())) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean clicked(String key) {
		if (keys.size() > 0) {
			for (int i = 0; i < keys.size(); i++) {
				if (key.toUpperCase().contentEquals(keys.get(i).toUpperCase())) {
					return clicked.get(i).booleanValue();
				}
			}
		}
		return false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
