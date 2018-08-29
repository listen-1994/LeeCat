package Config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.Properties;

public class ConfigReader {
    public static final String CONFIGFILE = "leecat.config";
    public static String port = "10086";
    public static String servletPath = "ROOT/webApps";

    public static void readConfig() throws IOException, NoSuchFieldException, IllegalAccessException {
        Properties pro = new Properties();
        FileInputStream in = new FileInputStream(CONFIGFILE);
        pro.load(in);
        Enumeration en = pro.propertyNames();

        while (en.hasMoreElements()) {
            String key = (String) en.nextElement();
            String value = pro.getProperty(key);
            Field field = ConfigReader.class.getDeclaredField(key);
            field.setAccessible(true);
            field.set(new ConfigReader(), value);
        }
    }
}
