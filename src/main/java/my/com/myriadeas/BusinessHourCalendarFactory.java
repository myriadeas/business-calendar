package my.com.myriadeas;

import org.joda.time.LocalDate;

import net.objectlab.kit.datecalc.common.HolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;
import net.objectlab.kit.datecalc.common.WorkingWeek;
import net.objectlab.kit.datecalc.joda.JodaWorkingWeek;
import net.objectlab.kit.datecalc.joda.LocalDateCalculator;
import net.objectlab.kit.datecalc.joda.LocalDateKitCalculatorsFactory;

public class BusinessHourCalendarFactory extends LocalDateKitCalculatorsFactory {
	
	//private static final BusinessHourCalendarFactory BUSINESS = new BusinessHourCalendarFactory();

	/*
	public static BusinessHourCalendarFactory getDefaultInstance() {
		return BUSINESS;
	}
	*/
	
	
	public BusinessCalendarCalculator getBusinessCalendarCalculator(
			final String name, HolidayCalendar<LocalDate> calendar, final String holidayHandlerType,
			JodaWorkingWeek workingWeek, WeekBusinessHour weekBusinessHour, LocalDate startDate){
		this.registerHolidays(name, calendar);		
		LocalDateCalculator localDateCalculator = this.getDateCalculator(name,
				HolidayHandlerType.FORWARD);
		BusinessCalendarCalculator businessCalendarCalculator =  new BusinessCalendarCalculator(localDateCalculator);
		businessCalendarCalculator.setWeekBusinessHour(weekBusinessHour);
		businessCalendarCalculator.setWorkingWeek(workingWeek);	
		businessCalendarCalculator.setStartDate(startDate);
		return businessCalendarCalculator;
	}
	
	/*
	public BusinessCalendarCalculator getBusinessCalendarCalculator(
			final String name, final String holidayHandlerType,
			JodaWorkingWeek workingWeek, WeekBusinessHour weekBusinessHour) {
		LocalDateCalculator localDateCalculator = this.getDateCalculator(name,
				holidayHandlerType);
		BusinessCalendarCalculator businessCalendarCalculator = new BusinessCalendarCalculator();
		businessCalendarCalculator.setName(localDateCalculator.getName());
		businessCalendarCalculator.setHolidayHandler(localDateCalculator
				.getHolidayHandler());
		businessCalendarCalculator.setWeekBusinessHour(weekBusinessHour);		
		businessCalendarCalculator.setWorkingWeek(workingWeek);		
		return businessCalendarCalculator;
	}
	*/
	
}
