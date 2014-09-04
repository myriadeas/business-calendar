package my.com.myriadeas;

import static org.junit.Assert.*;

import org.joda.time.LocalTime;
import org.junit.Test;

public class BusinessHourTest {

	@Test
	public void testCreateLocalTime() {
		String szBusinessHour = "09:00";
		assertEquals(new LocalTime(9, 0),
				BusinessHour.createLocalTime(szBusinessHour));

		szBusinessHour = "19:20";
		assertEquals(new LocalTime(19, 20),
				BusinessHour.createLocalTime(szBusinessHour));
	}

	@Test
	public void testGetStartAndEndTime() {
		String szBusinessHour = "09:00-18:00";
		assertEquals(new LocalTime(9, 0),
				BusinessHour.getStartTime(szBusinessHour));
		assertEquals(new LocalTime(18, 0),
				BusinessHour.getEndTime(szBusinessHour));
	}

	@Test
	public void testBusinessHour() {
		String szBusinessHour = "09:00-18:00";
		BusinessHour businessHour = new BusinessHour(szBusinessHour);
		assertEquals(new LocalTime(9, 0), businessHour.getStart());
		assertEquals(new LocalTime(18, 0), businessHour.getEnd());
	}
	

}
