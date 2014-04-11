package zeller51.goldwars.net;

import zeller51.goldwars.map.Map;

public class Server implements Runnable {

	public static final int UPS = 60;
	public static final int PORT = 1337;

	private long lastTime = System.nanoTime();
	private double unprocessed = 0;
	private double nsPerTick = 1000000000.0 / UPS;
	private int frames = 0;
	private int ticks = 0;
	private int dframes = 0;
	private int dticks = 0;
	private long lastTimer1 = System.currentTimeMillis();

	private Map map;
	private ServerPacketHandler packerHandler;

	public static void main(String[] args) {
		Server server = new Server();
		server.start();
	}

	private void start() {
		init();
		new Thread(this).start();
	}

	@Override
	public void run() {
		while (true) {
			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;
			while (unprocessed >= 1) {
				ticks++;
				tick();
				unprocessed -= 1;
			}

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
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

	private void init() {
		map = new Map(48, 48);
		map.generateMap();

		packerHandler = new ServerPacketHandler(this, PORT);
		packerHandler.start();
	}

	private void tick() {
		map.update();
	}

	public void removePlayer(String username) {
		// TODO Auto-generated method stub

	}
}
