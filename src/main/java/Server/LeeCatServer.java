package Server;


import Config.ConfigReader;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;


public class LeeCatServer {

    private static LeeCatServer server = null;
    private ServerSocketChannel ss = null;

    private boolean started = false;

    private int port;

    private LeeCatServer() {
        this.port = Integer.valueOf(ConfigReader.port);
    }

    public static LeeCatServer getServer() {
        if (server == null) {
            server = new LeeCatServer();
            return server;
        } else {
            return server;
        }
    }

    public void startup() throws IOException {

        this.ss = ServerSocketChannel.open();
        this.ss.socket().bind(new InetSocketAddress(this.port));
        this.started = true;

    }

    public int getPort() {
        return this.port;
    }

    public void stop() throws IOException {
        started = false;
        ss.close();
    }

    public boolean isStarted() {
        return this.started;
    }

    public ServerSocketChannel getSs() {
        return this.ss;
    }
}
