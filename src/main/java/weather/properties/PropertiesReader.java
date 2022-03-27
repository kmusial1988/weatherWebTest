package weather.properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

    private static final Logger logger = LogManager.getLogger(PropertiesReader.class);

    public Properties loadFromFile(String fileName) {

        Properties properties = new Properties();
        InputStream input;

        input = getClass().getClassLoader().getResourceAsStream(fileName);

        try {
            properties.load(input);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return properties;
    }
}
