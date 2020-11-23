package uni.todo;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

public class Controller {

	public static int weekIndex = 0;
	@FXML
	private Button buttonAdd;
	@FXML
	private Button buttonReload;
	@FXML
	private GridPane mondayBox;
	@FXML
	private GridPane tuesdayBox;
	@FXML
	private GridPane wednesdayBox;
	@FXML
	private GridPane thursdayBox;
	@FXML
	private GridPane fridayBox;
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
	private Button scrollFwdButton;
	@FXML
	private Button homeButton;
	@FXML
	private Button scrollBwdButton;

	@FXML
	private void initialize() {
		buttonAdd.setOnMouseClicked(event -> {
			new AddDialog().showAndWait();
			updateBoxes();
		});
		buttonReload.setOnMouseClicked(event -> {
			new DeleteDialog().showAndWait();
			updateBoxes();
		});
		scrollFwdButton.setOnMouseClicked(event -> {
			weekIndex++;
			updateBoxes();
		});
		scrollBwdButton.setOnMouseClicked(event -> {
			weekIndex--;
			updateBoxes();
		});
		homeButton.setOnMouseClicked(event -> {
			weekIndex = 0;
			updateBoxes();
		});
		updateBoxes();
	}

	private void updateBoxes() {
		mondayBox.getChildren().clear();
		tuesdayBox.getChildren().clear();
		wednesdayBox.getChildren().clear();
		thursdayBox.getChildren().clear();
		fridayBox.getChildren().clear();
		final ArrayList<Date> datesOfWeek = TimeCalculator.getDatesWithOffset(weekIndex);
		mondayLabel.setText(String.format("Montag, %s", TimeCalculator.autobots(datesOfWeek.get(0)).format()));
		tuesdayLabel.setText(String.format("Dienstag, %s", TimeCalculator.autobots(datesOfWeek.get(1)).format()));
		wednesdayLabel.setText(String.format("Mittwoch, %s", TimeCalculator.autobots(datesOfWeek.get(2)).format()));
		thursdayLabel.setText(String.format("Donnerstag, %s", TimeCalculator.autobots(datesOfWeek.get(3)).format()));
		fridayLabel.setText(String.format("Freitag, %s", TimeCalculator.autobots(datesOfWeek.get(4)).format()));
		for (int dateIndex = 0; dateIndex < datesOfWeek.size(); dateIndex++) {
			int finalDateIndex = dateIndex;
			ArrayList<Task> tasks = TaskManager.getTasks(datesOfWeek.get(dateIndex));
			tasks.forEach(task -> {
				final Label taskLabel = new Label();
				taskLabel.setFont(Font.font(Font.getDefault().getFamily(), 15));
				if (task.isDone()) {
					taskLabel.setText(String.format("✅ %s - %s", task.getName(), task.getTime().format()));
					taskLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
					taskLabel.setTextFill(Color.GRAY);
				} else {
					if (Util.isDarkColor(task.getColor())) taskLabel.setTextFill(Color.WHITE);
					else taskLabel.setTextFill(Color.BLACK);
					taskLabel.setText(String.format("%s - %s", task.getName(), task.getTime().format()));
					taskLabel.setBackground(new Background(new BackgroundFill(Color.web(task.getColor()), CornerRadii.EMPTY, Insets.EMPTY)));
				}
				taskLabel.setPrefSize(200, 30);
				taskLabel.setAlignment(Pos.CENTER);
				taskLabel.setTextAlignment(TextAlignment.CENTER);
				taskLabel.setContentDisplay(ContentDisplay.CENTER);
				taskLabel.setOnMouseClicked(event -> {
					new EditDialog(datesOfWeek.get(finalDateIndex), task).showAndWait();
					updateBoxes();
				});
				switch (finalDateIndex) {
					case 0 -> mondayBox.add(taskLabel, 0, task.getTime().getHour());
					case 1 -> tuesdayBox.add(taskLabel, 0, task.getTime().getHour());
					case 2 -> wednesdayBox.add(taskLabel, 0, task.getTime().getHour());
					case 3 -> thursdayBox.add(taskLabel, 0, task.getTime().getHour());
					case 4 -> fridayBox.add(taskLabel, 0, task.getTime().getHour());
					default -> throw new IllegalArgumentException();
				}
			});
		}
	}
}
