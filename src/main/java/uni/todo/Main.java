package uni.todo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;

import java.io.IOException;
import java.util.Objects;
import java.util.prefs.Preferences;

public class Main extends Application {

	public static String TASK_FILE;

	public static void main(String[] args) throws IOException, ArgumentParserException {
		final ArgumentParser argParser = ArgumentParsers.newFor("prog").build();
		argParser.addArgument("--filePath")
				.type(String.class)
				.help("The path of the data file")
				.setDefault(String.format("%s%s.tasks.dat", System.getProperty("user.home"), Util.osSwitch("\\", "/", "/")))
				.required(false);
		System.out.printf("Using file path %s%n", argParser.parseArgs(args).getString("filePath"));
		TASK_FILE = argParser.parseArgs(args).getString("filePath");
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
