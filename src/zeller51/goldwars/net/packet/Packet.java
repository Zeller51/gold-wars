package zeller51.goldwars.net.packet;

import zeller51.goldwars.net.ClientPacketHandler;
import zeller51.goldwars.net.ServerPacketHandler;

public abstract class Packet {

	public static enum PacketTypes {
		INVALID(-1), CONNECT(00), DISCONNECT(01);

		private int packetId;

		private PacketTypes(int packetId) {
			this.packetId = packetId;
		}

		public int getId() {
			return packetId;
		}
	}

	public byte packetId;
	protected byte[] data;

	public Packet(int packetId, byte[] data) {
		this.packetId = (byte) packetId;
		this.data = data;
	}

	public abstract void writeData(ClientPacketHandler client);
	
	public abstract void writeData(ServerPacketHandler server);

	public static PacketTypes lookupPacket(String id) {
		try {
			return lookupPacket(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return PacketTypes.INVALID;
		}
	}

	public static PacketTypes lookupPacket(int id) {
		for (PacketTypes p : PacketTypes.values()) {
			if (p.getId() == id) {
				return p;
			}
		}
		return PacketTypes.INVALID;
	}
}

