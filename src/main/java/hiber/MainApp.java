package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      for (int i = 0; i < 5; i++) {
         Car car = new Car("Model" + i, 100 + i);
         User user = new User("User" + i, "Lastname" + i, "user" + i + "@mail.ru");
         user.setCar(car);
         userService.add(user);
      }

      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         System.out.println();
      }

      for (User user : users) {
         System.out.println("Id = " + user.getCar().getId());
         System.out.println("Model = " + user.getCar().getModel());
         System.out.println("Series = " + user.getCar().getSeries());
         System.out.println();
      }

      System.out.println(userService.findUserByCar("Model3", 103).getFirstName());

      context.close();
   }
}
