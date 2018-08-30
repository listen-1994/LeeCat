package Servlet;

import java.io.*;

public class MyClassLoader extends ClassLoader {
    private String myLibPath;

    public MyClassLoader(String libPath) {
        this.myLibPath = libPath;
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String fileName = getFileName(name);
        File file = new File(myLibPath, fileName);

        try {
            FileInputStream is = new FileInputStream(file);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            int len = 0;
            try {
                while((len = is.read())!=-1){
                    bos.write(len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            byte[] data = bos.toByteArray();
            is.close();
            bos.close();

            return  defineClass(name,data,0,data.length);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.findClass(name);
    }

    private String getFileName(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append(name.replace(".","/"))
                .append(".class");
        return sb.toString();
    }

}
