package Http.Request;

import org.apache.log4j.Logger;

import java.util.HashMap;

public class HttpRequestBody {
    private static Logger log = Logger.getLogger(HttpRequestBody.class);
    private HashMap<String,String> parameters;
    private HashMap<String,MultipartFile> multipartFileHashMap;


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

    public void setMultipartFile(String key,MultipartFile file){
        if(this.multipartFileHashMap==null){
            this.multipartFileHashMap = new HashMap<>();
        }
        this.multipartFileHashMap.put(key,file);
    }

    public MultipartFile getMultipartFile(String key){
        if(multipartFileHashMap==null){
            return null;
        }
        return this.multipartFileHashMap.get(key);
    }
}
