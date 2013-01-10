package serializeTest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.Map;

public class SerialTest {

	Map<String, String> target = new Hashtable<String, String>();

	ObjectOutputStream oout;
	ObjectInputStream oin;

	Socket socket;
	ServerSocket server;

	DataOutputStream dout;
	DataInputStream din;
	
	
	

	public SerialTest(Map<String, String> target) {
		this.target = target;
	}

	public void save(String fileName) {
		File file = new File(fileName);

		try {
			oout = new ObjectOutputStream(new FileOutputStream(file));
			oout.writeObject(target);
			oout.flush();
			oout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (oout != null) {
				try {
					oout.close();
				} catch (IOException ignored) {
				}
			}
		}
	}

	/**
	 * TODO method의 기능 설명
	 * 
	 * @param fileName
	 */
	public Map<String, String> load(String fileName) {
		File file = new File(fileName);

		try {
			oin = new ObjectInputStream(new FileInputStream(file));

			@SuppressWarnings("unchecked")
			Map<String, String> target = (Map<String, String>) oin.readObject();

			this.target = target;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				oin.close();
			} catch (IOException ignored) {
			}
		}
		
		return target;
	}

	public void send(String key, byte[] data) {
		String value = target.get(key);
		String[] str = value.split(",");
		String address = str[0];
		String port = str[1];

		try {
			socket = new Socket(address, Integer.valueOf(port));

			dout = new DataOutputStream(socket.getOutputStream());

			dout.write(data);
			dout.flush();
			try {
				dout.close();
			} catch (Exception ignored) {
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void read(String key, byte[] data) {
		

	}

}
