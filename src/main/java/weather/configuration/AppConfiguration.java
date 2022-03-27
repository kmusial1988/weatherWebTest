package weather.configuration;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;
import weather.session.SessionObject;


@Configuration
@ComponentScan("weather")
public class AppConfiguration {

    @Bean
    public SessionFactory sessionFactory() {
        return new org.hibernate.cfg.Configuration()
                .configure().buildSessionFactory();
    }
    @Bean
    @SessionScope
    public SessionObject sessionObject() {
        return new SessionObject();
    }

}
