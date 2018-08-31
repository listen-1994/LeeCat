package Http.Builder;

import Http.Request.HttpRequestBody;
import Http.Request.HttpRequestHeader;
import Http.Request.LeeHttpRequest;
import org.apache.log4j.Logger;

import java.net.Socket;

public class HttpRequestBuilder {
    private byte[] request;
    private static Logger log = Logger.getLogger(HttpRequestBuilder.class);

    public HttpRequestBuilder(byte[] a_request) {
        this.request = a_request;
    }

    public LeeHttpRequest build(Socket socket) {
        String requestString = new String(this.request);
        String[] requests = requestString.split("\r\n\r\n");
        String header = requests[0];
        HttpRequestBody requestBody=null;
        HttpRequestHeader requestHeader = new RequestHeaderBuilder(header).build();
        if (requestHeader.getMethod().equals("GET") && requestHeader.getUri().contains("?")) {
            String body = requestHeader.getUri().split("[?]")[1];
            requestHeader.setUri(requestHeader.getUri().split("[?]")[0]);
            requestBody = new RequestBodyBuilder(body).build();
        } else if (requestHeader.getMethod().equals("POST")&&requests.length > 1&&requestHeader.getHeader("Content-Type")!=null) {
            if(requestHeader.getHeader("Content-Type").equals("application/x-www-form-urlencoded")){
                String body = requests[1];
                requestBody = new RequestBodyBuilder(body).build();
            }else if(requestHeader.getHeader("Content-Type").equals("Content-Type= multipart/form-data")){
                String boundary = requestHeader.getHeader("boundary");
                int contextLength = Integer.valueOf(requestHeader.getHeader("Content-Length"));
                byte[] bodyByte = new byte[contextLength];
                System.arraycopy(this.request,request.length-contextLength,bodyByte,0,contextLength);
                RequestBodyBuilder bodyBuilder = new RequestBodyBuilder();
                bodyBuilder.build(bodyByte,boundary);
            }
        }

        return new LeeHttpRequest(requestHeader,requestBody,socket);
    }
}
