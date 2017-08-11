
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class VanRentalSystem {
	private LinkedHashMap<String, Depot> depots;
	private List<Vehicle> vehicles;
	private Hashtable<String, Request> requestHistory;

	/***
	 * Class constructor.
	 */
	VanRentalSystem() {
		depots = new LinkedHashMap<String, Depot>();
		vehicles = new ArrayList<Vehicle>();
		requestHistory = new Hashtable<String, Request>();;
	}
	
	public static void main(String[] args) throws IOException, ParseException {
		VanRentalSystem bookingSystem = new VanRentalSystem();
		bookingSystem.readRequest(args[0]);
	}
	
	/**
	 * 
	 * read one line at one time from file
	 * 
	 * @param String
	 * @throw IOException, ParseException
	 */
	public void readRequest(String filename) throws IOException, ParseException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		try {
			String line;
			while ((line = br.readLine()) != null) {
				String lineRemove = line.split("\\#", 2)[0].trim();
				if(lineRemove!="") readInput(lineRemove);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			br.close();
		}
	}
	
	/***
	 * 
	 * This function is dealing with the request,and dispatch this request into
	 * different method.
	 * 
	 * @param String
	 * 
	 */
	public void readInput(String request) {
		String[] req;
		req = request.split(" ");
		try {
			switch (req[0]) {
			case "Location":
				addVehicle(req[1], req[2], req[3]);
				break;
			case "Request":
				bookVehicle(req);
				break;
			case "Change":
				changeBooking(req);
				break;
			case "Cancel":
				cancelBooking(req);
				break;
			case "Print":
				print(req[1]);
				break;
			default:
				break;

			}
		} catch (Exception e) {
			System.out.println("Exception message: here req"+ e.getMessage());
		}
	}
	
	/****
	 * 
	 * add vehicle according to the parameters
	 * 
	 * @param String,String,String
	 * 
	 */
	public void addVehicle(String depotName, String vehicleName, String vehicleType) {
		Vehicle vehicle = new Vehicle(vehicleName, vehicleType, depotName);
		vehicles.add(vehicle);
		// check if the Depots has been initialized or not
		if (depots.size() == 0) {//if nothing in depot list
			Depot newDepot = new Depot(depotName);
			newDepot.addVehicle(vehicle);
			depots.put(depotName, newDepot);
		} else {//depot already exists
			if (depots.containsKey(depotName)) {
				depots.get(depotName).addVehicle(vehicle);
				return;
			}
			// create a new one if the Depot does not exist in this list
			Depot newdepot = new Depot(depotName);
			newdepot.addVehicle(vehicle);
			depots.put(depotName, newdepot);
		}
	}
	

	/**
	 * 
	 * check if a date is validate
	 * 
	 * @param Date, Date
	 * @return boolean
	 */
	public boolean checkDateValidate(Date begin, Date end) {
		if (begin.compareTo(end) == 1)
			return false;
		return true;
	}	
	
	/**
	 * 
	 * book the request rooms in this venue
	 * 
	 * @param String[], Request
	 * @return boolean
	 * @throw ParseException, IOException
	 */
	public boolean bookVehicle(String[] req) throws ParseException, IOException {
		Date beginDate;
		Date endDate;
		String vehicleType;
		int vehicleNumber;
		LinkedHashMap<String, Vehicle> bookedVehicles = new LinkedHashMap<String, Vehicle>();
		SimpleDateFormat formatter = new SimpleDateFormat("HH MMM d yyyy");		
		// begin date and end date are form 2-5
		beginDate = formatter.parse(req[2] + " " + req[3] + " " + req[4] + " 2017");
		endDate = formatter.parse(req[5] + " " + req[6] + " " + req[7] + " 2017");
		if (!checkDateValidate(beginDate, endDate)){
			if (req[0].equals("Request"))
				System.out.println("Booking rejected");
			return false;
		}
		for (int i = 8; i < req.length; i = i + 2) {// more than two vehicles
			vehicleNumber = Integer.parseInt(req[i]);// get the number of vehicles
			vehicleType = req[i+1];// get the type
			for (int n = 0; n < vehicleNumber; n++) {// for type of vehicle
				boolean findVehicle = false;
				for (int j = 0; j < vehicles.size(); j++) {// for each vehicle
					if (vehicles.get(j).getType().equals(vehicleType)) {// if the type is good
						Vehicle temp = vehicles.get(j);// get booking object
						Booking booking = temp.getBooking();
						if ((!bookedVehicles.containsKey(temp.getName()))&&booking.checkAvaliable(beginDate, endDate)) {// if the date is okay
							bookedVehicles.put(temp.getName(),temp);// add to the booked vehicle list
							findVehicle = true;
							break;
						}
					}
				}
				if (!findVehicle){
					if (req[0].equals("Request"))
						System.out.println("Booking rejected");
					return false;
				}
				
			}
		}
			
		if (bookedVehicles.size() != 0) {
			for (Vehicle vehicle: bookedVehicles.values()) {
				vehicle.getBooking().checkAvaliableBook(beginDate, endDate);
			}
			List<Vehicle> temp_ves_sorted = new ArrayList<Vehicle>();
			for (Vehicle vehicle: vehicles) {
				if(bookedVehicles.containsKey(vehicle.getName())){ 
					temp_ves_sorted.add(vehicle);
				}
			}
			Request request = new Request(beginDate, endDate, temp_ves_sorted);
			requestHistory.put(req[1], request);
			if (req[0].equals("Request"))
				printCurrentResult(req[0], req[1]);
			return true;
		}
		return false;
	}
	
	
	/**
	 * 
	 * print result according to current request
	 * 
	 * @param String, String
	 * @throw IOException
	 */
	public void printCurrentResult(String type, String number) throws IOException {
		if (type.equals("Request"))
			type = "Booking";
		String s = type + " " + number;
		s += requestHistory.get(number).requestDetail();
		System.out.println(s);
	}
	
	/**
	 * 
	 * change booking according to request
	 * 
	 * @param String[]
	 * @return boolean
	 * @throw ParseException, IOException
	 */
	public boolean changeBooking(String[] req) throws ParseException, IOException {
		Request request = requestHistory.get(req[1]);
		if (request == null) {//do not exist
			System.out.println("Change rejected");
			return false;
		}
		List<Vehicle> vehicles = request.getVehicles();
		for (Vehicle vehicle: vehicles) {//cancel all booking
			vehicle.getBooking().cancelBooking(request.getBegin(), request.getEnd());
		}
		//System.out.println("Changeing"+req[1]);
		if (bookVehicle(req)) {//if book success
			printCurrentResult("Change", req[1]);
			return true;
		} else {//if failed, recover former booking
			Request temp = requestHistory.get(req[1]);
			for (Vehicle vehicle: vehicles) {
				vehicle.getBooking().checkAvaliableBook(temp.getBegin(), temp.getEnd());
			}
			System.out.println("Change rejected");
			return false;
		}
	}
	
	/**
	 * 
	 * cancel booking according to request
	 * 
	 * @param String[]
	 * @return boolean
	 * @throw IOException
	 */
	public boolean cancelBooking(String[] req) throws IOException {
		Request request = requestHistory.get(req[1]);
		if (request == null) {//do not exist
			if (req[0].equals("Cancel")) {
				System.out.println("Cancel rejected");
			}
			return false;
		}
		List<Vehicle> vehicles = request.getVehicles();
		for (Vehicle vehicle: vehicles) {
			if (!vehicle.getBooking().cancelBooking(request.getBegin(), request.getEnd())){
				System.out.println("Cancel rejected");//cannot find date
				return false;
			}
		}
		requestHistory.remove(req[1]);
		System.out.println("Cancel " + req[1]);
		return true;
	}
	
	
	/**
	 * 
	 * get venue's booking detail as string and output to console
	 * 
	 * @param String
	 * @throw IOException, ParseException
	 */
	public void print(String depotName) throws ParseException, IOException {
		if (depots.containsKey(depotName)) {
			System.out.print(depots.get(depotName).getBookingDetail());
		}
	}
}
