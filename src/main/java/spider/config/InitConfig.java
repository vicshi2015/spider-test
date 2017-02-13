package spider.config;

import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by shiwei on 2017/1/11.
 */
public class InitConfig {
    public static String proxyHost = "";
    public static int proxyPort = 0;

    static {
        try
        {
            InputStreamReader stream = new InputStreamReader(
                    Thread.currentThread().getContextClassLoader()
                            .getResourceAsStream("spider.properties"), "GBK");
            Properties props = new Properties();
            props.load(stream);

            if(!props.getProperty("proxy.host").isEmpty())
            {
                proxyHost = props.getProperty("proxy.host");
            }

            if(!props.getProperty("proxy.port").isEmpty())
            {
                proxyPort = Integer.parseInt(props.getProperty("proxy.port"));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
