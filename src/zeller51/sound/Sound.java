package zeller51.sound;

import java.applet.AudioClip;
import java.io.FileInputStream;

import sun.applet.AppletAudioClip;

public class Sound {

	private AudioClip clip;

	public Sound(String name) {
		FileInputStream fis;
		byte[] data = new byte[1048576];

		try {
			fis = new FileInputStream("res/" + name);
			fis.read(data);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			clip = new AppletAudioClip(data);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void play() {
		try {
			new Thread() {
				public void run() {
					clip.play();
				}
			}.start();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}