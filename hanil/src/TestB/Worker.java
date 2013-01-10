package TestB;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;

public class Worker extends Thread {

	private static final int MAX_REQUEST = 100;
	private LinkedList<Socket> requestSocketQueue = new LinkedList<Socket>();
    private int tail;
    private int head;
    private int count;
    private Socket socket;
	private Socket requestSocket;
	private Socket responseSocket;
	private DataInputStream in;
	private DataOutputStream out;
	private DataInputStream input;
	private DataOutputStream output;
	public static int success = 0;

	/**
	 * 
	 * @param socket
	 */
    public Worker(Socket socket) {
    	this.socket = socket;
        this.head = 0;
        this.tail = 0;
        this.count = 0;
    }

	public void run() {

			try {
				
				putSocketRequest(socket);
				requestSocket = takeSocket();
				
				in = new DataInputStream(requestSocket.getInputStream());
				out = new DataOutputStream(requestSocket.getOutputStream());

				// 서버로 보낼 소켓 열기
				responseSocket = new Socket(Server.serverIp, Server.serverPort);
				output = new DataOutputStream(responseSocket.getOutputStream());
				input = new DataInputStream(responseSocket.getInputStream());
				/*
				 * Message Spec
				 */
				byte[] buf = new byte[10];
				in.readFully(buf);

				String dataStr = new String(buf);
				System.out.println("dataLength : " + dataStr);

				int dataSize = 0;
				dataSize = Integer.parseInt(dataStr.trim()) - 10;
				System.out.println("dataSize : " + dataSize);

				byte[] body = new byte[dataSize + buf.length];
				System.arraycopy(buf, 0, body, 0, buf.length);

				in.readFully(body, buf.length, dataSize);
				System.out.println("Data : " + body);

				/*
				 *  Send To Server
				 *  And Receive From Server 
				 *  And Send To Client Again
				 */

				// Send Data in Client To Server 
				output.write(body);
				output.flush();

				// Read Receive Data From Server Again
				input.read(body);

				// Send Data To Client Again
				out.write(body);
				out.flush();

			} catch (IOException ignored) {
			} finally {
		
				if (in != null) {
					try {
						in.close();
					} catch (IOException ignored) {
					} finally {
						in = null;
					}
				}
				if (out != null) {
					try {
						out.close();
					} catch (IOException ignored) {
					} finally {
						out = null;
					}
				}
				if (requestSocket != null) {
					try {
						requestSocket.close();
					} catch (IOException ignored) {
					} finally {
						requestSocket = null;
					}
				}
				if (responseSocket != null) {
					try {
						responseSocket.close();
					} catch (IOException ignored) {

					} finally {
						responseSocket = null;
					}
				}
				if (socket != null) {
					try {
						socket.close();
					} catch (IOException ignored) {
					} finally {
						socket = null;
					}
				}
			}
		}


    // 큐에 Socket 객체를 담는다
    public synchronized void putSocketRequest (Socket socket) {
    	while (count >= MAX_REQUEST) {
    		try {
    			wait();
    		} catch (InterruptedException e) {}
    	}
    	requestSocketQueue.add(tail, socket);

    	tail = (tail + 1) % MAX_REQUEST;
        count++ ;
        notifyAll();
    }
    
    // 담겨있는 큐에서 Socket 객체를 꺼내온다.   
    public synchronized Socket takeSocket() {
    	while (count <= 0) {
    		try {
    			wait();
    		} catch (InterruptedException e) {}
    	}
    	// 먼저 들어간것을 먼저 가져온다.
    	Socket socket = requestSocketQueue.get(head);
    	
    	head = (head + 1) % MAX_REQUEST;
    	count--;
    	notifyAll();
    	return socket;
    }
}