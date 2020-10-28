package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Calendar;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   public void add(Car car) {
      sessionFactory.getCurrentSession().save(car);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<Car> listCars() {
      TypedQuery<Car> query = sessionFactory.getCurrentSession().createQuery("from Car");
      return query.getResultList();
   }

   @Override
   //@SuppressWarnings("unchecked")
   public User findUserByCar(String model, int series) {
      Query query = sessionFactory.openSession()
              .createQuery("from Car where model = :model and series = :series");
      query.setParameter("model", model);
      query.setParameter("series", series);

      Car car = (Car) query.getSingleResult();

      query = sessionFactory.openSession().createQuery("from User where id = :carId");
      query.setParameter("carId", car.getId());

      User user = (User) query.getSingleResult();
      return user;
   }
}
