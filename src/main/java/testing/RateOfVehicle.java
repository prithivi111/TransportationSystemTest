package testing;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RateOfVehicle {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int RatingId;
	
	@Column(name = "vehicle_type", unique = true)
	private String typeOfVehicle;
	
	@Column(name ="rate")
	private int rate;

		
	public RateOfVehicle(String typeOfVehicle, int rate) {
		super();
		this.typeOfVehicle = typeOfVehicle;
		this.rate = rate;
	}

	public RateOfVehicle() {
	
	}

	public String getTypeOfVehicle() {
		return typeOfVehicle;
	}

	public void setTypeOfVehicle(String typeOfVehicle) {
		this.typeOfVehicle = typeOfVehicle;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}
	
	

}
