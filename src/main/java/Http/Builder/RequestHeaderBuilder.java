package Http.Builder;

import Http.Request.HttpRequestHeader;

public class RequestHeaderBuilder {
    private String header;

    public RequestHeaderBuilder(String a_header) {
        this.header = a_header;
    }

    public HttpRequestHeader build() {
        HttpRequestHeader header = new HttpRequestHeader();
        String[] headers = this.header.split("\r\n");
        String[] requesLine = headers[0].split(" ");
        header.setMethod(requesLine[0]);
        header.setUri(requesLine[1]);
        header.setProtocolVersion(requesLine[2]);


        for(int i = 1;i<headers.length;i++){
            String[] keyValue = headers[i].split(":");
            header.setHeader(keyValue[0],keyValue[1]);
        }

        return header;
    }
}
