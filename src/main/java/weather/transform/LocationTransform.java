package weather.transform;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import weather.connection.HibernateUtil;
import weather.model.LocationModelDTO;
import weather.model.entity.LocationModelEntity;

import java.util.ArrayList;
import java.util.List;

public class LocationTransform {

    private static final Logger logger = LogManager.getLogger(HibernateUtil.class);

    public LocationModelDTO locationTransformToView(LocationModelEntity locationModelEntity) {

        LocationModelDTO locationModel = new LocationModelDTO();

        locationModel.setLocation_id(locationModelEntity.getLocation_id());
        locationModel.setLatitudeAndLongitude(locationModelEntity.getLatitudeAndLongitude());
        locationModel.setCityName(locationModelEntity.getCityName());
        locationModel.setRegion(locationModelEntity.getRegion());
        locationModel.setCountryName(locationModelEntity.getCountryName());

        return locationModel;

    }

    public List<LocationModelDTO> translateList(List<LocationModelEntity> list) {

        List<LocationModelDTO> newList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            newList.add(i, locationTransformToView(list.get(i)));
        }

        return newList;

    }


}
