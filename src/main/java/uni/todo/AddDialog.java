package uni.todo;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class AddDialog extends Dialog<Void> {

	public AddDialog() {
		setTitle("Hinzufügen");
		getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
		Node closeButton = getDialogPane().lookupButton(ButtonType.CLOSE);
		closeButton.managedProperty().bind(closeButton.visibleProperty());
		closeButton.setVisible(false);
		getDialogPane().setPrefSize(350, 240);
		final TextField nameField = new TextField("Name");
		nameField.setMinSize(350, 30);
		final DatePicker datePicker = new DatePicker();
		datePicker.setMinSize(350, 30);
		final Label nameLabel = new Label("Name");
		nameLabel.setMinSize(350, 30);
		final Label dateLabel = new Label("Datum");
		dateLabel.setMinSize(350, 30);
		final Label timeLabel = new Label("Uhrzeit");
		timeLabel.setMinSize(350, 30);
		final Label dividerLabel = new Label(":");
		dividerLabel.setTextAlignment(TextAlignment.CENTER);
		dividerLabel.setAlignment(Pos.CENTER);
		dividerLabel.setMinSize(10, 30);
		final TextField hourField = new TextField("00");
		hourField.setMinSize(170, 30);
		final TextField minuteField = new TextField("00");
		minuteField.setMinSize(170, 30);
		final Button okayButton = new Button("Hinzufügen");
		final ColorPicker colorPicker = new ColorPicker(Color.RED);
		colorPicker.setMinSize(350, 30);
		final Label colorLabel = new Label("Farbe");
		colorLabel.setMinSize(350, 30);
		okayButton.setMinWidth(350);
		okayButton.setOnMouseClicked(event -> {
			if (nameField.getText().isEmpty() || datePicker.getValue() == null) return;
			TaskManager.addTask(TimeCalculator.decepticons(new Date(datePicker.getValue().getDayOfMonth(), datePicker.getValue().getMonthValue(), datePicker.getValue().getYear())), new Task(new Time(Integer.parseInt(hourField.getText()), Integer.parseInt(minuteField.getText())), nameField.getText(), colorPicker.getValue().toString(), false));
			System.out.printf("%g, %g, %g%n", colorPicker.getValue().getRed(), colorPicker.getValue().getBlue(), colorPicker.getValue().getGreen());
			close();
		});
		getDialogPane().setContent(new VBox(nameLabel, nameField, dateLabel, datePicker, timeLabel, new HBox(hourField, dividerLabel, minuteField), colorLabel, colorPicker, new Label("") {{
			setMinSize(350, 30);
		}}, okayButton));
	}
}
