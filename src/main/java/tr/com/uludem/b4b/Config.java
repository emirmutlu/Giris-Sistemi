package tr.com.uludem.b4b;

import java.io.InputStream;
import java.util.Properties;
public final class Config {
    private static final Properties PROPS = new Properties();
    static {
        try (InputStream in = Config.class.getResourceAsStream("/b4b.properties")){
            if (in != null) PROPS.load(in);
        } catch (Exception ignored){}
    }
    public static String get (String key){
        return PROPS.getProperty(key,"");
    }
}
