package urx;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Networker {
	
	String ip;
	int port = 30002;
	InputStream in;
	DataOutputStream out;

	public Networker(String ip) {
		this.ip = ip;
        Socket socket;
		try {
			socket = new Socket(ip, port);
			in = socket.getInputStream();
			out = new DataOutputStream(socket.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void send(String message){
		try {
		out.writeUTF(message);
		out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
