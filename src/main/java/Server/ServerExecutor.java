package Server;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;

public class ServerExecutor implements Runnable {
    private static Logger log = Logger.getLogger(ServerExecutor.class);
    private LeeCatServer server ;

    private ExecutorService executorService;
    public ServerExecutor(LeeCatServer a_server,ExecutorService a_executorService){
        this.server = a_server;
        this.executorService = a_executorService;
    }
    public void run() {
        while(server.isStarted()){
            try {
                SocketChannel socketChannel = server.getSs().accept();
                executorService.execute(new SocketExecutor(socketChannel));
                log.info("建立连接："+socketChannel.socket().getInetAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
