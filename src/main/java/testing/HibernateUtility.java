package testing;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtility {
		public static SessionFactory initializeSession() {
			try {
			Configuration con = new Configuration().configure().addAnnotatedClass(Vehicle.class).addAnnotatedClass(Customer.class).addAnnotatedClass(RateOfVehicle.class);
			SessionFactory sf = con.buildSessionFactory();
			return sf;
			} catch (Exception e) {
				System.out.println("Error during initialization");
				e.printStackTrace();
				
			}
			return null;
		}

}
