import Config.ConfigReader;
import Server.LeeCatServer;
import Server.ServerExecutor;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LeeCat {
    public static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(20);
    private static Logger log = Logger.getLogger(LeeCat.class);
    public static void main(String[] args) throws IOException, NoSuchFieldException, IllegalAccessException {
        startup();
    }

    public static void startup() throws IOException, NoSuchFieldException, IllegalAccessException {
        //读入配置文件
        ConfigReader.readConfig();

        //启动ServerSocket监听
        LeeCatServer server = LeeCatServer.getServer();
        server.startup();
        EXECUTOR_SERVICE.execute(new ServerExecutor(server, EXECUTOR_SERVICE));
        log.info("服务器启动"+server.getPort());
    }

}
