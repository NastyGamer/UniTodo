package uni.todo;

import java.io.Serializable;
import java.util.Objects;

public class Task implements Serializable {

	private final Time time;
	private final String name;
	private final String color;
	private boolean done;

	public Task(Time time, String name, String color, boolean done) {
		this.time = time;
		this.name = name;
		this.color = color;
		this.done = done;
	}

	public Time getTime() {
		return time;
	}

	public String getName() {
		return name;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Task task = (Task) o;
		return done == task.done &&
		       time.equals(task.time) &&
		       name.equals(task.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(time, name, done);
	}

	@Override
	public String toString() {
		return "Task{" +
		       "time=" + time +
		       ", name='" + name + '\'' +
		       ", done=" + done +
		       '}';
	}

	public String getColor() {
		return color;
	}
}
