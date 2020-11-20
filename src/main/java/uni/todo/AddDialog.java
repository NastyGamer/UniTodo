package uni.todo;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class AddDialog extends Dialog<Void> {

	public AddDialog() {
		getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
		Node closeButton = getDialogPane().lookupButton(ButtonType.CLOSE);
		closeButton.managedProperty().bind(closeButton.visibleProperty());
		closeButton.setVisible(false);
		getDialogPane().setPrefSize(400, 300);
		final TextField nameField = new TextField("Name");
		final DatePicker datePicker = new DatePicker();
		final Label nameLabel = new Label("Name");
		final Label dateLabel = new Label("Datum");
		final Label timeLabel = new Label("Uhrzeit");
		final Label dividerLabel = new Label(":");
		final TextField hourField = new TextField("00");
		final TextField minuteField = new TextField("00");
		final Button okayButton = new Button("Hinzufügen");
		final ColorPicker colorPicker = new ColorPicker(Color.RED);
		okayButton.setOnMouseClicked(event -> {
			if (nameField.getText().isEmpty() || datePicker.getValue() == null) return;
			TaskManager.addTask(new Date(datePicker.getValue().getDayOfMonth(), datePicker.getValue().getMonthValue(), datePicker.getValue().getYear()), new Task(new Time(Integer.parseInt(hourField.getText()), Integer.parseInt(minuteField.getText())), nameField.getText(), colorPicker.getValue().toString(), false));
			System.out.printf("%g, %g, %g%n", colorPicker.getValue().getRed(), colorPicker.getValue().getBlue(), colorPicker.getValue().getGreen());
			close();
		});
		getDialogPane().setContent(new VBox(nameLabel, nameField, dateLabel, datePicker, timeLabel, new HBox(hourField, dividerLabel, minuteField), colorPicker, okayButton));
	}

}
