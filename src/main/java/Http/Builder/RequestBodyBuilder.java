package Http.Builder;

import Http.Request.HttpRequestBody;
import org.apache.log4j.Logger;

public class RequestBodyBuilder {
    private Logger log = Logger.getLogger(RequestBodyBuilder.class);
    private String body;

    public RequestBodyBuilder(String a_body) {
        this.body = a_body;
    }

    public HttpRequestBody build() {
        HttpRequestBody requestBody = new HttpRequestBody();
        String[] parameters = this.body.split("&");
        for (int i = 0; i < parameters.length; i++) {
            requestBody.setParameter(parameters[i].split("=")[0],parameters[i].split("=")[1]);
        }
        return requestBody;
    }


}
