package my.com.myriadeas;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

public class BusinessHour {

	private static final String HOUR_MINUTE_DELIMITER = ":";

	private static final String SLOT_DELIMITER = "-";

	private LocalTime start;

	private LocalTime end;

	public boolean isInBusinessHour(LocalDateTime time) {
		LocalTime hour = new LocalTime(time);
		return ((hour.isAfter(start) && hour.isBefore(end)) ||
				(hour.getHourOfDay() == start.getHourOfDay() && hour.getMinuteOfHour() == start.getMinuteOfHour()) || 
				(hour.getHourOfDay() == end.getHourOfDay() && hour.getMinuteOfHour() == end.getMinuteOfHour()));
	}
	
	/**
	 * 
	 * @param businessHour
	 *            - time slot from 9am to 6pm should write as 09:00-18:00
	 */
	public BusinessHour(String businessHour) {
		this(getStartTime(businessHour), getEndTime(businessHour));
	}

	public BusinessHour(LocalTime start, LocalTime end) {
		if (start.isAfter(end)) {
			throw new IllegalArgumentException(
					"End time must after start time. If it is overnight,"
							+ " set for next day");
		}
		this.start = start;
		this.end = end;

	}

	protected static LocalTime getStartTime(String businessHour) {
		return createLocalTime(businessHour.split(SLOT_DELIMITER)[0]);
	}

	protected static LocalTime getEndTime(String businessHour) {
		return createLocalTime(businessHour.split(SLOT_DELIMITER)[1]);

	}

	protected static LocalTime createLocalTime(String time) {
		String[] times = time.split(HOUR_MINUTE_DELIMITER);
		LocalTime localTime = new LocalTime(Integer.parseInt(times[0]),
				Integer.parseInt(times[1]));
		return localTime;
	}

	public LocalTime getStart() {
		return start;
	}

	public LocalTime getEnd() {
		return end;
	}

	
}
