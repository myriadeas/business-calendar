package my.com.myriadeas;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDateTime;
import org.joda.time.Minutes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DayBusinessHour {
	private static final Logger logger = LoggerFactory
			.getLogger(DayBusinessHour.class);

	private int dayOfWeek;

	private List<BusinessHour> businessHours;

	/**
	 * 
	 * @param dayOfWeek
	 * @param businessHour
	 *            09:00-12:00&01:00-18:00
	 */
	public DayBusinessHour(int dayOfWeek, String businessHour) {
		if (businessHour != null && !businessHour.equals("")){
			
			String[] aszBusinessHours = businessHour.split("&");
			businessHours = new ArrayList<BusinessHour>();
			for (String szBusinessHour : aszBusinessHours) {
				businessHours.add(new BusinessHour(szBusinessHour));
			}
		}		
	}

	public DayBusinessHour(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
		businessHours = new ArrayList<BusinessHour>();
	}

	public boolean isInBusinessHour(LocalDateTime time) {
		logger.debug("Entering DayBusinessHour.isInBusinessHour(LocalDateTime={})", time);
		for (BusinessHour businessHour : businessHours) {
			logger.debug("BusinessHour = " + businessHour.getStart() + "-" + businessHour.getEnd());
			if (businessHour.isInBusinessHour(time)) {
				return true;
			}
		}
		return false;
	}
	
	public List<BusinessHour> getBusinessHours(){
		return this.businessHours;
	}
	
	public int getTotalBusinessHour(){
		List<BusinessHour> businessHours = getBusinessHours();
		int minutes = 0;
		for (BusinessHour businessHour: businessHours){
			minutes = minutes + Minutes.minutesBetween(businessHour.getStart(), businessHour.getEnd()).getMinutes();
		}
		return minutes;
	}
	
	public int getTotalBusinessHourUntilGivenTime(LocalDateTime localDateTime){
		List<BusinessHour> businessHours = getBusinessHours();
		int minutes = 0;
		for (BusinessHour businessHour : businessHours) {
			if (businessHour.isInBusinessHour(localDateTime)) {
				minutes = minutes + Minutes.minutesBetween(businessHour.getStart(), localDateTime.toLocalTime()).getMinutes();
				break;
			} else if(localDateTime.toLocalTime().isAfter(businessHour.getEnd())){
				minutes = minutes + Minutes.minutesBetween(businessHour.getStart(), businessHour.getEnd()).getMinutes();
			}
		}
		
		return minutes;
	}
	

	/**
	 * 
	 * @param businessHour
	 */
	public void addBusinessHour(BusinessHour businessHour) {
		businessHours.add(businessHour);
	}

	public int getDayOfWeek() {
		return this.dayOfWeek;
	}
}
