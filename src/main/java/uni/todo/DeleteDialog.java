package uni.todo;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.time.LocalDate;
import java.util.ArrayList;

public class DeleteDialog extends Dialog<Void> {

	public DeleteDialog() {
		setTitle("Löschen");
		getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
		Node closeButton = getDialogPane().lookupButton(ButtonType.CLOSE);
		closeButton.managedProperty().bind(closeButton.visibleProperty());
		closeButton.setVisible(false);
		getDialogPane().setPrefSize(250, 120);
		final Label choiceLabel = new Label("Welche Aufgaben:");
		choiceLabel.setMinWidth(250);
		choiceLabel.setMinHeight(30);
		final ChoiceBox<String> choiceBox = new ChoiceBox<>();
		choiceBox.setPrefHeight(30);
		choiceBox.setMinWidth(250);
		final Label deleteUncompletedLabel = new Label("Auch nicht erledigte: ");
		deleteUncompletedLabel.setTextAlignment(TextAlignment.CENTER);
		deleteUncompletedLabel.setAlignment(Pos.CENTER);
		deleteUncompletedLabel.setMinWidth(200);
		deleteUncompletedLabel.setMinHeight(30);
		final CheckBox deleteUncompletedBox = new CheckBox();
		deleteUncompletedBox.setMinWidth(50);
		deleteUncompletedBox.setMinHeight(30);
		final Button deleteButton = new Button("Löschen");
		deleteButton.setMinWidth(250);
		choiceBox.getItems().addAll("Alle", "Diese Woche", "Heute");
		deleteButton.setOnMouseClicked(event -> {
			if (choiceBox.getValue() == null) return;
			switch (choiceBox.getValue()) {
				case "Alle":
					if (deleteUncompletedBox.isSelected())
						TaskManager.tasks.values().forEach(ArrayList::clear);
					else {
						TaskManager.tasks.values().forEach(list -> list.removeIf(Task::isDone));
					}
					break;
				case "Diese Woche":
					final ArrayList<Date> datesOfWeek = TimeCalculator.getDatesWithOffset(Controller.weekIndex);
					if (deleteUncompletedBox.isSelected()) datesOfWeek.forEach(date -> {
						TaskManager.getTasks(date).clear();
					});
					else {
						datesOfWeek.forEach(date -> {
							TaskManager.getTasks(date).removeIf(Task::isDone);
						});
					}
					break;
				case "Heute":
					final LocalDate localDate = LocalDate.now();
					final Date today = new Date(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
					if (deleteUncompletedBox.isSelected()) TaskManager.getTasks(today).clear();
					else TaskManager.getTasks(today).removeIf(Task::isDone);
					break;
				default:
					throw new IllegalStateException("Unexpected value: " + choiceBox.getValue());
			}
			close();
		});
		getDialogPane().getChildren().add(new VBox(choiceLabel, choiceBox, new HBox(deleteUncompletedLabel, deleteUncompletedBox), deleteButton));
	}

}
