package Servlet;

import Config.ConfigReader;
import Http.Request.LeeHttpRequest;
import Http.Response.LeeHttpResponse;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ServletExecutor {
    public void run(LeeHttpRequest request,LeeHttpResponse response) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        MyClassLoader classLoader = new MyClassLoader(ConfigReader.servletPath);
        Class clazz = classLoader.findClass("HelloWorld.HelloWorld");
        Object servelt = clazz.newInstance();
        Method method = clazz.getDeclaredMethod("doGet",new Class[]{LeeHttpRequest.class,LeeHttpResponse.class});
        method.invoke(servelt,request,response);
    }

    public void runHttp(LeeHttpRequest request, LeeHttpResponse response) throws IOException {
        ByteArrayOutputStream out = response.getOutputStream();
        byte[] bytes = new byte[1024];
        if(request.getHeader().getUri().equals("/")){
            FileInputStream in = new FileInputStream(ConfigReader.httpPath+"/index.html");
            int readNum;
            while((readNum=in.read(bytes))>0){
                out.write(bytes,0,readNum);
                if(readNum<bytes.length){
                    break;
                }
            }
            out.flush();
        }else{
            FileInputStream in = new FileInputStream(ConfigReader.httpPath+request.getHeader().getUri());
            int readNum;
            while((readNum=in.read(bytes))>0){
                out.write(bytes,0,readNum);
                if(readNum<bytes.length){
                    break;
                }
            }
            out.flush();
        }
    }
}
