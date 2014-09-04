package my.com.myriadeas;

import java.util.Date;

import net.objectlab.kit.datecalc.common.DateCalculator;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

public interface BusinessHourCalendar extends DateCalculator<LocalDate> {

	public Boolean isInBusinessHour(Date date);

	public Boolean isInBusinessHour(LocalDateTime date);

	public void setWeekBusinessHour(WeekBusinessHour weekBusinessHour);
}
