package zeller51.goldwars.io;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class Keyboard implements KeyListener {

	private List<Key> keys = new ArrayList<Key>();

	public Key up = new Key(keys);
	public Key down = new Key(keys);
	public Key left = new Key(keys);
	public Key right = new Key(keys);

	public Keyboard(Component c) {
		c.addKeyListener(this);
	}

	public void update() {
		for (int i = 0; i < keys.size(); i++) {
			keys.get(i).update();
		}
	}

	private void toggle(boolean state, int keyCode) {
		if (keyCode == KeyEvent.VK_W)
			up.toggle(state);
		if (keyCode == KeyEvent.VK_S)
			down.toggle(state);
		if (keyCode == KeyEvent.VK_A)
			left.toggle(state);
		if (keyCode == KeyEvent.VK_D)
			right.toggle(state);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		toggle(true, e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		toggle(false, e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	private class Key {
		public boolean down = false;
		public boolean clicked = false;

		public Key(List<Key> keys) {
			keys.add(this);
		}

		public void toggle(boolean state) {
			down = state;
			clicked = state;
		}

		public void update() {
			if (down && clicked) {
				clicked = false;
			}
		}
	}

}
