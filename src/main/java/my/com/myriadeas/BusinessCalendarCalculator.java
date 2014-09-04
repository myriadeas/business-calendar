package my.com.myriadeas;

import java.util.Date;

import org.joda.time.LocalDateTime;

import net.objectlab.kit.datecalc.joda.LocalDateCalculator;

public class BusinessCalendarCalculator extends LocalDateCalculator implements
		BusinessHourCalendar {
	

	private WeekBusinessHour weekBusinessHour;

	public BusinessCalendarCalculator(LocalDateCalculator localDateCalculator){
		super(
				localDateCalculator.getName(), 
				localDateCalculator.getStartDate(), 
				localDateCalculator.getHolidayCalendar(), 
				localDateCalculator.getHolidayHandler()
				);
	}
	
	public Boolean isInBusinessHour(Date date) {
		return isInBusinessHour(new LocalDateTime(date));
	}

	public Boolean isInBusinessHour(LocalDateTime date) {
		if (this.isNonWorkingDay(date.toLocalDate())) {
			return false;
		} else {
			return this.weekBusinessHour.isInBusinessHour(date);
		}
	}

	public void setWeekBusinessHour(WeekBusinessHour weekBusinessHour) {
		this.weekBusinessHour = weekBusinessHour;
	}
	
	public WeekBusinessHour getWeekBusinessHour() {
		return this.weekBusinessHour;
	}

}
