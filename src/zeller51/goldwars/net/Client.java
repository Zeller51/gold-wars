package zeller51.goldwars.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Client {

	public InetAddress ipAddress;
	public int port;
	private String username;

	public Client(String hostAddress, int port, String username) {
		try {
			ipAddress = InetAddress.getByName(hostAddress);
		} catch (UnknownHostException e) {
			System.err.println("Unable to get InetAddress of client "
					+ username + " at " + hostAddress + ":" + port + "!");
			e.printStackTrace();
		}
		this.port = port;
		this.username = username;
	}

}
