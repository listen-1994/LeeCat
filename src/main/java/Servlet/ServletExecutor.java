package Servlet;

import Config.ConfigReader;
import Http.Request.LeeHttpRequest;
import Http.Response.LeeHttpResponse;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ServletExecutor {

    public String findPath(String servletUri) {
        String servletName = ServletLoader.servletMappingMap.get(servletUri);
        if (servletName == null) {
            return "null";
        }
        String servletPath = ServletLoader.servletClassMap.get(servletName);
        if (servletPath == null) {
            return "null";
        }
        return servletPath;
    }

    public void run(LeeHttpRequest request, LeeHttpResponse response) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        MyClassLoader classLoader = new MyClassLoader(ConfigReader.servletPath);
        String servletPath = findPath(request.getHeader().getUri());
        if(servletPath.equals("null")){
            return;
        }
        Class clazz = classLoader.findClass(servletPath);
        Object servelt = clazz.newInstance();
        Method method = clazz.getDeclaredMethod("doGet", new Class[]{LeeHttpRequest.class, LeeHttpResponse.class});
        method.invoke(servelt, request, response);
    }

    public void runHttp(LeeHttpRequest request, LeeHttpResponse response) throws IOException {
        ByteArrayOutputStream out = response.getOutputStream();
        byte[] bytes = new byte[1024];
        if (request.getHeader().getUri().equals("/")) {
            FileInputStream in = new FileInputStream(ConfigReader.httpPath + "/index.html");
            int readNum;
            while ((readNum = in.read(bytes)) > 0) {
                out.write(bytes, 0, readNum);
                if (readNum < bytes.length) {
                    break;
                }
            }
            out.flush();
            out.close();
        } else {
            FileInputStream in = new FileInputStream(ConfigReader.httpPath + request.getHeader().getUri());
            int readNum;
            while ((readNum = in.read(bytes)) > 0) {
                out.write(bytes, 0, readNum);
                if (readNum < bytes.length) {
                    break;
                }
            }
            out.flush();
            out.close();
        }
    }
}
