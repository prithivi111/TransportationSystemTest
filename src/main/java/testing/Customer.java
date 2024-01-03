package testing;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table (name="customer")
public class Customer {
	
	@Id
	@Column(name = "cust_id")
	private String custId;
	
	@Column(name = "cust_name")
	private String custName;
	
	@Column(name = "mobile_no")
	private String mob;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "driving_license")
	private String drivingLicense;
	
	@Column(name = "duration_Of_rent")
	private int durationForRent;
	
	@Column(name ="rate_of_vehicle")
	private int costOfVehicle;
	
	public int getCostOfVehicle() {
		return costOfVehicle;
	}

	public void setCostOfVehicle(int costOfVehicle) {
		this.costOfVehicle = costOfVehicle;
	}
	@OneToOne(mappedBy = "customer",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Vehicle vehicle;
	

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Customer() {

	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getMob() {
		return mob;
	}

	public void setMob(String mob) {
		this.mob = mob;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDrivingLicense() {
		return drivingLicense;
	}
	public void setDrivingLicense(String drivingLicense) {
		this.drivingLicense = drivingLicense;
	}
	public int getDurationForRent() {
		return durationForRent;
	}
	public void setDurationForRent(int durationForRent) {
		this.durationForRent = durationForRent;
	}
		

}
