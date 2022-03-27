package weather.repository.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import weather.connection.HibernateUtil;
import  weather.model.entity.LocationModelEntity;
import  weather.model.entity.WeatherModelEntity;
import weather.repository.IWeatherRepository;

import java.util.Collections;
import java.util.List;

@Repository
public class WeatherRepositoryImpl implements IWeatherRepository {

    @Autowired
    SessionFactory sessionFactory;

    private final Logger logger = LogManager.getLogger(WeatherRepositoryImpl.class);

    @Override
    public void saveWeather(WeatherModelEntity weatherModelEntity) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession();) {
            transaction = session.beginTransaction();
            session.save(weatherModelEntity);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public List<WeatherModelEntity> getAllWeatherModelData() {

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession();) {
            transaction = session.beginTransaction();

            List<WeatherModelEntity> list = session.createQuery("SELECT n FROM WeatherModelEntity AS n", WeatherModelEntity.class)
                    .getResultList();

            transaction.commit();

            return list;
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            logger.error(e.getMessage(), e);
        }

        return Collections.emptyList();

    }

    @Override
    public WeatherModelEntity getWeatherModelDataByLocation(LocationModelEntity location) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession();) {
            transaction = session.beginTransaction();

            WeatherModelEntity model = (WeatherModelEntity) session.createQuery("FROM WeatherModelEntity w WHERE w.location =:location")
                    .setParameter("location", location)
                    .getSingleResult();

            transaction.commit();
            return model;

        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void deleteRecord(WeatherModelEntity weatherModelEntity) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession();) {
            transaction = session.beginTransaction();
            session.delete(weatherModelEntity);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();

            logger.error(e.getMessage(), e);
        }
    }
}
