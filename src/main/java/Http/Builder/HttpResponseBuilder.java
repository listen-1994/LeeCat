package Http.Builder;

import Http.Response.HttpResponseHeader;
import Http.Response.LeeHttpResponse;

import java.net.Socket;

public class HttpResponseBuilder {

    public static void buildHeader(LeeHttpResponse response){
        response.getHeader().setHeader("Content-Length",String.valueOf(response.getOutputStream().toByteArray().length));
    }
}
