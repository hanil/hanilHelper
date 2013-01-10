package TestB;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Hbroker extends Thread {

	protected Socket socket;
	protected DataInputStream in = null;
	protected DataOutputStream out = null;
	
	public Hbroker(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {

		try {
			System.out.println("accept..!!");

			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());

			byte[] buffer = new byte[10];

			in.readFully(buffer);

			String dataStr = new String(buffer);
			System.out.println("dataLength =============>" + dataStr);

			int dataSize = 0;
			dataSize = Integer.parseInt(dataStr.trim()) - 10;

			System.out.println("dataSize : " + dataSize);

			byte[] body = new byte[dataSize + buffer.length];
			System.arraycopy(buffer, 0, body, 0, buffer.length);

			in.readFully(body, buffer.length, dataSize);

			System.out.println("Data  : [" + new String(body) + "]");

			out.write(body);
			out.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		if (args.length != 2) {
			System.err.println("java Hbroker <host name> <port>");
			System.exit(1);
		}

		String host = args[0];
		int port = -1;

		try {
			port = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			System.err.println("java Hbroker <host name> <port>");
			System.exit(2);
		}
		ServerSocket server = new ServerSocket();
		server.setReuseAddress(true);
		server.bind(new InetSocketAddress(host, port));

		while (true) {
			System.out.println("waiting...");
			Socket client = server.accept();
			Hbroker echo = new Hbroker(client);
			echo.start();
		}
	}
}