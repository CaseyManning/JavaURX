package urx;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
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
		
		public String readAll(InputStream in) throws IOException {
		    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		    StringBuilder sb = new StringBuilder();
		    String line;
		    while ((line = reader.readLine()) != null)
		        sb.append(line).append("\n");
		    return sb.toString();
		}
		
		@Override
		public void run() {
			System.out.println("Starting Reciever Thread");
			try {
				var recieved = socket.getInputStream().readNBytes(1386);
				while(true) {
					recieved = socket.getInputStream().readNBytes(715);
					ByteBuffer b = ByteBuffer.wrap(recieved);
					String[] aa = unpack("!IB6d".toCharArray(), recieved);

					System.out.println(recieved);
				    String s = Arrays.toString(recieved);
				    System.out.println(Arrays.toString(aa));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	    public String[] unpack(char[] packet, byte[] raw){
	        String[] result = new String[packet.length];

	        int pos = 0;
	        int Strindex = 0;

	        for (int x = 0; x < packet.length; x++){

	            char type = packet[x];
	            if (type == 'x'){
	                pos += 1;
	                continue;
	            }
	            else if (type == 'c'){
	                char c = (char) (raw[pos] & 0xFF);
	                result[Strindex] = Character.toString(c);
	                Strindex += 1;
	                pos += 1;
	            }
	            else if (type == 'h'){
	                ByteBuffer bb = ByteBuffer.allocate(2);
	                bb.order(ByteOrder.LITTLE_ENDIAN);
	                bb.put(raw[pos]);
	                bb.put(raw[pos+1]);
	                short shortVal = bb.getShort(0);
	                result[Strindex] = Short.toString(shortVal);
	                pos += 2;
	                Strindex += 1;
	            }
	            else if (type == 's'){
	                String s = "";

	                while (raw[pos] != (byte)0x00){
	                    char c = (char) (raw[pos] & 0xFF);
	                    s += Character.toString(c);
	                    pos += 1;
	                }
	                result[Strindex] = s;
	                Strindex += 1;
	                pos += 1;
	            }
	            else if (type == 'b'){
	                Byte p = raw[pos];
	                result[Strindex] = Integer.toString(p.intValue());
	                Strindex += 1;
	                pos += 1;
	            }
	        }
	        return result;
	    }
	}
}
