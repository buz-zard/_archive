package lt.buzzard.connection;

import java.io.IOException;

public interface Connection {

    public void sendPacket(String packet) throws IOException;

    public String getPacket() throws IOException;

    public boolean isSent();

    public void connect() throws IOException;

    public void disconnect() throws IOException;

}
