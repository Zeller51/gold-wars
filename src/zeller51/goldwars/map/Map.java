package zeller51.goldwars.map;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import zeller51.goldwars.entities.Entity;
import zeller51.goldwars.net.Client;
import zeller51.goldwars.net.ServerPacketHandler;
import zeller51.goldwars.net.packet.Packet;
import zeller51.goldwars.net.packet.Packet02CreateMap;
import zeller51.goldwars.net.packet.Packet03SendChunk;
import zeller51.goldwars.net.packet.Packet04MapSent;

public class Map {

	private final float CAVEFREQUENCY = 2.0f;
	private final float GOLDFREQUENCY = 5.0f;
	private final float CAVESIZE = 8.0f;
	private final float GOLDSIZE = 4.0f;
	private final float CAVEAMOUNT = 0.1f;
	private final float GOLDAMOUNT = 0.2f;

	public int width, height;
	public long seed;

	private List<Block> blocks = new ArrayList<Block>();
	private List<Entity> entities = new ArrayList<Entity>();

	public Map(int width, int height, long seed) {
		this.width = width;
		this.height = height;
	}

	public Map(int width, int height) {
		this(width, height, System.nanoTime());
	}

	public void update() {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update(this);
		}
	}

	public void generateMap() {
		Perlin pCave = new Perlin(seed);
		Perlin pGold = new Perlin(seed * 2);

		for (float h = 0; h < height; h++) {
			for (float w = 0; w < width; w++) {
				float cave = pCave.turbulence2(w / CAVESIZE, h / CAVESIZE,
						CAVEFREQUENCY);
				float gold = pGold.turbulence2(w / GOLDSIZE, h / GOLDSIZE,
						GOLDFREQUENCY);

				int block;

				// Create caves
				if (cave > CAVEAMOUNT) {
					block = Block.AIR;
				} else {
					block = Block.STONE;
				}

				// Create gold
				if ((block == Block.STONE) && (gold >= GOLDAMOUNT)) {
					block = Block.GOLD;
				}

				// Create bedrock surrounding the map
				if ((w == 0) || (w == width - 1)) {
					block = Block.BEDROCK;
				}
				if ((h == 0) || (h == height - 1)) {
					block = Block.BEDROCK;
				}

				// Create Block
				createBlock((int) w * 8, (int) h * 8, block);
			}
		}
	}

	private void createBlock(int x, int y, int block, int index) {
		switch (block) {
		case Block.AIR:
			blocks.add(index, new BlockAir(x, y));
			break;
		case Block.STONE:
			blocks.add(index, new BlockStone(x, y));
			break;
		case Block.GOLD:
			blocks.add(index, new BlockGold(x, y));
			break;
		case Block.BEDROCK:
			blocks.add(index, new BlockBedrock(x, y));
			break;
		}
	}

	public void createBlock(int x, int y, int block) {
		createBlock(x, y, block, blocks.size());
	}

	public Block getBlock(int x, int y) {
		for (int i = 0; i < blocks.size(); i++) {
			if (blocks.get(i).isBlock(x, y)) {
				return blocks.get(i);
			}
		}
		System.err.println("Unable to find block at X:" + x + " Y:" + y + "!");
		return null;
	}

	public int getBlockIndex(int x, int y) {
		for (int i = 0; i < blocks.size(); i++) {
			if (blocks.get(i).isBlock(x, y)) {
				return i;
			}
		}
		System.err.println("Unable to find block index at X:" + x + " Y:" + y
				+ "!");
		return -1;
	}

	public void changeBlock(int x, int y, int block) {
		int index = getBlockIndex(x, y);

		blocks.remove(index);
		createBlock(x, y, block, index);
	}

	public void render(Graphics g, int offsetX, int offsetY) {
		for (Block b : blocks) {
			b.render(g, offsetX, offsetY);
		}
	}

	public void sendToClient(Client client, ServerPacketHandler packetHandler) {
		String map = "";
		for (Block b : blocks) {
			map = map + (":" + b.getType() + ":" + b.x + ":" + b.y);
		}
		map = map.substring(1);
		splitAndSendMapPackets(map, packetHandler, client);
	}

	private void splitAndSendMapPackets(final String map,
			final ServerPacketHandler packetHandler, final Client client) {
		new Thread() {
			public void start() {
				new Packet02CreateMap(width, height).writeDataTo(packetHandler,
						client);
				int length = map.getBytes().length;
				int dataSize = Packet.SIZE - 3;
				int packets = (int) Math.ceil((double) length
						/ (double) dataSize);
				int dataToGo = length;
				for (int i = 0; i < packets; i++) {
					if (dataToGo >= dataSize) {
						new Packet03SendChunk(map.substring(i * dataSize, i
								* dataSize + dataSize)).writeDataTo(
								packetHandler, client);
						dataToGo -= dataSize;
					} else {
						new Packet03SendChunk(map.substring(i * dataSize, i
								* dataSize + dataToGo)).writeDataTo(
								packetHandler, client);
					}
					// Should be replaced
					// Pauses between sending packets so to not overload client
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				new Packet04MapSent().writeDataTo(packetHandler, client);
			}
		}.start();
	}

	public void addChunks(String mapData) {
		String[] data = mapData.split(":");

		for (int i = 0; i < data.length / 3; i++) {
			createBlock(Integer.parseInt(data[i * 3 + 1]),
					Integer.parseInt(data[i * 3 + 2]),
					Integer.parseInt(data[i * 3]));
		}
	}
}
