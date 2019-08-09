package urx;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.util.Arrays;

public class Networker {
	
	String ip;
	int port = 30002;
	BufferedReader in;
	DataOutputStream out;
	Socket socket;

	public Networker(String ip) {
		this.ip = ip;
		try {
			socket = new Socket(ip, port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new DataOutputStream(socket.getOutputStream());
			System.out.println("Created Socket: " + socket.isConnected());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Reciever r = new Reciever();
		r.start();
	}
	
	public void send(String message) {
		try {
			out.write(message.getBytes());
			out.flush();
			System.out.println("Sending message: " + message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	class Reciever extends Thread {
		
		@Override
		public void run() {
			System.out.println("Starting Reciever Thread");
			try {
				byte[] recieved = socket.getInputStream().readNBytes(1386);
				while(true) {
					recieved = socket.getInputStream().readNBytes(715);
					ByteBuffer bb = ByteBuffer.wrap(recieved);

					System.out.println("————————————————————————————————————");
					Struct struct = new Struct();

				    String ss = Arrays.toString(recieved);
				    
				    System.out.println(ss);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
