package uni.todo;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;

public class TimeCalculator {

	public static ArrayList<Date> getDates() {
		final Calendar calendar = Calendar.getInstance();
		calendar.set(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).getDayOfMonth());
		final ArrayList<Date> list = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			list.add(new Date(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR)));
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		return list;
	}

}
