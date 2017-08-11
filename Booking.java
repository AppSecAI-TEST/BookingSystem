
import java.util.*;
import java.util.Date;


public class Booking {
    public final static long SECOND_MILLIS = 1000;
    public final static long HOUR_MILLIS = SECOND_MILLIS*60*60;
	private List<Date> beginDate;
	private List<Date> endDate;

	/***
	 * Class constructor.
	 */
	Booking(){
		beginDate = new ArrayList<Date>();
		endDate = new ArrayList<Date>();
	}
	
	/***
	 * Class constructor. Initiate Booking, set name size and venue name
	 * 
	 * @param Date, Date
	 */
	Booking(Date begin, Date end){
		beginDate.add(begin);
		endDate.add(end);		
	}
	
	/**
	 * check if this room is avalible for the date and book if so
	 * @param Date, Date
	 * @return boolean
	 */
	public boolean checkAvaliableBook(Date beginDa, Date endDa){
		//no booking
		if(beginDate.size()==0||endDate.size()==0){
			addBooking(beginDa, endDa, 0);
			return true;
		}
		//before the date list
		if(hoursDiff(endDa, beginDate.get(0)) >= 1){
			addBooking(beginDa, endDa, 0);
			return true;
		}
		//after the date list
		if(hoursDiff(endDate.get(endDate.size()-1),beginDa) >= 1){
			addBooking(beginDa, endDa, beginDate.size());
			return true;
		}
		//if it's not the last interval, check if the begin date is bigger than the end date and end date is smaller than 
		//the next begin date 
		//System.out.println(beginDa+" "+endDa);
		for(int i=0; i<(beginDate.size()-1); i++){
			//between the date list
			//System.out.println(endDate.get(i)+" with "+beginDa);
			//System.out.println(beginDate.get(i+1)+" with "+endDa);
			if((hoursDiff(endDate.get(i),beginDa) >= 1) && (hoursDiff(endDa,beginDate.get(i+1)) >= 1)){
				addBooking(beginDa, endDa, i+1);
				//System.out.println(beginDa+" booking "+endDa);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * check if this room is avalible for the date and book if so
	 * @param Date, Date
	 * @return boolean
	 */
	public boolean checkAvaliable(Date beginDa, Date endDa){
		//no booking
		if(beginDate.size()==0||endDate.size()==0){
			return true;
		}
		//before the date list
		if(hoursDiff(endDa, beginDate.get(0)) >= 1){
			//System.out.println("first" +beginDa);
			return true;
		}
		//after the date list
		if(hoursDiff(endDate.get(endDate.size()-1),beginDa) >= 1){
			//System.out.println("last" +beginDa);
			return true;
		}	
		//if it's not the last interval, check if the begin date is bigger than the end date and end date is smaller than 
		//the next begin date 
		for(int i=0; i<(beginDate.size()-1); i++){
			//between the date list
			if((hoursDiff(endDate.get(i),beginDa) >= 1) && (hoursDiff(endDa,beginDate.get(i+1)) >= 1)){
				//System.out.println("middle" +beginDa);
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * add the booking dates to date lists
	 * @param Date, Date, int
	 */
	public void addBooking(Date beginDa, Date endDa, int position){
		if(position == beginDate.size()){
			beginDate.add(beginDa);
			endDate.add(endDa);
		}
		else{
			beginDate.add(position, beginDa);
			endDate.add(position, endDa);
		}
	}
	
	/**
	 * cancel passed in dates from the list
	 * @param Date, Date
	 * @return boolean
	 */
	public boolean cancelBooking(Date beginDa, Date endDa){
		Iterator<Date> iteBegin = beginDate.iterator();
		Iterator<Date> iteEnd = endDate.iterator();	
		Date temp_begin;
		Date temp_end;
		iteBegin = beginDate.iterator();
		iteEnd = endDate.iterator();	
		while (iteBegin.hasNext() && iteEnd.hasNext()) {
			temp_begin = iteBegin.next();
			temp_end = iteEnd.next();
			if(temp_begin.compareTo(beginDa)==0 && temp_end.compareTo(endDa)==0){
				iteBegin.remove();
				iteEnd.remove();
				return true;
			}
		}
		return false;
	}
	
	/**
	 * get the dates in this room
	 * @return String
	 */
	public List<Date> getBeginDate(){
		return beginDate;
	}
	
	/**
	 * get the dates in this room
	 * @return String
	 */
	public List<Date> getEndDate(){
		return endDate;
	}
	
    /**
     * Get the hours difference
     */
    public static int hoursDiff( Date earlierDate, Date laterDate )
    {
        int time =  (int)((laterDate.getTime()/HOUR_MILLIS) - (earlierDate.getTime()/HOUR_MILLIS));       
        return time;
    }
    
}
