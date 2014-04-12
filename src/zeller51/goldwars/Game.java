package zeller51.goldwars;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import zeller51.goldwars.map.Map;
import zeller51.goldwars.net.ClientPacketHandler;
import zeller51.goldwars.net.packet.Packet00Connect;
import zeller51.goldwars.net.packet.Packet01Disconnect;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static final int UPS = 60;

	public static final String WINDOWNAME = "Gold Wars";
	public static final int GAMEWIDTH = 160;
	public static final int GAMEHEIGHT = 100;
	public static final int GAMESCALE = 4;

	public static final int CANVASWIDTH = GAMEWIDTH * GAMESCALE;
	public static final int CANVASHEIGHT = GAMEHEIGHT * GAMESCALE;

	private long lastFrame = System.currentTimeMillis();
	private long lastTime = System.nanoTime();
	private double unprocessed = 0;
	private double nsPerTick = 1000000000.0 / UPS;
	private int frames = 0;
	private int ticks = 0;
	private int dframes = 0;
	private int dticks = 0;
	private long lastTimer1 = System.currentTimeMillis();

	private boolean running = false;
	public boolean mapSent = false;

	private String serverIpAddress;
	private String username;

	private ClientPacketHandler packetHandler;

	private BufferedImage buffer = new BufferedImage(GAMEWIDTH, GAMEHEIGHT,
			BufferedImage.TYPE_INT_RGB);
	public Map map;

	public Game(String serverIpAddress, String username) {
		this.serverIpAddress = serverIpAddress;
		this.username = username;
		setPreferredSize(new Dimension(CANVASWIDTH, CANVASHEIGHT));
	}

	public void start() {
		init();

		running = true;
		new Thread(this).start();
	}

	private void init() {
		Assets.load();

		packetHandler = new ClientPacketHandler(this, serverIpAddress);
		packetHandler.start();

		Packet00Connect connect = new Packet00Connect(username);
		connect.writeData(packetHandler);
	}

	public void exit() {
		Packet01Disconnect disconnect = new Packet01Disconnect(username);
		disconnect.writeData(packetHandler);
		System.exit(0);
	}

	@Override
	public void run() {
		while (running) {
			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
			while (unprocessed >= 1) {
				ticks++;
				if (mapSent) {
					tick();
				} else {
					tickWaiting();
				}
				unprocessed -= 1;
				shouldRender = true;
			}

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (System.currentTimeMillis() - lastFrame >= 13) {
				frames++;
				if (mapSent) {
					render();
				} else {
					renderWaiting();
				}
				lastFrame = System.currentTimeMillis();
			}

			if (System.currentTimeMillis() - lastTimer1 > 1000) {
				lastTimer1 += 1000;
				dframes = frames;
				dticks = ticks;
				frames = 0;
				ticks = 0;
			}
		}

	}

	private void tick() {

	}

	private void tickWaiting() {

	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			requestFocus();
			return;
		}

		Graphics gf = bs.getDrawGraphics();
		Graphics g = buffer.getGraphics();

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GAMEWIDTH, GAMEHEIGHT);

		map.render(g, 0, 0);

		g.drawImage(Assets.font.createText("UPS: " + dticks), 1,
				GAMEHEIGHT - 7, null);
		g.drawImage(Assets.font.createText("FPS: " + dframes), 36,
				GAMEHEIGHT - 7, null);

		gf.drawImage(buffer.getScaledInstance(CANVASWIDTH, CANVASHEIGHT, 0), 0,
				0, null);
		bs.show();
	}

	private void renderWaiting() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			requestFocus();
			return;
		}

		Graphics gf = bs.getDrawGraphics();
		Graphics g = buffer.getGraphics();

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GAMEWIDTH, GAMEHEIGHT);

		g.drawImage(Assets.font.createText("Waiting for map!"), 2, 2, null);

		g.drawImage(Assets.font.createText("UPS: " + dticks), 1,
				GAMEHEIGHT - 7, null);
		g.drawImage(Assets.font.createText("FPS: " + dframes), 36,
				GAMEHEIGHT - 7, null);

		gf.drawImage(buffer.getScaledInstance(CANVASWIDTH, CANVASHEIGHT, 0), 0,
				0, null);
		bs.show();
	}

	public void createMap(int width, int height) {
		map = new Map(width, height);
	}

}
