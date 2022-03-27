package weather.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationModelDTO {

    private String location_id;
    private String latitudeAndLongitude;
    private String cityName;
    private String region;
    private String countryName;

    @Override
    public String toString() {
        return "Location: " + cityName + ", " +  latitudeAndLongitude+ ", " +region + ", " +countryName + ", " +location_id;
    }
}
