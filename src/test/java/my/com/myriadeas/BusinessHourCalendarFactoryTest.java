package my.com.myriadeas;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import net.objectlab.kit.datecalc.common.DefaultHolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;
import net.objectlab.kit.datecalc.joda.JodaWorkingWeek;
import net.objectlab.kit.datecalc.joda.LocalDateCalculator;
import net.objectlab.kit.datecalc.joda.LocalDateKitCalculatorsFactory;

import org.joda.time.LocalDate;
import org.junit.Test;

public class BusinessHourCalendarFactoryTest extends LocalDateKitCalculatorsFactory{

	@Test
	public void testLocalDateCalculator(){
		String branchCode = "MAIN";
		LocalDate cny = new LocalDate(2014, 1, 5);
		JodaWorkingWeek workingWeek = new FullWorkingWeek();
		Set<LocalDate> holidays = new HashSet<LocalDate>();
		holidays.add(cny);
		
		LocalDate earlyBoundary = new LocalDate(2014, 1, 1);
		LocalDate lateBoundary = earlyBoundary.plusYears(1);
		
		
		final HolidayCalendar<LocalDate> calendar = new DefaultHolidayCalendar<LocalDate>(
				holidays, earlyBoundary, lateBoundary);
		
		LocalDateKitCalculatorsFactory localDateKitCalculatorsFactory = new LocalDateKitCalculatorsFactory();
		localDateKitCalculatorsFactory.registerHolidays(branchCode, calendar);		
		LocalDateCalculator localDateCalculator = localDateKitCalculatorsFactory.getDateCalculator(branchCode,
				HolidayHandlerType.FORWARD);
		localDateCalculator.setWorkingWeek(workingWeek);
		
		
		assertTrue(localDateCalculator.isNonWorkingDay(cny));
		assertFalse(localDateCalculator.isNonWorkingDay(new LocalDate()));
		
		localDateCalculator.setStartDate(new LocalDate(2014, 2, 1));
		System.out.println(localDateCalculator.moveByDays(4).getCurrentBusinessDate());
			
	}
	
	
	@Test
	public void testBusinessCalendarCalculator(){
		String branchCode = "MAIN";
		LocalDate cny = new LocalDate(2014, 1, 5);
		JodaWorkingWeek workingWeek = new FullWorkingWeek();
		Set<LocalDate> holidays = new HashSet<LocalDate>();
		holidays.add(cny);
		
		LocalDate earlyBoundary = new LocalDate().minusYears(1);
		LocalDate lateBoundary = earlyBoundary.plusYears(1);
		
		
		final HolidayCalendar<LocalDate> calendar = new DefaultHolidayCalendar<LocalDate>(
				holidays, earlyBoundary, lateBoundary);
		
		LocalDateKitCalculatorsFactory localDateKitCalculatorsFactory = new LocalDateKitCalculatorsFactory();
		localDateKitCalculatorsFactory.registerHolidays(branchCode, calendar);		
		LocalDateCalculator localDateCalculator = localDateKitCalculatorsFactory.getDateCalculator(branchCode,
				HolidayHandlerType.FORWARD);
		
		
		BusinessHourCalendarFactory businessHourCalendarFactory = new BusinessHourCalendarFactory();
		businessHourCalendarFactory.registerHolidays(branchCode, calendar);
		BusinessCalendarCalculator businessCalendarCalculator =  new BusinessCalendarCalculator(localDateCalculator);		
		businessCalendarCalculator.setWorkingWeek(workingWeek);		
		
		assertTrue(businessCalendarCalculator.isNonWorkingDay(cny));
		assertFalse(businessCalendarCalculator.isNonWorkingDay(new LocalDate()));
		
		
	}
	
	@Test
	public void testGetBusinessCalendarCalculatorWithHoliday(){
		String branchCode = "MAIN";
		LocalDate cny = new LocalDate(2014, 1, 5);
		JodaWorkingWeek workingWeek = new FullWorkingWeek();
		Set<LocalDate> holidays = new HashSet<LocalDate>();
		holidays.add(cny);
		
		LocalDate earlyBoundary = new LocalDate(2014, 1 , 1);
		LocalDate lateBoundary = earlyBoundary.plusYears(1);
		
		final HolidayCalendar<LocalDate> calendar = new DefaultHolidayCalendar<LocalDate>(
				holidays, earlyBoundary, lateBoundary);
		
		BusinessHourCalendarFactory businessHourCalendarFactory = new BusinessHourCalendarFactory();
		
		BusinessCalendarCalculator businessCalendarCalculator = businessHourCalendarFactory.getBusinessCalendarCalculator(branchCode, calendar, HolidayHandlerType.FORWARD, 
				workingWeek, new WeekBusinessHour(), 
				new LocalDate(2014, 1 , 1));
		
		assertTrue(businessCalendarCalculator.isNonWorkingDay(cny));
		assertFalse(businessCalendarCalculator.isNonWorkingDay(new LocalDate()));
		
		
		businessCalendarCalculator.setStartDate(new LocalDate(2014, 1 , 4));
		LocalDate expectedDate = new LocalDate(2014, 1 , 9);
		LocalDate date = businessCalendarCalculator.moveByBusinessDays(4).getCurrentBusinessDate();
		assertEquals(expectedDate, date);
		System.out.println(date);
		
		expectedDate = new LocalDate(2014, 1 , 8);
		businessCalendarCalculator.setStartDate(new LocalDate(2014, 1 , 4));
		date = businessCalendarCalculator.moveByDays(4).getCurrentBusinessDate();
		assertEquals(expectedDate, date);
		System.out.println(date);
		
		
	}
	
	@Test
	public void testGetBusinessCalendarCalculatorWithoutHoliday(){
		String branchCode = "MAIN";
		JodaWorkingWeek workingWeek = new FullWorkingWeek();
		Set<LocalDate> holidays = new HashSet<LocalDate>();
		
		LocalDate earlyBoundary = new LocalDate(2014, 2 , 1);
		LocalDate lateBoundary = earlyBoundary.plusYears(1);
		
		final HolidayCalendar<LocalDate> calendar = new DefaultHolidayCalendar<LocalDate>(
				holidays, earlyBoundary, lateBoundary);
		
		BusinessHourCalendarFactory businessHourCalendarFactory = new BusinessHourCalendarFactory();
		
		BusinessCalendarCalculator businessCalendarCalculator = businessHourCalendarFactory.getBusinessCalendarCalculator(branchCode, calendar, HolidayHandlerType.FORWARD, 
				workingWeek, new WeekBusinessHour(), 
				new LocalDate(2014, 2 , 1));
		
		
		businessCalendarCalculator.setStartDate(new LocalDate(2014, 2 , 1));
		LocalDate expectedDate = new LocalDate(2014, 2 , 5);
		LocalDate date = businessCalendarCalculator.moveByDays(4).getCurrentBusinessDate();
		assertEquals(expectedDate, date);
		System.out.println(date);
	}


	
}
