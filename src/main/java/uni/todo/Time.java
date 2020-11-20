package uni.todo;

import java.util.Objects;

public class Time implements Comparable<Time> {

	private final int hour;
	private final int minute;

	public Time(int hour, int minute) {

		this.hour = hour;
		this.minute = minute;
	}

	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Time time = (Time) o;
		return hour == time.hour &&
		       minute == time.minute;
	}

	@Override
	public int hashCode() {
		return Objects.hash(hour, minute);
	}

	@Override
	public String toString() {
		return "Time{" +
		       "hour=" + hour +
		       ", minute=" + minute +
		       '}';
	}

	public String format() {
		return String.format("%s:%s", (hour < 10 ? String.format("0%d", hour) : String.valueOf(hour)),
				(minute < 10 ? String.format("0%d", minute) : String.valueOf(minute)));
	}

	@Override
	public int compareTo(Time o) {
		return Integer.compare(getHour(), o.getHour());
	}
}
