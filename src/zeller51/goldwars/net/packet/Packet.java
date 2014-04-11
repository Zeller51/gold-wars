package zeller51.goldwars.net.packet;

import zeller51.goldwars.net.Client;
import zeller51.goldwars.net.ClientPacketHandler;
import zeller51.goldwars.net.ServerPacketHandler;

public abstract class Packet {

	public static enum PacketTypes {
		INVALID(-1), CONNECT(00), DISCONNECT(01), CREATEMAP(02), CREATEBLOCK(03), MAPSENT(
				04);

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

	public void writeData(ClientPacketHandler client) {
		client.sendData(data);
	}

	public void writeData(ServerPacketHandler server) {
		server.sendDataToClients(data);
	}

	public void writeDataTo(ServerPacketHandler server, Client client) {
		server.sendData(data, client.ipAddress, client.port);
	}

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
