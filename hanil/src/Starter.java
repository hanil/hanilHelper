import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import lempel.blueprint.base.concurrent.WorkerGroup;

public class Starter extends Thread{

	ServerSocket server;
	Socket client;
	protected WorkerGroup workerGrp;
	
	
	public Starter() {
		
		try {
			server = new ServerSocket();
			server.setReuseAddress(true);
			server.bind(new InetSocketAddress("127.0.0.1", 1258));
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			workerGrp = new WorkerGroup(HWorker.class, 1000);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	@Override
	public void run(){
		while (true) {
			System.out.println("waiting...");
			
			try {
				client = server.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Hbroker hb = new Hbroker(client);
			
			try {
				workerGrp.addJob(hb);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
			//hb.start();
		}
		
	}

	public static void main(String[] args) {
		Starter sts =  new Starter();
		sts.start();
	}
}
