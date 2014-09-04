package my.com.myriadeas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeekBusinessHour {
	private static final Logger logger = LoggerFactory
			.getLogger(WeekBusinessHour.class);

	private DayBusinessHour SUN = new DayBusinessHour(DateTimeConstants.SUNDAY);
	private DayBusinessHour MON = new DayBusinessHour(DateTimeConstants.MONDAY);
	private DayBusinessHour TUE = new DayBusinessHour(DateTimeConstants.TUESDAY);
	private DayBusinessHour WED = new DayBusinessHour(
			DateTimeConstants.WEDNESDAY);
	private DayBusinessHour THU = new DayBusinessHour(
			DateTimeConstants.THURSDAY);
	private DayBusinessHour FRI = new DayBusinessHour(DateTimeConstants.FRIDAY);
	private DayBusinessHour SAT = new DayBusinessHour(
			DateTimeConstants.SATURDAY);

	private Map<Integer, DayBusinessHour> weekBusinessHours = new HashMap<Integer, DayBusinessHour>();

	/**
	 * Use default working week - MON to FRI working only
	 */
	public WeekBusinessHour() {
		init();
	}

	/**
	 * 
	 * @param mapWeekBusinessHour
	 *            - MONDAY:[09:00-18:00], TUESDAY:[09:00-12:00&13:00-18:00]
	 */
	public WeekBusinessHour(Map<Integer, String> mapWeekBusinessHour) {
		this();
		for (Entry<Integer, String> weekBusinessHour : mapWeekBusinessHour
				.entrySet()) {
			int dayOfWeek = weekBusinessHour.getKey();
			String szDayBusinessHour = weekBusinessHour.getValue();
			DayBusinessHour dayBusinessHour = new DayBusinessHour(dayOfWeek,
					szDayBusinessHour);
			weekBusinessHours.put(dayOfWeek, dayBusinessHour);
		}
	}

	private void init() {
		weekBusinessHours.put(SUN.getDayOfWeek(), SUN);
		weekBusinessHours.put(MON.getDayOfWeek(), MON);
		weekBusinessHours.put(TUE.getDayOfWeek(), TUE);
		weekBusinessHours.put(WED.getDayOfWeek(), WED);
		weekBusinessHours.put(THU.getDayOfWeek(), THU);
		weekBusinessHours.put(FRI.getDayOfWeek(), FRI);
		weekBusinessHours.put(SAT.getDayOfWeek(), SAT);
	}

	/**
	 * Again without any validation of the input
	 * 
	 * @param dayOfWeek
	 * @param businessHour
	 */
	public void addBusinessHour(int dayOfWeek, BusinessHour businessHour) {
		weekBusinessHours.get(dayOfWeek).addBusinessHour(businessHour);
	}

	public boolean isInBusinessHour(LocalDateTime time) {
		logger.debug("Entering WeekBusinessHour.isInBusinessHour(LocalDateTime={})", time);
		return weekBusinessHours.get(time.getDayOfWeek()).isInBusinessHour(
				time);
		/*
		if (isHoliday(time)) {
			return false;
		} else {
			return weekBusinessHours.get(time.getDayOfWeek()).isInBusinessHour(
					time);
		}
		*/
		
	}
	
	public List<BusinessHour> getBusinessHours(LocalDateTime localDateTime){		
		if (isInBusinessHour(localDateTime)){
			return weekBusinessHours.get(localDateTime.getDayOfWeek()).getBusinessHours();
		} else {
			throw new NotInBusinessHourException("Not in business hour");
		}
	}
	
	public int getTotalBusinessHoursInMinute(LocalDateTime localDateTime){		
		if (isInBusinessHour(localDateTime)){
			return weekBusinessHours.get(localDateTime.getDayOfWeek()).getTotalBusinessHour();
		} else {
			throw new NotInBusinessHourException("Not in business hour");
		}
	}
	
	public int getTotalBusinessHourUntilGivenTime(LocalDateTime localDateTime){		
		if (isInBusinessHour(localDateTime)){
			return weekBusinessHours.get(localDateTime.getDayOfWeek()).getTotalBusinessHourUntilGivenTime(localDateTime);
		} else {
			throw new NotInBusinessHourException("Not in business hour");
		}
	}
	
	

	/**
	 * Should check with object lab kit calendar
	 * 
	 * @param dateTime
	 * @return
	 */
	private boolean isHoliday(LocalDateTime dateTime) {
		
		return false;
	}

}
