package weather.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherModelDTO {

    private Float temperature;
    private Float pressure;
    private Integer humidity;
    private Float windDir;
    private Float windSpeed;
    private Date date;
    private LocationModelDTO location;

    @Override
    public String toString() {
        return "Weather for " + location +
                " for " + date +
                " is:\n\t\t\t\t\t  temperature = " + temperature +
                ", pressure = " + pressure +
                ", humidity = " + humidity +
                ", wind direction = " + windDir +
                ", wind speed = " + windSpeed;
    }
}
