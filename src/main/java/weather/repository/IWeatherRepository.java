package weather.repository;

import weather.model.entity.LocationModelEntity;
import weather.model.entity.WeatherModelEntity;

import java.util.List;

public interface IWeatherRepository {

    void saveWeather(WeatherModelEntity weatherModelEntity);

    List<WeatherModelEntity> getAllWeatherModelData();

    WeatherModelEntity getWeatherModelDataByLocation(LocationModelEntity location);

    void deleteRecord(WeatherModelEntity weatherModelEntity);
}
