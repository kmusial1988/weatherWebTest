package weather.controllers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import weather.Gui;
import weather.connection.HibernateUtil;
import weather.model.WeatherModelDTO;
import weather.model.entity.LocationModelEntity;
import weather.model.entity.WeatherModelEntity;
import weather.repository.ILocationRepository;
import weather.service.ILocationService;
import weather.service.IWeatherService;
import weather.session.SessionObject;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Controller
public class ViewControllerWeb {

    @Autowired
    ILocationService locationService;

    @Autowired
    IWeatherService weatherService;

    @Resource
    SessionObject sessionObject;

    @Autowired
    ILocationRepository locationRepository;

    private static final Logger logger = LogManager.getLogger(HibernateUtil.class);

    @RequestMapping(value = "/weatherServiceMain", method = RequestMethod.GET)
    public String main() {

        this.sessionObject.setLastAddress("/weatherServiceMain");
        return "weatherServiceMain";

    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String defaultRedirect() {
        return "redirect:/weatherServiceMain";
    }


//    @RequestMapping(value = "/find", method = RequestMethod.POST)
//    public String findBooks(Model model, @RequestParam String pattern) {
//        model.addAttribute("isLogged", (sessionObject.getUser() != null));
//        List<Book> books = this.bookService.findBooks(pattern);
//        this.sessionObject.setLastFindPattern(pattern);
//        model.addAttribute("books", books);
//        this.sessionObject.setLastAddress("/find");
//        return "main";
//    }

//    @RequestMapping(value = "/find", method = RequestMethod.GET)
//    public String findBooksAfterRedirect(Model model) {
//        model.addAttribute("isLogged", (sessionObject.getUser() != null));
//        List<Book> books = this.bookService.findBooks(sessionObject.getLastFindPattern());
//        model.addAttribute("books", books);
//        this.sessionObject.setLastAddress("/find");
//        return "main";
//    }

    @RequestMapping(value = "/All", method = RequestMethod.GET)
    public String allLocation(Model model) {
        List<LocationModelEntity> locationModel = this.locationService.getAllLocation();
        model.addAttribute("locations", locationModel);

        this.sessionObject.setLastAddress("/All");
        return "All";
    }
//TODO
    @RequestMapping(value = "/Weather", method = RequestMethod.GET)
    public String allWeather(Model model) {

        List<WeatherModelEntity> weatherModelEntities = this.weatherService.getAllWeathers();
        List<LocationModelEntity> locationModel = this.locationService.getAllLocation();
        model.addAttribute("weathers", weatherModelEntities);
        model.addAttribute("locations", locationModel);

        try {
            weatherService.listWeathers(locationModel, 1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }


        return "Weather";
    }


    @RequestMapping(value = "/Add", method = RequestMethod.GET)
    public String addLocation(Model model) {

        model.addAttribute("info", this.sessionObject.getInfo());
        model.addAttribute("location", new LocationModelEntity());

        return "Add";

    }

    @RequestMapping(value = "/Add", method = RequestMethod.POST)
    public String addLocation(@ModelAttribute LocationModelEntity locationModelEntity) {


        if (locationService.isLocationExiest(locationModelEntity)) {
            this.sessionObject.setInfo("Lokalizacja Istnieje !!!");
        } else {
            if (locationModelEntity.getCityName().equals("") || locationModelEntity.getLatitudeAndLongitude().equals("")
                    || locationModelEntity.getRegion().equals("") || locationModelEntity.getCountryName().equals("")) {
                this.sessionObject.setInfo("Uzupełnij formularz!!!");
            } else {
                String idUUID = String.valueOf(UUID.randomUUID());
                String longitudeAndLatitude = locationModelEntity.getLatitudeAndLongitude();
                String cityName = locationModelEntity.getCityName();
                String region = locationModelEntity.getRegion();
                String countryName = locationModelEntity.getCountryName();

                this.locationService.saveLocationModel(
                        new LocationModelEntity(idUUID, longitudeAndLatitude, cityName,region,countryName));
                this.sessionObject.setInfo("Dodano");
            }
        }
        return "redirect:/All";


    }

    @RequestMapping(value = "/Edit/{name}", method = RequestMethod.GET)
    public String editLocation(@PathVariable String name, Model model) {

        LocationModelEntity locationModelEntity = this.locationService.getLocationByIdAndName(name);
        model.addAttribute("location", locationModelEntity);

        return "Edit";
    }

    @RequestMapping(value = "/Edit/{name}", method = RequestMethod.POST)
    public String editLocationModel(@ModelAttribute LocationModelEntity locationModelEntity, @PathVariable String name) {


        LocationModelEntity location = this.locationService.getLocationByIdAndName(name);

        location.setLocation_id(location.getLocation_id());
        location.setCityName(locationModelEntity.getCityName());
        location.setLatitudeAndLongitude(locationModelEntity.getLatitudeAndLongitude());
        location.setRegion(locationModelEntity.getRegion());
        location.setCountryName(locationModelEntity.getCountryName());

        this.locationRepository.editLocation(location);

        return "redirect:/All";

    }

    @RequestMapping(value = "/Remove/{name}", method = RequestMethod.GET)
    public String removeLocation(@PathVariable String name){


        for(LocationModelEntity locationModel : this.locationService.getAllLocation()){
            if(locationModel.getCityName().equals(name)){
                this.locationService.delateLocationOnList(name);
                return "redirect:/All";
            }
        }

        return "redirect:/All";
    }
}
