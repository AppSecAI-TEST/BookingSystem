import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Vehicle {
	private String name;
	private Booking booking;
	private String type;
	private String depot;
	
	/***
	 * Class constructor.
	 */
	Vehicle() {
		booking = new Booking();
	}
	
	/***
	 * Class constructor. Initiate Booking, set name size and depot name
	 * 
	 * @param String, String, String
	 */
	Vehicle(String name, String type, String depot) {
		booking = new Booking();
		this.name = name;
		this.type = type;
		this.depot = depot;
	}

	public String getDepot() {
		return depot;
	}

	public String getName() {
		return name;
	}

	public Booking getBooking() {
		return booking;
	}

	public String getType() {
		return type;
	}
	
	/*****
	 * get booking detail in this vehicle
	 * @return String
	 */
	public String getBookingDetail() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm MMM dd");
		String s="";
		List<Date> beginDate = booking.getBeginDate();
		List<Date> endDate = booking.getEndDate();
		//s+="date size "+beginDate.size()+"\n";
		for(int i=0;i<beginDate.size();i++){
			s += depot+" "+name+" "+dateFormat.format(beginDate.get(i))+" "+dateFormat.format(endDate.get(i))+"\n";
		}
		return s;
	}
}
