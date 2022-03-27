package weather.repository;


import weather.model.entity.LocationModelEntity;

import java.util.List;

public interface ILocationRepository {

    void saveLocation(LocationModelEntity locationModelEntity);

    void editLocation(LocationModelEntity locationModelEntity);

    List<LocationModelEntity> getAllLocationModelData();



    LocationModelEntity getAllLocationModelDataByCityNameOrId(String pattern);

    void delateRecord(LocationModelEntity locationModelEntity);
}
