package testing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import ExpensesModel.Expenses;

public class DaoOperations {
	public static SessionFactory sf = HibernateUtility.initializeSession();
	static String value="";
	static Scanner sc= new Scanner(System.in);
	static int countB=0,countT=0,countTX=0,countSC=0,countBK=0;
	
	/**
	 * This method will populate the rate of vehicles and store the data into the database.
	 */
	public static void populateRateOfVehicle() {
		
		Session session0 = null;
		Transaction tx0 = null; 
		
		RateOfVehicle[] rateVehicle  = {
	                new RateOfVehicle("BUS", 100),
	                new RateOfVehicle("TRUCK", 150),
	                new RateOfVehicle("TAXI", 60),
	                new RateOfVehicle("SCOOTY", 30),
	                new RateOfVehicle("BIKE", 40)
	        };
		
		session0 = sf.openSession();
		tx0= session0.beginTransaction();
		
		for(RateOfVehicle rr: rateVehicle) {
				session0.save(rr);
			}
		tx0.commit();
		session0.close();
		
	}
	
	public static void bookVehicle() {
		Session session1 = null;
		Transaction tx1 = null;
		
		String vehicleId = null;
		String vehicleType = null;		
	//1.1 Vehicles data scanning	
			do {	
				int counter=0;
				if (counter<3) {
					counter++;
					System.out.println("Enter the vehicle type (Input from these five units: Bus/Truck/Taxi/Scooty/Bike)");
					vehicleType = sc.next().toUpperCase();	
					try {
						vehicleId= generatedVehicleID(vehicleType);
						System.out.println("Genereated Vehicle ID is: "+ vehicleId);	
					} catch (IllegalArgumentException e) {
						System.out.println("Invalid vehicle type.Try again!!");					
					}
				} else {
					System.out.println();
					System.out.println("System Failure!! Multiple invalid inputs!");
					System.exit(0);
				}
			}while (vehicleId == null);						
			System.out.println("Enter vehicle mileage");
			double mileage = sc.nextDouble();
			Date parsedDate = dateValidationMethod();	
			System.out.println("Enter gear type: ");
			String gearType = sc.next();	
			
	//1.1 Vehicle object creation
			Vehicle vehicle = new Vehicle();
			vehicle.setTypeOfVehicle(vehicleType);
			vehicle.setVehicleId(vehicleId);
			vehicle.setDateOfPurchase(parsedDate);
			vehicle.setMilageOfVehicle(mileage);
			vehicle.setGeartype(gearType);

	//1.1.1 Customer data scanning			
			System.out.println("Enter Customer Id");
			String customerId=sc.next();
			System.out.println("Enter customer name: ");
			String custName = sc.next();     
		    String userInputMobileAterValidation = mobileValidationMethod();	        
			String userInputEmailAfterValidation = emailValidationMethod();		
			String userInputDrivingLicenseValidation = licenseValidationMethod();	
			System.out.println("Enter the duration of rent (in hours)");
			int duration = sc.nextInt();
	
		try {
			session1 = sf.openSession();
			tx1 = session1.beginTransaction();
			
			//The method to calculate the rate of vehicle
			int rentalCostOfVehicle= calculateRateOfVehicle(vehicle, session1, duration);
	
			//2.1.Customer object creation
			Customer customer = new Customer();
			customer.setCustId(customerId);
			customer.setCustName(custName);
			customer.setMob(userInputMobileAterValidation);
			customer.setEmail(userInputEmailAfterValidation);
			customer.setDrivingLicense(userInputDrivingLicenseValidation);
			customer.setDurationForRent(duration);
			customer.setCostOfVehicle(rentalCostOfVehicle);
		
			//The method to calculate the rate of vehicle
		
			customer.setVehicle(vehicle);
			vehicle.setCustomer(customer);

			session1.save(vehicle);
			session1.save(customer);
			tx1.commit();
			
			System.out.println("The data of Vehicle and Customer successfully saved in the Database!");
			
		} catch(Exception e) {
			e.printStackTrace();
			tx1.rollback();
		} finally {
			if(session1!=null) {
			session1.close();
			}		
		}
		
	}
	
	private static String generatedVehicleID(String vehicleType) {
		switch(vehicleType) {
		case "BUS":
			countB++;
			if(countB<10) {
				return "B" + "0"+ countB;
			}else{
				return "B" + countB;
			}		
		case "TRUCK":
			countT++;
			if(countT<10) {
				return "T" + "0"+ countT;
			}else{
				return "T" + countT;
			}
		case "TAXI":
			countTX++;
			if(countTX<10) {
				return "TX" + "0"+ countTX;
			}else{
				return "TX" + countTX;
			}
		case "SCOOTY":
			countSC++;
			if(countSC<10) {
				return "SC" + "0"+ countSC;
			}else{
				return "SC" + countSC;
			}
		case "BIKE":
			countBK++;
			if(countBK<10) {
				return "B" + "0"+ countBK;
			}else{
				return "B" + countBK;
			}
		default:
				throw new IllegalArgumentException("Invalid vehicle type: "+ vehicleType);		
		}

	}

	
	/**This method will accepts the input of date in the form of String by the user in the format (yyyy-MM-dd)
	 * @return parsedDate
	 */
	public static Date dateValidationMethod() {
		
		
		System.out.println("Enter date of purchase (yyyy-MM-dd): ");
		String dateOfPurchase = sc.next();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
			try {
				date =simpleDateFormat.parse(dateOfPurchase);  	
			}catch(ParseException e) {
				System.out.println("Invalid date-time format: " + e.getMessage());
			}
		return date;
	}
	
	/**This method will validate the input of email given by the user, but limited to 3 times.
	 * If 3 entries could not satisfy the condition, the program will terminate
	 * @return userInputEmail;
	 */
	public static String emailValidationMethod() {	
		 final String regex = "^[A-Za-z0-9+_.-]+@(.+)$";  
         Pattern pattern = Pattern.compile(regex);  
         String userInputEmail="";
         boolean emailCheck;
         int count = 0;
         	do {
			 System.out.println("Enter a valid customer email (Eg: abc123@yahoo.com)");
			 userInputEmail = sc.next();
			 Matcher matcher = pattern.matcher(userInputEmail);
			 emailCheck = matcher.matches();
			 	if (emailCheck) {
			 		System.out.println("Email validating.....");
			 		System.out.println("This is a valid email!");
			 		System.out.println();
			 		break;
			 		} else {
			 			if (count<2) {
			 				System.out.println("This is a invalid email.");	
			 				count++;
			 			}else {
			 				System.out.println("Invalid email entry multiple times!!");
			 				count++;
			 				break;
			 			}
			 		}
			} while (count<3);
         
         	if(count == 3 ) {
         		System.out.println("System Failure!!");
         		System.exit(0);
 			}	
         return userInputEmail;
	}
	
	/**This method will validate the input of mobile no. given by the user, but limited to 3 times.
	 * If 3 entries could not satisfy the condition, the program will terminate
	 * @return userInputMobile;
	 */
	public static String mobileValidationMethod() {	
		final String regex = "^[\\\\+]?[(]?[0-9]{3}[)]?[-\\\\s\\\\.]?[0-9]{3}[-\\\\s\\\\.]?[0-9]{4,6}$";  
        Pattern pattern = Pattern.compile(regex);  
        String userInputMobile="";
        boolean mobileCheck;
        int count = 0;
        	do {
			 System.out.println("Enter a valid mobile no.(Eg: 319-122-1223)");
			 userInputMobile = sc.next();
			 Matcher matcher = pattern.matcher(userInputMobile);
			 mobileCheck = matcher.matches();
			 	if (mobileCheck) {
			 		System.out.println("Mobile no. validating.....");
			 		System.out.println("This is a valid mobile no.!");
			 		System.out.println();
			 		break;
			 		} else {
			 			if (count<2) {
			 				System.out.println("This is a invalid mobile no.");	
			 				count++;
			 			}else {
			 				System.out.println("Invalid mobile no. entry multiple times!!");
			 				count++;
			 				break;
			 			}
			 		}
			} while (count<3);
        
        	if(count == 3 ) {
        		System.out.println("System Failure!!");
        		System.exit(0);
        	}
        return userInputMobile;
	}
	
	/**This method will validate the input of license given by the user, but limited to 3 times.
	 * If 3 entries could not satisfy the condition, the program will terminate
	 * @return userInputLicense
	 */
	public static String licenseValidationMethod() {
		
		final String regex = "^[A-Z0-9]{5}-[A-Z0-9]{5}-[A-Z0-9]{5}$";
		 Pattern pattern = Pattern.compile(regex);  
	        String userInputLicense="";
	        boolean licenseCheck;
	        int count = 0;
        	do {
			 System.out.println("Enter a valid license key (Eg: ABCDE-AB2345-FGHIJ)");
			 userInputLicense = sc.next().toUpperCase();
			 Matcher matcher = pattern.matcher(userInputLicense);
			 licenseCheck = matcher.matches();
			 	if (licenseCheck) {
			 		System.out.println("License key validating.....");
			 		System.out.println("This is a valid License Key!");
			 		System.out.println();
			 		break;
			 		} else {
			 			if (count<2) {
			 				System.out.println("This is a invalid license key");	
			 				count++;
			 			}else {
			 				System.out.println("Invalid license key entry multiple times!!");
			 				count++;
			 				break;
			 			}
			 		}
			} while (count<3);
        
        	if(count == 3 ) {
        		System.out.println("System Failure!!");
        		System.exit(0);
			}
        return userInputLicense;	
	}
	
	public static int calculateRateOfVehicle(Vehicle vehicle, Session session1, int duration) {
		
		RateOfVehicle rov = new RateOfVehicle();
		int rentalCost =0;
		
		List<RateOfVehicle> rateRows= null;
		
		Query query = session1.createQuery("from RateOfVehicle");
		rateRows = query.list();
		
		System.out.println("The details of all the rate/cost record(s) is/are mentioned below: ");
		for(RateOfVehicle rr : rateRows) {
			System.out.print(rr.getTypeOfVehicle() + " | " +rr.getRate());
			System.out.println();
		}
		
		for(RateOfVehicle rr : rateRows) {
			if(vehicle.getTypeOfVehicle().equals(rr.getTypeOfVehicle())) {
				rentalCost = (rr.getRate() * duration);
				System.out.println("Booking successful!! The Rental Cost for this booking is: " + rentalCost);
				break;
			}
		}
			
		return rentalCost;
		
	}			
}
