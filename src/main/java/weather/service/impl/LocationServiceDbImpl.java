package weather.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weather.connection.HibernateUtil;
import weather.model.entity.LocationModelEntity;
import weather.model.entity.WeatherModelEntity;
import weather.repository.ILocationRepository;
import weather.repository.IWeatherRepository;
import weather.service.ILocationService;
import weather.service.IWeatherService;

import java.util.List;

@Service
public class LocationServiceDbImpl implements ILocationService {

    @Autowired
    ILocationRepository locationRepository;
    @Autowired
    IWeatherRepository weatherRepository;
    @Autowired
    IWeatherService weatherService;

    private static final Logger logger = LogManager.getLogger(HibernateUtil.class);

    @Override
    public boolean isLocationExiest(LocationModelEntity model) {
        List<LocationModelEntity> list = locationRepository.getAllLocationModelData();
        for (LocationModelEntity location : list) {
            if (location.getLatitudeAndLongitude().equals(model.getLatitudeAndLongitude()) || location.getCityName().equals(model.getCityName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void cleanRecords() {
        List<LocationModelEntity> list = locationRepository.getAllLocationModelData();
        for (LocationModelEntity location : list) {
            this.locationRepository.delateRecord(location);
        }
    }

    @Override
    public void delateLocationOnList(String pattern) {

        LocationModelEntity location = getLocationByIdAndName(pattern);
        if (location.getLocation_id() == null) {
            return;
        }
        WeatherModelEntity weather = weatherService.getWeatherByLocation(location);
        this.weatherRepository.deleteRecord(weather);
        this.locationRepository.delateRecord(location);


    }

    @Override
    public void saveLocationModel(LocationModelEntity locationModelEntity) {
        this.locationRepository.saveLocation(locationModelEntity);

    }

    @Override
    public List<LocationModelEntity> getAllLocation() {

        return this.locationRepository.getAllLocationModelData();
    }

    @Override
    public LocationModelEntity getLocationByIdAndName(String patternToSearch) {

        LocationModelEntity locationModel = new LocationModelEntity();
        try {
            locationModel = locationRepository.getAllLocationModelDataByCityNameOrId(patternToSearch);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return locationModel;
    }

    @Override
    public void editLocation(String whatEdit, String pattern, String editData) {

        LocationModelEntity location = getLocationByIdAndName(pattern);

            if (location != null && isLocationExiest(location)) {
                if (whatEdit.equals("cityName")) {
                    location.setCityName(editData);

                }
                if (whatEdit.equals("countryName")) {
                    location.setCountryName(editData);

                }
                if (whatEdit.equals("regionName")) {

                    location.setRegion(editData);
                }
                if (whatEdit.equals("coordinates")) {
                    location.setLatitudeAndLongitude(editData);
                }

                locationRepository.editLocation(location);
            } else {
                System.out.println("Wrong location");

            }

    }
}
