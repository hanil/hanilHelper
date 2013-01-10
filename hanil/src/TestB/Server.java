package TestB;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * Message Broker
 */

public class Server {

	// Target Server IP/Port
	public static String serverIp = "";
	public static int serverPort = -1;

	/**
	 * 
	 * @param args
	 *            <host name> <port> <target host> <port>
	 */
	public static void main(String[] args) {

		ServerSocket serverSocket = null;
		Socket socket = null;
		try {

			System.out.println("##### Message Broker Program #####");
			if (args.length != 4) {
				System.err
						.println("java Server <host name> <port> <target host> <port>");
				System.exit(1);
			}

			String host = args[0];
			int port = -1;

			try {
				port = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				System.err
						.println("java Server <host name> <port> <target host> <port>");
				System.exit(2);
			}

			serverIp = args[2];

			try {
				serverPort = Integer.parseInt(args[3]);
			} catch (NumberFormatException e) {
				System.err
						.println("java Server <host name> <port> <target host> <port>");
				System.exit(3);
			}

			serverSocket = new ServerSocket();
			serverSocket.setReuseAddress(true);
			serverSocket.bind(new InetSocketAddress(host, port));

			while (true) {
				System.out.println("Client connect Waiting...");

				socket = serverSocket.accept();
				System.out.println("Connected.");

				/**
				 * @param socket
				 */
			
				new Worker(socket).start();
			}


		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}