package my.com.myriadeas;

import java.util.Date;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class BusinessHourApp {

	public static void main(String[] args) {
		LocalDateTime dueDate = new LocalDateTime(
				getDatetime("17/03/2014 10:00:00"));
		LocalDateTime returnDate = new LocalDateTime(
				getDatetime("31/03/2014 10:31:00"));
		WeekBusinessHour weekBusinessHour = getStandard_9amTo6pmWeekBusinessHour();
		int lateBy = getLateBy(weekBusinessHour, dueDate, returnDate);
		System.out
				.println("Standard  9am to 6pm working with Monday to Friday Late by= "
						+ lateBy + " minutes");

		weekBusinessHour = get9amTo6pmWorkingAndLunchBreakFrom1pmTo2pmWeekBusinessHour();
		lateBy = getLateBy(weekBusinessHour, dueDate, returnDate);
		System.out
				.println("Standard  9am to 6pm working and lunch break from 1 pm to 2 pm "
						+ "with Monday to Friday Late by= "
						+ lateBy
						+ " minutes");
		

		dueDate = new LocalDateTime(
				getDatetime("17/03/2014 10:00:00"));
		returnDate = new LocalDateTime(
				getDatetime("31/03/2018 10:31:00"));
		weekBusinessHour = get9amTo6pmWorkingAndLunchBreakFrom1pmTo2pmWeekBusinessHour();
		lateBy = getLateBy(weekBusinessHour, dueDate, returnDate);
		//it is still need to take in consideration of DateCalculator performance also.
		//5 years is extreme example - my computer test is around 2 seconds.
		System.out
				.println("Performance Test - Late for 5 years"
						+ "with Monday to Friday Late by= "
						+ lateBy
						+ " minutes");
	}

	/**
	 * Late by should be object also. must have unit, measurement.
	 * 
	 * @param weekBusinessHour
	 * @param dueDate
	 * @param returnDate
	 * @return
	 */
	public static int getLateBy(WeekBusinessHour weekBusinessHour,
			LocalDateTime dueDate, LocalDateTime returnDate) {
		long start = System.currentTimeMillis();
		int minutes = 0;
		while (dueDate.isBefore(returnDate)) {
			dueDate = dueDate.plusMinutes(1);
			if (weekBusinessHour.isInBusinessHour(dueDate)) {
				minutes++;
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("time taken to calculate " + (end - start)
				+ " (in millis)");
		return minutes;
	}

	public static WeekBusinessHour getStandard_9amTo6pmWeekBusinessHour() {
		WeekBusinessHour weekBusinessHour = new WeekBusinessHour();
		BusinessHour _9amto6pmBusinessHour = new BusinessHour(new LocalTime(
				getDatetime("01/01/2014 09:00:00")), new LocalTime(
				getDatetime("01/01/2014 18:00:00")));
		weekBusinessHour.addBusinessHour(DateTimeConstants.MONDAY,
				_9amto6pmBusinessHour);
		weekBusinessHour.addBusinessHour(DateTimeConstants.TUESDAY,
				_9amto6pmBusinessHour);
		weekBusinessHour.addBusinessHour(DateTimeConstants.WEDNESDAY,
				_9amto6pmBusinessHour);
		weekBusinessHour.addBusinessHour(DateTimeConstants.THURSDAY,
				_9amto6pmBusinessHour);
		weekBusinessHour.addBusinessHour(DateTimeConstants.FRIDAY,
				_9amto6pmBusinessHour);
		return weekBusinessHour;
	}

	public static WeekBusinessHour get9amTo6pmWorkingAndLunchBreakFrom1pmTo2pmWeekBusinessHour() {
		WeekBusinessHour weekBusinessHour = new WeekBusinessHour();
		BusinessHour _9amto1pmBusinessHour = new BusinessHour(new LocalTime(
				getDatetime("01/01/2014 09:00:00")), new LocalTime(
				getDatetime("01/01/2014 13:00:00")));
		BusinessHour _2pmto6pmBusinessHour = new BusinessHour(new LocalTime(
				getDatetime("01/01/2014 14:00:00")), new LocalTime(
				getDatetime("01/01/2014 18:00:00")));
		weekBusinessHour.addBusinessHour(DateTimeConstants.MONDAY,
				_9amto1pmBusinessHour);
		weekBusinessHour.addBusinessHour(DateTimeConstants.MONDAY,
				_2pmto6pmBusinessHour);
		weekBusinessHour.addBusinessHour(DateTimeConstants.TUESDAY,
				_9amto1pmBusinessHour);
		weekBusinessHour.addBusinessHour(DateTimeConstants.TUESDAY,
				_2pmto6pmBusinessHour);
		weekBusinessHour.addBusinessHour(DateTimeConstants.WEDNESDAY,
				_9amto1pmBusinessHour);
		weekBusinessHour.addBusinessHour(DateTimeConstants.WEDNESDAY,
				_2pmto6pmBusinessHour);
		weekBusinessHour.addBusinessHour(DateTimeConstants.THURSDAY,
				_9amto1pmBusinessHour);
		weekBusinessHour.addBusinessHour(DateTimeConstants.THURSDAY,
				_2pmto6pmBusinessHour);
		weekBusinessHour.addBusinessHour(DateTimeConstants.FRIDAY,
				_9amto1pmBusinessHour);
		weekBusinessHour.addBusinessHour(DateTimeConstants.FRIDAY,
				_2pmto6pmBusinessHour);
		return weekBusinessHour;
	}

	private static final DateTimeFormatter STANDARD_DATE_FORMAT = DateTimeFormat
			.forPattern("dd/MM/yyyy");

	private static final DateTimeFormatter STANDARD_DATE_FORMAT_WITHTIME = DateTimeFormat
			.forPattern("dd/MM/yyyy HH:mm:ss");

	/**
	 * Return date with ddMMyyyy format. If format is wrong, return current date
	 * 
	 * @param input
	 *            format - dd/MM/yyyy
	 * @return
	 */
	public static Date getDate(String input) {
		return STANDARD_DATE_FORMAT.parseDateTime(input).toDate();
	}

	/**
	 * 
	 * @param input
	 *            format = dd/MM/yyyy HH:mm:ss. example - 01/01/2001 01:01:01
	 * @return
	 */
	public static Date getDatetime(String input) {
		return STANDARD_DATE_FORMAT_WITHTIME.parseDateTime(input).toDate();
	}
}
