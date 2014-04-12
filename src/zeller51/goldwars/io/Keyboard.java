package zeller51.goldwars.io;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Keyboard implements KeyListener {

	public Keyboard(Component c) {
		c.addKeyListener(this);
	}

	private ArrayList<String> keys = new ArrayList<String>();
	private ArrayList<Boolean> clicked = new ArrayList<Boolean>();

	public void tick() {
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
			keys.add(KeyEvent.getKeyText(e.getKeyCode()));
			clicked.add(new Boolean(true));
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		clicked.remove(keys.indexOf(KeyEvent.getKeyText(e.getKeyCode())));
		keys.remove(KeyEvent.getKeyText(e.getKeyCode()));

	}

	public boolean down(String key) {
		for (int i = 0; i < keys.size(); i++) {
			if (key.contentEquals(keys.get(i))) {
				return true;
			}
		}
		return false;
	}

	public boolean clicked(String key) {
		for (int i = 0; i < keys.size(); i++) {
			if (key.contentEquals(keys.get(i))) {
				return clicked.get(i).booleanValue();
			}
		}
		return false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
