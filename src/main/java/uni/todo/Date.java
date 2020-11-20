package uni.todo;

import java.io.Serializable;
import java.util.Objects;

public class Date implements Serializable {

	private final int day;
	private final int month;
	private final int year;

	public Date(int day, int month, int year) {
		this.day = day;
		this.month = month;
		this.year = year;
	}

	public int getDay() {
		return day;
	}

	public int getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Date date = (Date) o;
		return day == date.day &&
		       month == date.month &&
		       year == date.year;
	}

	@Override
	public int hashCode() {
		return Objects.hash(day, month, year);
	}

	@Override
	public String toString() {
		return "Date{" +
		       "day=" + day +
		       ", month=" + month +
		       ", year=" + year +
		       '}';
	}

	public String format() {
		return String.format("%s.%s.%d",
				(day < 10 ? String.format("0%d", day) : String.valueOf(day)),
				(month < 10 ? String.format("0%d", month) : String.valueOf(month)),
				year
		);
	}
}
