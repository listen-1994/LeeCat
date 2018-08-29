package Http.Response;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseHeader {
    private int httpCode = 200;
    private String protocolVersion = "HTTP/1.1";
    private String isOK = "OK";
    private HashMap<String,String> headers;

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public String getIsOK() {
        return isOK;
    }

    public void setIsOK(String isOK) {
        this.isOK = isOK;
    }

    public void setHeader(String key, String value){
        if(headers==null){
            headers = new HashMap<>();
        }
        headers.put(key,value);
    }
}
