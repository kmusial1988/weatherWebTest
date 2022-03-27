package weather;


import weather.controllers.ViewController;
import weather.validation.LocationValidator;

import java.util.Scanner;


public class Gui {

    private static final ViewController LOCATION_CONTROLLER = new ViewController();

    private static final LocationValidator validation = new LocationValidator();

    private static final Scanner scanner = new Scanner(System.in);

    public static void showMainMenu() {

        String choose1;
        do {

            System.out.println("[1]. Add location");
            System.out.println("[2]. Display available locations");
            System.out.println("[3]. Download weather data");
            System.out.println("[4]. Display information about specific location");
            System.out.println("[5]. Edit location");
            System.out.println("[6]. Clear location");
            System.out.println("[0]. Exit");

            choose1 = scanner.nextLine();


            switch (choose1) {
                case "1" -> LOCATION_CONTROLLER.addLocation();
                case "2" -> LOCATION_CONTROLLER.displayLocation();
                case "3" -> chooseWeather();
                case "4" -> LOCATION_CONTROLLER.locationSearch();
                case "5" -> edit();
                case "6" -> clear();
                case "0" -> LOCATION_CONTROLLER.cleanFile();
                default -> System.out.println("Wrong choice");
            }

        } while (!choose1.equals("0"));

    }

    public static String getLongitudeAndLatitudeFromUser() {

        String longitude;
        String latitude;

        do {
            System.out.println("Write city latitude:");
            latitude = scanner.nextLine();

            if (!validation.validationLatitude(latitude)) {
                System.out.println("Enter the name, this field cannot be empty format (00.000) and rang -90 to 90");
            }

        } while (!validation.validationLatitude(latitude) );

        do {
            System.out.println("Write city longitude:");
            longitude = scanner.nextLine();

            if (!validation.validationLongitude(longitude)) {
                System.out.println("Enter the name, this field cannot be empty format (000.000) and rang -180 to 180");
            }
        } while (!validation.validationLongitude(longitude));

        return latitude + "," + longitude;
    }

    public static String getCityNameFromUser() {

        String cityName;
        do {
            System.out.println("Write city name:");
            cityName = scanner.nextLine();
            if (!validation.validationNames(cityName)) {
                System.out.println("Enter the name, this field cannot be empty");
            }
        } while (!validation.validationNames(cityName));

        return cityName;

    }

    public static String getRegionFromUser() {
        System.out.println("Write region name:");
        String region = scanner.nextLine();
        if (region.equals("")) {
            region = "User did not add region";
        }
        return region;
    }

    public static String getCountryNameFromUser() {
        String countryName;
        do {
            System.out.println("Write country name:");
            countryName = scanner.nextLine();
            if (!validation.validationNames(countryName)) {
                System.out.println("Enter the name, this field cannot be empty");
            }

        } while (!validation.validationNames(countryName));
        return countryName;
    }

    public static void edit() {

        String choose1;
        do {
            System.out.println("[1]. Edit city name");
            System.out.println("[2]. Edit region name");
            System.out.println("[3]. Edit country name");
            System.out.println("[4]. Edit coordinates");
            System.out.println("[0]. Back");

            choose1 = scanner.nextLine();

            switch (choose1) {
                case "1" -> LOCATION_CONTROLLER.editLocationModelInDb("cityName",pattern(), getCityNameFromUser());
                case "2" -> LOCATION_CONTROLLER.editLocationModelInDb("regionName",pattern(), getRegionFromUser());
                case "3" -> LOCATION_CONTROLLER.editLocationModelInDb("countryName",pattern(), getCountryNameFromUser());
                case "4" -> LOCATION_CONTROLLER.editLocationModelInDb("coordinates",pattern(), getLongitudeAndLatitudeFromUser());
                case "0" -> {
                    break;
                }
                default -> System.out.println("Wrong choice");
            }
        } while (!choose1.equals("0"));
    }

    public static void clear() {

        String choose1;
        do {
            System.out.println("[1]. Clear all locations");
            System.out.println("[2]. Delete one location");
            System.out.println("[0]. Back");

            choose1 = scanner.nextLine();

            switch (choose1) {
                case "1" -> LOCATION_CONTROLLER.cleanFile();
                case "2" -> LOCATION_CONTROLLER.delateOneLocation();
                case "0" -> {
                    break;
                }
                default -> System.out.println("Wrong choice");
            }
        } while (!choose1.equals("0"));
    }

    public static void chooseWeather() {

        String choose1;
        do {
            System.out.println("[1]. Download weather for all locations");
            System.out.println("[2]. Download weather for specific location");
            System.out.println("[0]. Back");

            choose1 = scanner.nextLine();

            switch (choose1) {
                case "1" -> {
                    chooseAllWeathersDate();
                    choose1 = "0";
                }
                case "2" -> {
                    chooseOneWeatherDate();
                    choose1 = "0";
                }
                case "0" -> {
                    break;
                }
                default -> System.out.println("Wrong choice");
            }
        } while (!choose1.equals("0"));
    }

    public static void chooseAllWeathersDate() {

        int day;
        do {
            day = Integer.parseInt(enterString("Enter which day forward you want to check the weathers (max. one week: 0-7)\n[0]. Today\n[1]. Tomorrow\n[2]. Etc."));

            if (day < 0 || day > 7) {
                System.out.println("Wrong choice");
            } else {
                LOCATION_CONTROLLER.displayWeathers(day);
                break;
            }
        } while (!(day >= 0 && day <= 7));
    }

    public static void chooseOneWeatherDate() {

        int day;
        do {
            day = Integer.parseInt(enterString("Enter which day forward you want to check the weathers (max. one week: 0-7)\n[0]. Today\n[1]. Tomorrow\n[2]. Etc."));

            if (day < 0 || day > 7) {
                System.out.println("Wrong choice");
            } else {
                LOCATION_CONTROLLER.showOneWeather(day);
                break;
            }
        } while (day >= 0 && day <= 7);
    }

    public static String enterString(String info) {
        System.out.println(info);
        return scanner.nextLine();
    }

    public static String pattern(){
        return enterString("Enter city name or location ID to edit");
    }
}
