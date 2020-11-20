package uni.todo;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

public class Controller {

	@FXML
	private Button buttonAdd;

	@FXML
	private Button buttonReload;

	@FXML
	private VBox mondayBox;

	@FXML
	private VBox tuesdayBox;

	@FXML
	private VBox wednesdayBox;

	@FXML
	private VBox thursdayBox;

	@FXML
	private VBox fridayBox;

	@FXML
	private Label mondayLabel;

	@FXML
	private Label tuesdayLabel;

	@FXML
	private Label wednesdayLabel;

	@FXML
	private Label thursdayLabel;

	@FXML
	private Label fridayLabel;

	@FXML
	private void initialize() {
		buttonAdd.setOnMouseClicked(event -> {
			new AddDialog().showAndWait();
			updateBoxes();
		});
		buttonReload.setOnMouseClicked(event -> updateBoxes());
		updateBoxes();
	}

	private void updateBoxes() {
		mondayBox.getChildren().clear();
		tuesdayBox.getChildren().clear();
		wednesdayBox.getChildren().clear();
		thursdayBox.getChildren().clear();
		fridayBox.getChildren().clear();
		final ArrayList<Date> datesOfWeek = TimeCalculator.getDates();
		mondayLabel.setText(String.format("Montag, %s", datesOfWeek.get(0).format()));
		tuesdayLabel.setText(String.format("Dienstag, %s", datesOfWeek.get(1).format()));
		wednesdayLabel.setText(String.format("Mittwoch, %s", datesOfWeek.get(2).format()));
		thursdayLabel.setText(String.format("Donnerstag, %s", datesOfWeek.get(3).format()));
		fridayLabel.setText(String.format("Freitag, %s", datesOfWeek.get(4).format()));
		for (int dateIndex = 0; dateIndex < datesOfWeek.size(); dateIndex++) {
			int finalDateIndex = dateIndex;
			ArrayList<Task> tasks = TaskManager.getTask(datesOfWeek.get(dateIndex));
			for (int taskIndex = 0; taskIndex < tasks.size(); taskIndex++) {
				Task task = tasks.get(taskIndex);
				final Label taskLabel = new Label(String.format("%s - %s", task.getName(), task.getTime().format()));
				taskLabel.setFont(Font.font(Font.getDefault().getFamily(), 15));
				taskLabel.setBackground(new Background(new BackgroundFill(task.isDone() ? Color.GREEN : Color.web(task.getColor()), CornerRadii.EMPTY, Insets.EMPTY)));
				taskLabel.setPrefSize(200, 30);
				taskLabel.setAlignment(Pos.CENTER);
				taskLabel.setTextAlignment(TextAlignment.CENTER);
				taskLabel.setContentDisplay(ContentDisplay.CENTER);
				taskLabel.setOnMouseClicked(event -> {
					new EditDialog(datesOfWeek.get(finalDateIndex), task).showAndWait();
					updateBoxes();
				});
				switch (finalDateIndex) {
					case 0 -> mondayBox.getChildren().add(taskLabel);
					case 1 -> tuesdayBox.getChildren().add(taskLabel);
					case 2 -> wednesdayBox.getChildren().add(taskLabel);
					case 3 -> thursdayBox.getChildren().add(taskLabel);
					case 4 -> fridayBox.getChildren().add(taskLabel);
					default -> throw new IllegalArgumentException();
				}
				if (taskIndex < TaskManager.getTask(datesOfWeek.get(dateIndex)).size() - 1) {
					final AnchorPane placeHolder = new AnchorPane();
					placeHolder.setPrefSize(200, 30 * ((TaskManager.getTask(datesOfWeek.get(dateIndex)).get(taskIndex + 1).getTime().getHour() - TaskManager.getTask(datesOfWeek.get(dateIndex)).get(taskIndex).getTime().getHour()) - 1));
					switch (finalDateIndex) {
						case 0 -> mondayBox.getChildren().add(placeHolder);
						case 1 -> tuesdayBox.getChildren().add(placeHolder);
						case 2 -> wednesdayBox.getChildren().add(placeHolder);
						case 3 -> thursdayBox.getChildren().add(placeHolder);
						case 4 -> fridayBox.getChildren().add(placeHolder);
						default -> throw new IllegalArgumentException();
					}
				}
			}
		}
	}
}
