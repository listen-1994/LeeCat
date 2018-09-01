# LeeCat.LeeCat

一个试着简单实现tomcat的程序

## 安装运行
解压LeeCat.tar.gz

```bash
tar zxvf LeeCat.tar.gz
```

进入LeeCat文件夹，运行

```bash
java -jar LeeCat-1.0.jar
```

## 配置文件

### leecat.config

```properties
#设置服务器启动端口
port = 10086

#配置servlet路径
servletPath = ROOT/webApps

#配置http文件的路径
httpPath = ROOT
```

### ROOT/webApps/web.xml

配置servlet映射，和tomcat一样，主要是<servlet>和<servlet-mapping>

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee
                      http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
         version="3.1"
         metadata-complete="true">
    <display-name>Welcome to Leecat</display-name>
    <description>
        Welcome to Leecat
    </description>

    <servlet>
        <servlet-name>Hello</servlet-name>
        <servlet-class>Hello.HelloWorld</servlet-class>
    </servlet>

    <servlet-mapping>
        <servlet-name>Hello</servlet-name>
        <url-pattern>/Hello</url-pattern>
    </servlet-mapping>

</web-app>
```

## Servlet编写

导入LeeCat-1.0.jar包，实现doGet()和doPost()

```java
package Hello;

import Http.Request.LeeHttpRequest;
import Http.Request.MultipartFile;
import Http.Response.LeeHttpResponse;
import Servlet.HttpServlet;

import java.io.*;
import java.nio.file.Files;

public class HelloWorld extends HttpServlet {
    @Override
    public void init() {

    }

    @Override
    public void doGet(LeeHttpRequest leeHttpRequest, LeeHttpResponse leeHttpResponse) {
        PrintWriter out = new PrintWriter(leeHttpResponse.getOutputStream());
        String name = leeHttpRequest.getParameter("name");
        String password = leeHttpRequest.getParameter("password");
        out.write("name:"+name);
        out.write("pasword:"+password);
        out.flush();
        out.close();
    }

    @Override
    public void doPost(LeeHttpRequest leeHttpRequest, LeeHttpResponse leeHttpResponse) {
        PrintWriter out = new PrintWriter(leeHttpResponse.getOutputStream());

        String name = leeHttpRequest.getParameter("name");
        String password = leeHttpRequest.getParameter("password");

        out.write("name:"+name);
        out.write("pasword:"+password);
        out.flush();
        out.close();

        MultipartFile multipartFile = leeHttpRequest.getBody().getMultipartFile("fileUpload");

        File dir = new File("resources");
        if(!dir.exists()){
            dir.mkdir();
        }

        File file = new File("resources/"+multipartFile.getFileName());
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream fileout = new FileOutputStream("resources/"+multipartFile.getFileName());
            fileout.write(multipartFile.getFileBytes());
            fileout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

