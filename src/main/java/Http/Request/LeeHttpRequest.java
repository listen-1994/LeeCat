package Http.Request;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class LeeHttpRequest {

    private HttpRequestHeader requestHeader;
    private HttpRequestBody requestBody;
    private Socket socket;

    public LeeHttpRequest(HttpRequestHeader requestHeader, HttpRequestBody requestBody,Socket socket) {
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
        this.socket = socket;
    }

    public HttpRequestHeader getHeader() {
        return this.requestHeader;
    }

    public HttpRequestBody getBody() {
        return this.requestBody;
    }

    public String toString() {
        if (requestBody == null) {
            return this.requestHeader.toString() + "\n";
        }
        return this.requestHeader.toString() + "\n" + this.requestBody.toString();
    }

    public InputStream getInputStream() throws IOException {
        return this.socket.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        return this.socket.getOutputStream();
    }

    public Socket getSocket(){
        return this.socket;
    }

    public String getParameter(String key){
        return this.getBody().getParameter(key);
    }
}
