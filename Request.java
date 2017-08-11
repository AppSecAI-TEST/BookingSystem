

import java.util.*;

public class Request {
	private Date begin;
	private Date end;
	private List<Vehicle> vehicles;
//no hao
	/***
	 * Class constructor.
	 */
	Request() {
		vehicles = new ArrayList<Vehicle>();
	}

	/***
	 * Class constructor.
	 * 
	 * @param Date, Date, List<Vehicle>
	 */
	Request(Date begin, Date end, List<Vehicle> vehicles) {
		this.begin = begin;
		this.end = end;
		this.vehicles = vehicles;
	}

	/**
	 * return all vehicles' names as a string
	 * 
	 * @return String
	 */
	public String requestDetail() {
		String s = "";
		List<String> print_temp= new ArrayList<String>();
		int i=0;
		for (Vehicle vehicle:vehicles) {
			if(print_temp.contains(vehicle.getDepot())){//already in hash
				s+=", "+vehicle.getName();
			}
			else{
				if(i==0){
					s+=" "+vehicle.getDepot()+" "+vehicle.getName();
					print_temp.add(vehicle.getDepot());
				}
				else{
					s+="; "+vehicle.getDepot()+" "+vehicle.getName();
					print_temp.add(vehicle.getDepot());
				}
			}
			i++;
		}
		return s;
	}

	public Date getBegin() {
		return begin;
	}

	public Date getEnd() {
		return end;
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
}
