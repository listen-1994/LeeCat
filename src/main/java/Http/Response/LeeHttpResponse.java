package Http.Response;

import java.io.ByteArrayOutputStream;
import java.net.Socket;

public class LeeHttpResponse {
    private HttpResponseHeader header = new HttpResponseHeader();

    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    public ByteArrayOutputStream getOutputStream(){
        return this.outputStream;
    }

    public HttpResponseHeader getHeader(){
        return this.header;
    }

}
