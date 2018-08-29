package Http.Request;

import org.apache.log4j.Logger;

import java.util.HashMap;

public class HttpRequestBody {
    private static Logger log = Logger.getLogger(HttpRequestBody.class);
    private HashMap<String,String> parameters;

    public void setParameter(String key,String value){
        if(parameters==null){
            parameters = new HashMap<>();
        }
        parameters.put(key,value);
    }

    public String getParameter(String key) {
        if (parameters == null) {
            return null;
        }
        return parameters.get(key);
    }

    public String toString(){
        if(parameters==null){
            return "null";
        }
        return parameters.toString();
    }
}
