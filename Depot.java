
import java.text.ParseException;
import java.util.*;

public class Depot {
	private List<Vehicle> vehicles;
	private String name;

	/***
	 * Class constructor. initiate an object of vehicles
	 */
	Depot() {
		vehicles = new ArrayList<Vehicle>();
	}

	/**
	 * This is a constructor with a parameter vehicle's name
	 * 
	 * @param String
	 **/
	Depot(String name) {
		vehicles = new ArrayList<Vehicle>();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	/**
	 * add vehicle to list
	 * 
	 * @param Vehicle
	 */
	public void addVehicle(Vehicle vehicle) {
		vehicles.add(vehicle);
	}

	/*****
	 * 
	 * get each vehicle's name and booking detail
	 * 
	 * @throw ParseException
	 * @return String
	 */
	public String getBookingDetail() throws ParseException {
		String result = "";
		//result+=vehicles.size()+"\n";
		for (Vehicle vehicle: vehicles) {
			result+=vehicle.getBookingDetail();
		}
		return result;
	}
}
