import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Hbroker extends Thread {

	protected Socket socket;
	protected Socket sock;
	
	protected DataInputStream in = null;
	protected DataOutputStream out = null;
	
	protected DataInputStream dis = null;
	protected DataOutputStream dos = null;
	
	public Hbroker(Socket socket) {
		this.socket = socket;
		try {
			
			//Exception ó�� ����(throw �׻� ��� �ؾ� �Ѵ�..ó���� �Ҽ��� �ִ°��� ȣ�����ʿ��� ó���ؾ��ϴ°���)
			in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		} catch (IOException e) {
			System.err.println("��ſ���~!!");
			e.printStackTrace();
		}
		
		try {
			sock = new Socket("192.168.18.1", 10010);
			dis = new DataInputStream(sock.getInputStream());
			dos = new DataOutputStream(sock.getOutputStream());
		} catch (UnknownHostException e) {
			System.err.println("�� �� ���� host");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("��� ����");
			e.printStackTrace();
		}
	}

	public void exec() {


		try {
			System.out.println("accept..!!");


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

			dos.write(body);
			dos.flush();
			
			dis.readFully(body);
			
			out.write(body);
			out.flush();

			System.out.println("Send Completed");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}