package zeller51.goldwars;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import zeller51.goldwars.io.Keyboard;
import zeller51.goldwars.map.Map;
import zeller51.goldwars.net.ClientPacketHandler;
import zeller51.goldwars.net.packet.Packet00Connect;
import zeller51.goldwars.net.packet.Packet01Disconnect;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	private static enum GameState {
		LOADING, LOBBY, CONNECTING, GAME
	}

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

	private GameState gameState = GameState.LOADING;

	private int lobbySelection = 0;

	private String lobbyIp = "";
	private String lobbyUsername = "";
	private boolean lobbyBlink = false;
	private int lobbyBlinkTimer = 0;

	private ClientPacketHandler packetHandler;

	private BufferedImage buffer = new BufferedImage(GAMEWIDTH, GAMEHEIGHT,
			BufferedImage.TYPE_INT_RGB);
	public Map map;

	public Game() {
		setPreferredSize(new Dimension(CANVASWIDTH, CANVASHEIGHT));
	}

	public void start() {
		init();

		gameState = GameState.LOBBY;
		running = true;
		new Thread(this).start();
	}

	private void init() {
		Assets.load();
		addKeyListener(new Keyboard());
	}

	public void exit() {
		if (gameState == GameState.CONNECTING || gameState == GameState.GAME)
			disconnect();
		System.exit(0);
	}

	@Override
	public void run() {
		while (running) {
			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;
			while (unprocessed >= 1) {
				ticks++;
				tick();
				unprocessed -= 1;
			}

			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (System.currentTimeMillis() - lastFrame >= 13) {
				frames++;
				render();
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
		switch (gameState) {
		case LOADING:
			break;
		case LOBBY:
			tickLobby();
			break;
		case CONNECTING:
			tickConnecting();
			break;
		case GAME:
			tickGame();
			break;
		}
		Keyboard.tick();
	}

	private void tickConnecting() {
		if (mapSent) {
			gameState = GameState.GAME;
		}
	}

	private void tickGame() {

	}

	private void tickLobby() {
		lobbyBlinkTimer++;

		if (lobbyBlinkTimer > 40) {
			lobbyBlinkTimer -= 40;
		}

		if (lobbyBlinkTimer >= 20) {
			lobbyBlink = true;
		} else {
			lobbyBlink = false;
		}

		if (Keyboard.clicked("UP")) {
			lobbySelection--;
		}

		if (Keyboard.clicked("DOWN")) {
			lobbySelection++;
		}

		if (lobbySelection > 2) {
			lobbySelection -= 3;
		}

		if (lobbySelection < 0) {
			lobbySelection += 3;
		}

		switch (lobbySelection) {
		case 0:
			if (Keyboard.clicked("0"))
				lobbyIp = lobbyIp + "0";
			if (Keyboard.clicked("1"))
				lobbyIp = lobbyIp + "1";
			if (Keyboard.clicked("2"))
				lobbyIp = lobbyIp + "2";
			if (Keyboard.clicked("3"))
				lobbyIp = lobbyIp + "3";
			if (Keyboard.clicked("4"))
				lobbyIp = lobbyIp + "4";
			if (Keyboard.clicked("5"))
				lobbyIp = lobbyIp + "5";
			if (Keyboard.clicked("6"))
				lobbyIp = lobbyIp + "6";
			if (Keyboard.clicked("7"))
				lobbyIp = lobbyIp + "7";
			if (Keyboard.clicked("8"))
				lobbyIp = lobbyIp + "8";
			if (Keyboard.clicked("9"))
				lobbyIp = lobbyIp + "9";
			if (Keyboard.clicked("PERIOD"))
				lobbyIp = lobbyIp + ".";
			if (Keyboard.clicked("BACKSPACE")) {
				if (lobbyIp.length() > 0) {
					lobbyIp = lobbyIp.substring(0, lobbyIp.length() - 1);
				}
			}
			break;
		case 1:
			if (Keyboard.clicked("0"))
				lobbyUsername = lobbyUsername + "0";
			if (Keyboard.clicked("1"))
				lobbyUsername = lobbyUsername + "1";
			if (Keyboard.clicked("2"))
				lobbyUsername = lobbyUsername + "2";
			if (Keyboard.clicked("3"))
				lobbyUsername = lobbyUsername + "3";
			if (Keyboard.clicked("4"))
				lobbyUsername = lobbyUsername + "4";
			if (Keyboard.clicked("5"))
				lobbyUsername = lobbyUsername + "5";
			if (Keyboard.clicked("6"))
				lobbyUsername = lobbyUsername + "6";
			if (Keyboard.clicked("7"))
				lobbyUsername = lobbyUsername + "7";
			if (Keyboard.clicked("8"))
				lobbyUsername = lobbyUsername + "8";
			if (Keyboard.clicked("9"))
				lobbyUsername = lobbyUsername + "9";
			if (Keyboard.clicked("."))
				lobbyUsername = lobbyUsername + ".";
			if (Keyboard.clicked("a"))
				lobbyUsername = lobbyUsername + "a";
			if (Keyboard.clicked("b"))
				lobbyUsername = lobbyUsername + "b";
			if (Keyboard.clicked("c"))
				lobbyUsername = lobbyUsername + "c";
			if (Keyboard.clicked("d"))
				lobbyUsername = lobbyUsername + "d";
			if (Keyboard.clicked("e"))
				lobbyUsername = lobbyUsername + "e";
			if (Keyboard.clicked("f"))
				lobbyUsername = lobbyUsername + "f";
			if (Keyboard.clicked("g"))
				lobbyUsername = lobbyUsername + "g";
			if (Keyboard.clicked("h"))
				lobbyUsername = lobbyUsername + "h";
			if (Keyboard.clicked("i"))
				lobbyUsername = lobbyUsername + "i";
			if (Keyboard.clicked("j"))
				lobbyUsername = lobbyUsername + "j";
			if (Keyboard.clicked("k"))
				lobbyUsername = lobbyUsername + "k";
			if (Keyboard.clicked("l"))
				lobbyUsername = lobbyUsername + "l";
			if (Keyboard.clicked("m"))
				lobbyUsername = lobbyUsername + "m";
			if (Keyboard.clicked("n"))
				lobbyUsername = lobbyUsername + "n";
			if (Keyboard.clicked("o"))
				lobbyUsername = lobbyUsername + "o";
			if (Keyboard.clicked("p"))
				lobbyUsername = lobbyUsername + "p";
			if (Keyboard.clicked("q"))
				lobbyUsername = lobbyUsername + "q";
			if (Keyboard.clicked("r"))
				lobbyUsername = lobbyUsername + "r";
			if (Keyboard.clicked("s"))
				lobbyUsername = lobbyUsername + "s";
			if (Keyboard.clicked("t"))
				lobbyUsername = lobbyUsername + "t";
			if (Keyboard.clicked("u"))
				lobbyUsername = lobbyUsername + "u";
			if (Keyboard.clicked("v"))
				lobbyUsername = lobbyUsername + "v";
			if (Keyboard.clicked("w"))
				lobbyUsername = lobbyUsername + "w";
			if (Keyboard.clicked("x"))
				lobbyUsername = lobbyUsername + "x";
			if (Keyboard.clicked("y"))
				lobbyUsername = lobbyUsername + "y";
			if (Keyboard.clicked("z"))
				lobbyUsername = lobbyUsername + "z";
			if (Keyboard.clicked("BACKSPACE")) {
				if (lobbyUsername.length() > 0) {
					lobbyUsername = lobbyUsername.substring(0,
							lobbyUsername.length() - 1);
				}
			}
			break;
		case 2:
			if (Keyboard.clicked("ENTER")) {
				connect();
			}
			break;
		}
	}

	private void connect() {
		gameState = GameState.CONNECTING;
		packetHandler = new ClientPacketHandler(this, lobbyIp);
		packetHandler.start();

		Packet00Connect connect = new Packet00Connect(lobbyUsername);
		connect.writeData(packetHandler);
	}

	private void disconnect() {
		Packet01Disconnect disconnect = new Packet01Disconnect(lobbyUsername);
		disconnect.writeData(packetHandler);
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

		switch (gameState) {
		case LOADING:
			break;
		case LOBBY:
			renderLobby(g);
			break;
		case CONNECTING:
			renderConnecting(g);
			break;
		case GAME:
			renderGame(g);
			break;
		}

		g.drawImage(Assets.font.createText("UPS: " + dticks), 1,
				GAMEHEIGHT - 7, null);
		g.drawImage(Assets.font.createText("FPS: " + dframes), 36,
				GAMEHEIGHT - 7, null);

		gf.drawImage(buffer.getScaledInstance(CANVASWIDTH, CANVASHEIGHT, 0), 0,
				0, null);
		bs.show();
	}

	private void renderLobby(Graphics g) {
		g.setColor(Color.WHITE);
		if (lobbyBlink) {
			switch (lobbySelection) {
			case 0:
				g.fillRect(2, 3, 5, 5);
				break;
			case 1:
				g.fillRect(2, 11, 5, 5);
				break;
			case 2:
				g.fillRect(2, 19, 5, 5);
				break;
			}
		}

		g.drawImage(Assets.font.createText("ip:" + lobbyIp), 9, 2, null);

		g.drawImage(Assets.font.createText("username:" + lobbyUsername), 9, 10,
				null);

		g.drawImage(Assets.font.createText("connect"), 9, 18, null);
	}

	private void renderConnecting(Graphics g) {
		g.drawImage(Assets.font.createText("connecting..."), 9, 2, null);
	}

	private void renderGame(Graphics g) {
		map.render(g, 0, 0);
	}

	public void createMap(int width, int height) {
		map = new Map(width, height);
	}

}
