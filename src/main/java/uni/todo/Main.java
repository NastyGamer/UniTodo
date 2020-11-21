package uni.todo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

	public static final String TASK_FILE = String.format("%s\\.tasks.dat", System.getProperty("user.home"));

	public static void main(String[] args) throws IOException {
		TaskManager.loadTasks();
		launch(args);
		TaskManager.writeTasks();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Main.fxml")));
		primaryStage.setTitle("UniToDo");
		primaryStage.setScene(new Scene(root, 1150, 700));
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("Logo.png"))));
		primaryStage.show();
	}
}
