package weather.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "location")

public class LocationModelEntity {

    @Id
    @Column(name = "location_id")
    private String location_id;

    @Column(name = "latitude_and_longitude", nullable = false)
    private String latitudeAndLongitude;
    @Column(name = "city_name", nullable = false)
    private String cityName;
    @Column(name = "region", nullable = false)
    private String region;
    @Column(name = "country_name", nullable = false)
    private String countryName;

}
