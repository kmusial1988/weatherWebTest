package weather.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "weather")

public class WeatherModelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weathers_generator")
    @SequenceGenerator(name = "weathers_generator", sequenceName = "weathers_weather_id_seq", allocationSize = 1)
    @Column(name = "weather_id")
    private Integer id;

    @Column(name = "temperature", nullable = false)
    private Float temperature;
    @Column(name = "pressure", nullable = false)
    private Float pressure;
    @Column(name = "humidity", nullable = false)
    private Integer humidity;
    @Column(name = "windDir", nullable = false)
    private Float windDir;
    @Column(name = "windSpeed", nullable = false)
    private Float windSpeed;
    @Column(name = "date", nullable = false)
    private Date date;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "location_id", nullable = false)
    private LocationModelEntity location;
}
