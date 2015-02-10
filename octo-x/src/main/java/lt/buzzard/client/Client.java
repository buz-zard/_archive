package lt.buzzard.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import lt.buzzard.connection.Connection;

public class Client implements Connection{
	private Socket clientSocket;
	private String host;
	private int port;
	private BufferedReader in;
	private DataOutputStream out;
	private String packet;
	private boolean sent;
	
	public Client(String host, int port){
		this.host = host;
		this.port = port;
	}
	
	public void connect() throws UnknownHostException, IOException{
		clientSocket = new Socket(host, port);
		out = new DataOutputStream(clientSocket.getOutputStream());
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		sent = false;
	}
	
	public void disconnect() throws IOException{
		out.close();
		in.close();
		clientSocket.close();
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public void sendPacket(String packet) throws IOException{
		if(!sent){
			//System.out.println(" -> sending packet: " + packet);
			out.writeBytes(packet + '\n');
			sent = true;
		}
	}
	
	public String getPacket() throws IOException{
		if(sent){
			packet = in.readLine();
			sent = false;
			//System.out.println(" -> raceived packet: " + packet);
			return packet;
		} else{
			return null;
		}
	}
	
	public boolean isSent(){
		return sent;
	}
}
