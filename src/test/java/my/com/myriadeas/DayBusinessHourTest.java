package my.com.myriadeas;

import static org.easymock.EasyMock.createMockBuilder;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.easymock.IMockBuilder;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.junit.Test;

public class DayBusinessHourTest {
	private DayBusinessHour dayBusinessHour;
	
	@Test
	public void testGetTotalBusinessHour(){
		IMockBuilder<DayBusinessHour> dayBusinessHourMock = createMockBuilder(DayBusinessHour.class);
		dayBusinessHourMock.addMockedMethod("getBusinessHours");
		dayBusinessHour = dayBusinessHourMock.createMock();
		
		List<BusinessHour> businessHoursWithOneSlot = new ArrayList<BusinessHour>();
		BusinessHour businessHour = new BusinessHour(new LocalTime(9, 0), new LocalTime(13, 30));
		businessHoursWithOneSlot.add(businessHour);						
		
		
		List<BusinessHour> businessHoursWithTwoSlot = new ArrayList<BusinessHour>();
		new BusinessHour(new LocalTime(9, 0), new LocalTime(13, 30));
		businessHoursWithTwoSlot.add(businessHour);	
		businessHour = new BusinessHour(new LocalTime(14, 30), new LocalTime(18, 00));
		businessHoursWithTwoSlot.add(businessHour);
		
		expect(dayBusinessHour.getBusinessHours()).andReturn(businessHoursWithOneSlot).times(1);
		expect(dayBusinessHour.getBusinessHours()).andReturn(businessHoursWithTwoSlot).times(1);		
		replay(dayBusinessHour);
		
		int expectedTotalBusinessHour = 270;
		int returnedTotalBusinessHour = dayBusinessHour.getTotalBusinessHour();
		assertEquals(expectedTotalBusinessHour, returnedTotalBusinessHour);
		
		expectedTotalBusinessHour = 270 + 210;
		returnedTotalBusinessHour = dayBusinessHour.getTotalBusinessHour();
		assertEquals(expectedTotalBusinessHour, returnedTotalBusinessHour);
		
	}
	
	@Test
	public void testGetTotalBusinessHourUntilGivenTime_SingleSlot(){
		IMockBuilder<DayBusinessHour> dayBusinessHourMock = createMockBuilder(DayBusinessHour.class);
		dayBusinessHourMock.addMockedMethod("getBusinessHours");
		dayBusinessHour = dayBusinessHourMock.createMock();
		
		List<BusinessHour> businessHours = new ArrayList<BusinessHour>();
		BusinessHour businessHour = new BusinessHour(new LocalTime(9, 0), new LocalTime(13, 30));
		businessHours.add(businessHour);			
		
		expect(dayBusinessHour.getBusinessHours()).andReturn(businessHours).times(4);	
		replay(dayBusinessHour);
		
		LocalDateTime localDateTime = new LocalDateTime(2001, 1, 1, 9, 0, 31);		
		int expectedMinutes = 0;
		int returnedMinutes = dayBusinessHour.getTotalBusinessHourUntilGivenTime(localDateTime);
		assertEquals(expectedMinutes, returnedMinutes);
		
		localDateTime = new LocalDateTime(2001, 1, 1, 9, 30, 31);		
		expectedMinutes = 30;
		returnedMinutes = dayBusinessHour.getTotalBusinessHourUntilGivenTime(localDateTime);
		assertEquals(expectedMinutes, returnedMinutes);
		
		localDateTime = new LocalDateTime(2001, 1, 1, 13, 30, 31);		
		expectedMinutes = 270;
		returnedMinutes = dayBusinessHour.getTotalBusinessHourUntilGivenTime(localDateTime);
		assertEquals(expectedMinutes, returnedMinutes);
		
		localDateTime = new LocalDateTime(2001, 1, 1, 14, 30, 31);		
		expectedMinutes = 270;
		returnedMinutes = dayBusinessHour.getTotalBusinessHourUntilGivenTime(localDateTime);
		assertEquals(expectedMinutes, returnedMinutes);
		
	}
	
	@Test
	public void testGetTotalBusinessHourUntilGivenTime_TwoSlots(){
		
		IMockBuilder<DayBusinessHour> dayBusinessHourMock = createMockBuilder(DayBusinessHour.class);
		dayBusinessHourMock.addMockedMethod("getBusinessHours");
		dayBusinessHour = dayBusinessHourMock.createMock();
		
		List<BusinessHour> businessHours = new ArrayList<BusinessHour>();
		BusinessHour businessHour = new BusinessHour(new LocalTime(9, 0), new LocalTime(13, 30));
		businessHours.add(businessHour);
		businessHour = new BusinessHour(new LocalTime(14, 30), new LocalTime(18, 00));
		businessHours.add(businessHour);
		
		expect(dayBusinessHour.getBusinessHours()).andReturn(businessHours).times(8);	
		replay(dayBusinessHour);
		
		LocalDateTime localDateTime = new LocalDateTime(2001, 1, 1, 9, 0, 31);		
		int expectedMinutes = 0;
		int returnedMinutes = dayBusinessHour.getTotalBusinessHourUntilGivenTime(localDateTime);
		assertEquals(expectedMinutes, returnedMinutes);
		
		localDateTime = new LocalDateTime(2001, 1, 1, 9, 30, 31);		
		expectedMinutes = 30;
		returnedMinutes = dayBusinessHour.getTotalBusinessHourUntilGivenTime(localDateTime);
		assertEquals(expectedMinutes, returnedMinutes);
		
		localDateTime = new LocalDateTime(2001, 1, 1, 13, 30, 31);		
		expectedMinutes = 270;
		returnedMinutes = dayBusinessHour.getTotalBusinessHourUntilGivenTime(localDateTime);
		assertEquals(expectedMinutes, returnedMinutes);
		
		localDateTime = new LocalDateTime(2001, 1, 1, 14, 30, 31);		
		expectedMinutes = 270;
		returnedMinutes = dayBusinessHour.getTotalBusinessHourUntilGivenTime(localDateTime);
		assertEquals(expectedMinutes, returnedMinutes);
		
		localDateTime = new LocalDateTime(2001, 1, 1, 15, 30, 31);		
		expectedMinutes = 270 + 60;
		returnedMinutes = dayBusinessHour.getTotalBusinessHourUntilGivenTime(localDateTime);
		assertEquals(expectedMinutes, returnedMinutes);
		
		localDateTime = new LocalDateTime(2001, 1, 1, 17, 59, 31);		
		expectedMinutes = 270 + 209;
		returnedMinutes = dayBusinessHour.getTotalBusinessHourUntilGivenTime(localDateTime);
		assertEquals(expectedMinutes, returnedMinutes);

		localDateTime = new LocalDateTime(2001, 1, 1, 18, 00, 31);		
		expectedMinutes = 270 + 210;
		returnedMinutes = dayBusinessHour.getTotalBusinessHourUntilGivenTime(localDateTime);
		assertEquals(expectedMinutes, returnedMinutes);

		localDateTime = new LocalDateTime(2001, 1, 1, 18, 01, 31);		
		expectedMinutes = 270 + 210;
		returnedMinutes = dayBusinessHour.getTotalBusinessHourUntilGivenTime(localDateTime);
		assertEquals(expectedMinutes, returnedMinutes);

		
	}
}
