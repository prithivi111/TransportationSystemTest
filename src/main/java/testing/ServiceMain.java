package testing;


public class ServiceMain {
	
	public static void main(String[] args) {
		
		//Populating the rate of vehicles
		DaoOperations.populateRateOfVehicle();
		
		//BookingVehicles
		DaoOperations.bookVehicle();
	
	}
	

}
