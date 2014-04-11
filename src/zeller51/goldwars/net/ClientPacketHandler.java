package zeller51.goldwars.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import zeller51.goldwars.Game;
import zeller51.goldwars.net.packet.Packet;
import zeller51.goldwars.net.packet.Packet01Disconnect;
import zeller51.goldwars.net.packet.Packet03CreateBlock;
import zeller51.goldwars.net.packet.Packet.PacketTypes;
import zeller51.goldwars.net.packet.Packet00Connect;
import zeller51.goldwars.net.packet.Packet02CreateMap;

public class ClientPacketHandler extends Thread {

	public static final int PORT = 1337;
	
	private InetAddress serverIpAddress;
	private DatagramSocket socket;
	private Game game;

	public ClientPacketHandler(Game game, String serverIpAddress) {
		this.game = game;
		
		try {
			this.serverIpAddress = InetAddress.getByName(serverIpAddress);
			socket = new DatagramSocket();
		} catch (UnknownHostException e) {
			System.err.println("Cannot find server: " + serverIpAddress);
		} catch (SocketException e) {
			System.err.println("Unable to bind socket!");
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

			String message = new String(packet.getData());
			System.out.println("SERVER > " + message);

		}
	}

	public void sendData(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length,
				serverIpAddress, PORT);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
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
			handleConnect((Packet00Connect) packet, address, port);
			break;
		case DISCONNECT:
			packet = new Packet01Disconnect(data);
			handleDisconnect((Packet01Disconnect) packet, address, port);
			break;
		case CREATEMAP:
			packet = new Packet02CreateMap(data);
			handleCreateMap((Packet02CreateMap) packet);
			break;
		case CREATEBLOCK:
			packet = new Packet03CreateBlock(data);
			handleCreateBlock((Packet03CreateBlock) packet);
			break;
		case MAPSENT:
			handleMapSent();
			break;
		}
	}

	private void handleMapSent() {
		game.mapSent = true;
	}

	private void handleCreateBlock(Packet03CreateBlock packet) {
		game.map.createBlock(packet.getType(), packet.getX(), packet.getY());
	}
	
	private void handleCreateMap(Packet02CreateMap packet) {
		game.createMap(packet.getWidth(), packet.getHeight());
	}

	private void handleDisconnect(Packet01Disconnect packet, InetAddress address, int port) {
		System.out.println(packet.getUsername() + " has left the server. ");
	}

	private void handleConnect(Packet00Connect packet, InetAddress address,
			int port) {
		System.out.println(packet.getUsername() + " has connected to the server. ");
	}
}
