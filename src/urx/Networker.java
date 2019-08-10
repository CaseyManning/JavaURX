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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

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
		
		byte[] currentPacket;
		
		private final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
		public String bytesToHex(Byte[] bytes) {
		    char[] hexChars = new char[bytes.length * 2];
		    for (int j = 0; j < bytes.length; j++) {
		        int v = bytes[j] & 0xFF;
		        hexChars[j * 2] = HEX_ARRAY[v >>> 4];
		        hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
		    }
		    return new String(hexChars);
		}

		
		@Override
		public void run() {
			System.out.println("Starting Reciever Thread");
			try {
//				byte[] received = socket.getInputStream().readNBytes(1386);
				LinkedList<Byte> stored = new LinkedList<Byte>();
				byte newByte = socket.getInputStream().readNBytes(1)[0];
				stored.add(newByte);
				newByte = socket.getInputStream().readNBytes(1)[0];
				stored.add(newByte);
				newByte = socket.getInputStream().readNBytes(1)[0];
				stored.add(newByte);
				newByte = socket.getInputStream().readNBytes(1)[0];
				stored.add(newByte);
				
				while(true) {
					
					// Finds the start of the packet based on the length and type
					Byte[] bytes = new Byte[stored.size()];
					bytes = stored.toArray(bytes);
					String hex = bytesToHex(bytes);
					long packetSize = Long.parseLong(hex,16);
					if(packetSize > 5 && packetSize < 2000) {
						newByte = socket.getInputStream().readNBytes(1)[0];
						Byte[] asdfa = new Byte[1];
						asdfa[0] = newByte;
						String hex2 = bytesToHex(asdfa);
						int packetType = Integer.parseInt(hex2,16);
						if(packetType == 16) {
							System.out.println("AAIUSHDIUASHIFASIUFHAIUSHFIUASHFIUASHFAUIHFASUHFIu");
							currentPacket = socket.getInputStream().readNBytes((int) packetSize);

							ArrayList<byte[]> subPackets = getSubpackets(currentPacket);
							
							for(byte[] subPacket : subPackets) {
								//TODO: Billy
							}
						}
						
					} else {
						newByte = socket.getInputStream().readNBytes(1)[0];
					}
					
					
					stored.add(newByte);
					stored.remove(0);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public ArrayList<byte[]> getSubpackets(byte[] bigPacket) {
			int i = 0;
			ArrayList<byte[]> subPackets = new ArrayList<byte[]>();
			while(true) {
				Byte[] lengthBytes = {bigPacket[i], bigPacket[i+1], bigPacket[i+2], bigPacket[i+3]};
				String lengthHex = bytesToHex(lengthBytes);
				long subPacketSize = Long.parseLong(lengthHex,16);
				byte[] packet = Arrays.copyOfRange(bigPacket, i + 5, i + 5 + (int) subPacketSize);;
				subPackets.add(packet);
				i += subPacketSize + 5;
				if(i >= bigPacket.length) {
					break;
				}
			}
			return subPackets;
			
		}
	}
}
