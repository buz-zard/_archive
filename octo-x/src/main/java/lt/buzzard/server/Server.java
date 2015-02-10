package lt.buzzard.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import lt.buzzard.connection.Connection;

public class Server implements Connection {

    private boolean sent;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private int port;
    private DataOutputStream out;
    private BufferedReader in;
    private String packet;

    public Server(int port) {
        setPort(port);
    }

    @Override
    public void connect() throws IOException {
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new DataOutputStream(clientSocket.getOutputStream());
        sent = true;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void disconnect() throws IOException {
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }

    @Override
    public void sendPacket(String packet) throws IOException {
        if (!sent) {
            // System.out.println(" -> sending packet: " + packet);
            out.writeBytes(packet + '\n');
            sent = true;
        }
    }

    @Override
    public String getPacket() throws IOException {
        if (sent) {
            packet = in.readLine();
            sent = false;
            // System.out.println(" -> raceived packet: " + packet);
            return packet;
        } else {
            return null;
        }
    }

    @Override
    public boolean isSent() {
        return sent;
    }

}
