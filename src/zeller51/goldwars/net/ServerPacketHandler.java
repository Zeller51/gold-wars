package zeller51.goldwars.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import zeller51.goldwars.net.packet.Packet;
import zeller51.goldwars.net.packet.Packet.PacketTypes;
import zeller51.goldwars.net.packet.Packet00Connect;
import zeller51.goldwars.net.packet.Packet01Disconnect;

public class ServerPacketHandler extends Thread {

	private DatagramSocket socket;
	private List<Client> clients = new ArrayList<Client>();

	private Server server;

	public ServerPacketHandler(Server server, int port) {
		this.server = server;

		try {
			this.socket = new DatagramSocket(port);
		} catch (SocketException e) {
			System.err.println("Unable to bind socket on port " + port + "!");
			e.printStackTrace();
		}

	}

	public void run() {
		while (true) {
			byte[] data = new byte[16384];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}

			parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
		}

	}

	private void parsePacket(byte[] data, InetAddress address, int port) {
		String message = new String(data).trim();
		data = message.getBytes();
		PacketTypes type = Packet.lookupPacket(message.substring(0, 2));
		Packet packet;
		switch (type) {
		default:
		case INVALID:
			break;
		case CONNECT:
			packet = new Packet00Connect(data);
			addConnection(((Packet00Connect) packet), address.getHostAddress(),
					port);
			break;
		case DISCONNECT:;
			packet = new Packet01Disconnect(data);
			removeConnection(((Packet01Disconnect) packet));
			break;
		}
	}

	private void addConnection(Packet00Connect packet, String hostAddress,
			int port) {
		Client client = new Client(hostAddress, port, packet.getUsername());
		clients.add(client);
		System.out.println(((Packet00Connect) packet).getUsername()
				+ " has connected from " + hostAddress + " on " + port + ".");
		server.map.sendToClient(client, this);
	}

	private void removeConnection(Packet01Disconnect packet) {
		server.removePlayer(packet.getUsername());
		System.out.println(((Packet01Disconnect) packet).getUsername()
				+ " has disconnected.");
	}

	public void sendDataToClients(byte[] data) {
		for (Client c : clients) {
			sendData(data, c.ipAddress, c.port);
		}
	}

	public void sendData(byte[] data, InetAddress ipAdress, int port) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAdress,
				port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
