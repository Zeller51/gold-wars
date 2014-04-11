package zeller51.goldwars;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class GoldWars {

	private static Game game = new Game();
	private static WindowListener windowListener = new WindowListener() {

		@Override
		public void windowOpened(WindowEvent e) {
		}

		@Override
		public void windowIconified(WindowEvent e) {
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
		}

		@Override
		public void windowClosing(WindowEvent e) {
			game.exit();
		}

		@Override
		public void windowClosed(WindowEvent e) {
		}

		@Override
		public void windowActivated(WindowEvent e) {
		}
	};

	public static void main(String[] args) {
		JFrame frame = new JFrame(Game.WINDOWNAME);
		frame.setMinimumSize(game.getPreferredSize());
		frame.setPreferredSize(game.getPreferredSize());
		frame.setMaximumSize(game.getPreferredSize());
		frame.setUndecorated(true);
		frame.setLocationRelativeTo(null);
		frame.add(game);
		frame.addWindowListener(windowListener);
		frame.setVisible(true);

		game.start();
	}

}
