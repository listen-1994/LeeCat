package Http.Builder;

import Http.Request.HttpRequestBody;
import Http.Request.HttpRequestHeader;
import Http.Request.LeeHttpRequest;
import org.apache.log4j.Logger;

import java.net.Socket;

public class HttpRequestBuilder {
    private String request;
    private static Logger log = Logger.getLogger(HttpRequestBuilder.class);

    public HttpRequestBuilder(String a_request) {
        this.request = a_request;
    }

    public LeeHttpRequest build(Socket socket) {
        String[] requests = this.request.split("\r\n\r\n");
        String header = requests[0];
        HttpRequestBody requestBody=null;
        HttpRequestHeader requestHeader = new RequestHeaderBuilder(header).build();
        if (requestHeader.getMethod().equals("GET") && requestHeader.getUri().contains("?")) {
            String body = requestHeader.getUri().split("[?]")[1];
            requestHeader.setUri(requestHeader.getUri().split("[?]")[0]);
            requestBody = new RequestBodyBuilder(body).build();
        } else if (requests.length > 1) {
            String body = requests[1];
            requestBody = new RequestBodyBuilder(body).build();
        }

        return new LeeHttpRequest(requestHeader,requestBody,socket);
    }
}
