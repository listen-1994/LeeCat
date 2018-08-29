package Server;

import Http.Builder.HttpRequestBuilder;
import Http.Builder.HttpResponseBuilder;
import Http.Request.LeeHttpRequest;
import Http.Response.HttpResponseHeader;
import Http.Response.LeeHttpResponse;
import Servlet.HttpServlet;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Map;

public class SocketExecutor implements Runnable {
    private SocketChannel socket;
    private static Logger log = Logger.getLogger(SocketChannel.class);
    private static final int BUFFERSIZE = 8192;
    private ByteBuffer byteBuffer = ByteBuffer.allocateDirect(BUFFERSIZE);

    public SocketExecutor(SocketChannel a_socket) {
        this.socket = a_socket;
    }

    public void run() {
        try {
            int bytesRead = socket.read(byteBuffer);
            boolean isReadEnd = bytesRead < BUFFERSIZE;
            byteBuffer.flip();
            byte[] bytes = new byte[byteBuffer.remaining()];
            byteBuffer.get(bytes, 0, bytes.length);

            LeeHttpRequest request = new HttpRequestBuilder(new String(bytes)).build(socket.socket());
            log.info(request.toString());
            LeeHttpResponse response = new LeeHttpResponse();
            HttpServlet servlet = new HttpServlet();
            servlet.doGet(request, response);
            HttpResponseBuilder.buildHeader(response);
            sendResponse(response, socket.socket());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendResponse(LeeHttpResponse response, Socket socket) throws IOException {
        StringBuilder responseHeader = new StringBuilder();
        responseHeader.append(response.getHeader().getProtocolVersion())
                .append(" ")
                .append(response.getHeader().getHttpCode())
                .append(" ")
                .append(response.getHeader().getIsOK())
                .append("\r\n");
        for(Map.Entry<String,String> entry:response.getHeader().getHeaders().entrySet()){
            responseHeader.append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue())
                    .append("\r\n");
        }
        responseHeader.append("\r\n");

        OutputStream out = socket.getOutputStream();
        out.write(responseHeader.toString().getBytes());
        out.write(response.getOutputStream().toByteArray());
        out.flush();
    }
}