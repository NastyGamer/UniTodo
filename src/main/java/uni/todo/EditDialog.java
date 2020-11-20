package uni.todo;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;

public class EditDialog extends Dialog<Void> {

	public EditDialog(Date date, Task task) {
		getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
		Node closeButton = getDialogPane().lookupButton(ButtonType.CLOSE);
		closeButton.managedProperty().bind(closeButton.visibleProperty());
		closeButton.setVisible(false);
		getDialogPane().setPrefSize(100, 60);
		final Button doneButton = new Button(task.isDone() ? "Nicht Erledigt" : "Erledigt");
		doneButton.setPrefSize(100, 30);
		final Button deleteButton = new Button("Löschen");
		deleteButton.setPrefSize(100, 30);
		getDialogPane().setContent(new VBox(doneButton, deleteButton));
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
