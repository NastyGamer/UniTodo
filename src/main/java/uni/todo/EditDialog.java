package uni.todo;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class EditDialog extends Dialog<Void> {

	public EditDialog(Date date, Task task) {
		setTitle("Bearbeiten");
		getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
		getDialogPane().setBackground(new Background(new BackgroundFill(Color.web("#263238"), CornerRadii.EMPTY, Insets.EMPTY)));
		Node closeButton = getDialogPane().lookupButton(ButtonType.CLOSE);
		closeButton.managedProperty().bind(closeButton.visibleProperty());
		closeButton.setVisible(false);
		getDialogPane().setPrefSize(100, 60);
		final Button doneButton = new Button(task.isDone() ? "Nicht Erledigt" : "Erledigt");
		doneButton.setPrefSize(100, 30);
		final Button deleteButton = new Button("Löschen");
		deleteButton.setPrefSize(100, 30);
		getDialogPane().setContent(new VBox(doneButton, new AnchorPane() {{
			setMinHeight(15);
		}}, deleteButton));
		deleteButton.setOnMouseClicked(event -> {
			TaskManager.deleteTask(date, task);
			close();
		});
		doneButton.setOnMouseClicked(event -> {
			task.setDone(!task.isDone());
			close();
		});
	}

}
