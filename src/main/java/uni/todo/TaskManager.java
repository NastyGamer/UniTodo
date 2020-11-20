package uni.todo;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.WriterConfig;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class TaskManager {

	public static final HashMap<Date, ArrayList<Task>> tasks = new HashMap<>();

	public static void addTask(Date date, Task task) {
		if (!tasks.containsKey(date)) tasks.put(date, new ArrayList<>());
		tasks.get(date).add(task);
		sortTasks();
	}

	public static void deleteTask(Date date, Task task) {
		if (!tasks.containsKey(date)) return;
		tasks.get(date).remove(task);
	}

	public static ArrayList<Task> getTask(Date date) {
		if (!tasks.containsKey(date)) tasks.put(date, new ArrayList<>());
		return tasks.get(date);
	}

	public static void loadTasks() throws IOException {
		if (!(new File(Main.TASK_FILE).exists())) {
			System.out.println("Task file doesn't yet exist");
			final FileWriter writer = new FileWriter(Main.TASK_FILE);
			writer.write(Json.parse("{\n\"tasks\": []\n}").toString(WriterConfig.PRETTY_PRINT));
			writer.close();
		}
		final JsonValue json = Json.parse(new FileReader(Main.TASK_FILE));
		json.asObject().get("tasks").asArray().forEach(jsonValue -> {
			final String name = jsonValue.asObject().get("name").asString();
			final boolean done = jsonValue.asObject().get("done").asBoolean();
			final int hour = jsonValue.asObject().get("time").asObject().get("hour").asInt();
			final int minute = jsonValue.asObject().get("time").asObject().get("minute").asInt();
			final int day = jsonValue.asObject().get("date").asObject().get("day").asInt();
			final int month = jsonValue.asObject().get("date").asObject().get("month").asInt();
			final int year = jsonValue.asObject().get("date").asObject().get("year").asInt();
			final String color = jsonValue.asObject().get("color").asString();
			if (!tasks.containsKey(new Date(day, month, year)))
				tasks.put(new Date(day, month, year), new ArrayList<>());
			tasks.get(new Date(day, month, year)).add(new Task(new Time(hour, minute), name, color, done));
		});
		sortTasks();
	}

	public static void writeTasks() throws IOException {
		final JsonValue jsonValue = Json.parse("{\n\"tasks\": []\n}");
		tasks.forEach((date, taskList) -> {
			final JsonObject dateJson = new JsonObject();
			dateJson.add("day", date.getDay());
			dateJson.add("month", date.getMonth());
			dateJson.add("year", date.getYear());
			taskList.forEach(task -> {
				final JsonObject timeJson = new JsonObject();
				timeJson.add("hour", task.getTime().getHour());
				timeJson.add("minute", task.getTime().getMinute());
				final JsonObject taskJson = new JsonObject();
				taskJson.add("name", task.getName());
				taskJson.add("done", task.isDone());
				taskJson.add("time", timeJson);
				taskJson.add("date", dateJson);
				taskJson.add("color", task.getColor());
				jsonValue.asObject().get("tasks").asArray().add(taskJson);
			});
		});
		final FileWriter fileWriter = new FileWriter(Main.TASK_FILE);
		fileWriter.write(jsonValue.toString(WriterConfig.PRETTY_PRINT));
		fileWriter.close();
	}

	private static void sortTasks() {
		tasks.forEach((date, tasks) -> tasks.sort(Comparator.comparing(Task::getTime)));
	}

}
