package Http.Request;

import sun.management.Agent;

import java.util.HashMap;

public class HttpRequestHeader {
    private String method;
    private String uri;
    private String protocolVersion;

    private HashMap<String,String> header;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public void setHeader(String key,String value){
        if(header==null)
            header = new HashMap<>();
        header.put(key,value);
    }

    public String toString(){
        return method+" "+uri+" "+protocolVersion+"\n"
                +header.toString();
    }
}
