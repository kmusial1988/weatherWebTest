package weather.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class WeatherApi {
    private double lon;
    private double lat;
    private MainWeather[] daily;

    @Getter
    @ToString
    public class MainWeather {
        private long dt;
        private Temp temp;
        private float pressure;
        private int humidity;
        private float wind_speed;
        private float wind_deg;

        @Getter
        @ToString
        public class Temp {
            private float day;
        }
    }
}
