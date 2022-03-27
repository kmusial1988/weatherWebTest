package weather.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import weather.connection.HibernateUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocationValidator {

    private static final Logger logger = LogManager.getLogger(HibernateUtil.class);



    public boolean validationLatitude(String name) {
        Pattern recipientPackage = Pattern.compile("^-?[0-9]{1,3}\\.[0-9]{1,5}$");
        Matcher n = recipientPackage.matcher(name);
        double valueInt = 0;
        try {
            valueInt = Double.parseDouble(name);
        } catch (NumberFormatException e) {
            logger.error(e.getMessage(), e);
        }
        boolean rang = authenticationCoordinateInterval(-90, 90, valueInt);
        return !name.isBlank() && n.find() && rang;
    }
    public boolean validationLongitude(String name) {
        Pattern recipientPackage = Pattern.compile("^-?[0-9]{1,3}\\.[0-9]{1,5}$");
        Matcher n = recipientPackage.matcher(name);
        double valueInt = 0;
        try {
            valueInt = Double.parseDouble(name);
        } catch (NumberFormatException e) {
            logger.error(e.getMessage(), e);
        }
        boolean rang = authenticationCoordinateInterval(-180, 180, valueInt);
        return !name.isBlank() && n.find() && rang;
    }


    public boolean validationNames(String name) {

        Pattern namePackage = Pattern.compile("^[a-zA-Z]+\s?[a-zA-Z]+$");
        Matcher n = namePackage.matcher(name);
        return !name.isBlank() && n.find();
    }

    public boolean authenticationCoordinateInterval(
            int lowestInterval, int highInterval, double valueInt) {
        return valueInt >= lowestInterval && valueInt <= highInterval;

    }
}