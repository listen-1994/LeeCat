package Http.Builder;

import Http.Request.HttpRequestBody;
import Http.Request.HttpRequestHeader;
import Http.Request.MultipartFile;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestBodyBuilder {
    private Logger log = Logger.getLogger(RequestBodyBuilder.class);
    private String body;

    public RequestBodyBuilder(String a_body) {
        this.body = a_body;
    }

    public RequestBodyBuilder() {

    }

    public HttpRequestBody build() {
        HttpRequestBody requestBody = new HttpRequestBody();
        String[] parameters = this.body.split("&");
        for (int i = 0; i < parameters.length; i++) {
            requestBody.setParameter(parameters[i].split("=")[0], parameters[i].split("=")[1]);
        }
        return requestBody;
    }

    public HttpRequestBody build(byte[] bodyByte, HttpRequestHeader requestHeader) throws UnsupportedEncodingException {
        HttpRequestBody requestBody = new HttpRequestBody();
        String bodyString = new String(bodyByte, "ISO-8859-1");
        String boundary = "--" + requestHeader.getHeader("Content-Type").split(";")[1].split("=")[1];

        log.info(boundary);
        log.info(bodyString);

        String[] parameters = bodyString.split(boundary);
        for (int i = 1; i < parameters.length - 1; i++) {
            String[] headerAndBody = parameters[i].split("\r\n\r\n");
            String contentDisposition = headerAndBody[0].split("\r\n")[1];
            String key = null;
            String value = null;
            if (!contentDisposition.contains("filename")) {
                String regEx = "name=\"(.*?)\"";
                Pattern pattern = Pattern.compile(regEx);
                Matcher matcher = pattern.matcher(contentDisposition);
                if (matcher.find()) {
                    key = matcher.group(1);
                }
                //删除最后的"\r\n"
                value = headerAndBody[1];
                if (key != null && value != null) {
                    requestBody.setParameter(key, value);
                }
            } else {
                String fileName=null;
                String regEx = "name=\"(.*?)\"";
                String fileNameRegEx = "filename=\"(.*?)\"";
                Pattern pattern = Pattern.compile(regEx);
                Matcher matcher = pattern.matcher(contentDisposition);
                if (matcher.find()) {
                    key = matcher.group(1);
                }
                pattern = Pattern.compile(fileNameRegEx);
                matcher = pattern.matcher(contentDisposition);
                if (matcher.find()) {
                    fileName = matcher.group(1);
                }
                value = headerAndBody[1];
                if(key!=null&&fileName!=null&&value!=null){
                    //这里会多出两个字节，要删掉
                    byte[] fileBytetmp = value.getBytes("ISO-8859-1");
                    byte[] fileByte = new byte[fileBytetmp.length-2];
                    System.arraycopy(fileBytetmp, 0, fileByte, 0, fileByte.length);
                    requestBody.setMultipartFile(key,new MultipartFile(fileName,fileByte));
                }
            }
        }

        return requestBody;
    }

}
