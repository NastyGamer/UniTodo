package uni.todo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import org.apache.commons.lang3.math.NumberUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Objects;

public class AddDialog extends Dialog<Void> {

	public AddDialog() {
		setTitle("Hinzufügen");
		getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
		Node closeButton = getDialogPane().lookupButton(ButtonType.CLOSE);
		closeButton.managedProperty().bind(closeButton.visibleProperty());
		closeButton.setVisible(false);
		getDialogPane().setPrefSize(350, 565);
		//------------------Widgets------------------//
		final VBox root = new VBox();
		getDialogPane().setBackground(new Background(new BackgroundFill(Color.web("#263238"), CornerRadii.EMPTY, Insets.EMPTY)));

		final Label nameLabel = new Label("Name");
		nameLabel.setMinSize(350, 30);
		nameLabel.setTextFill(Color.WHITE);

		final TextField nameField = new TextField("Name");
		nameField.setMinSize(350, 30);

		final Label dateLabel = new Label("Datum");
		dateLabel.setMinSize(350, 30);
		dateLabel.setTextFill(Color.WHITE);

		final DatePicker datePicker = new DatePicker(LocalDate.now());
		datePicker.setMinSize(350, 30);

		final Label timeLabel = new Label("Uhrzeit");
		timeLabel.setMinSize(350, 30);
		timeLabel.setTextFill(Color.WHITE);

		final TextField hourField = new TextField("00");
		hourField.setMinSize(170, 30);

		final Label dividerLabel = new Label(":");
		dividerLabel.setTextAlignment(TextAlignment.CENTER);
		dividerLabel.setAlignment(Pos.CENTER);
		dividerLabel.setMinSize(10, 30);
		dividerLabel.setTextFill(Color.WHITE);

		final TextField minuteField = new TextField("00");
		minuteField.setMinSize(170, 30);

		final Label colorLabel = new Label("Farbe");
		colorLabel.setMinSize(350, 30);
		colorLabel.setTextFill(Color.WHITE);

		final ColorPicker colorPicker = new ColorPicker(Color.RED);
		colorPicker.setMinSize(350, 30);

		final Label recurringLabel = new Label("Wiederkehrend");
		recurringLabel.setMinSize(300, 30);
		recurringLabel.setTextFill(Color.WHITE);

		final CheckBox isRecurringCheckbox = new CheckBox();
		isRecurringCheckbox.setMinSize(50, 30);

		final Label mondayLabel = new Label("Montag");
		mondayLabel.setMinSize(300, 30);
		mondayLabel.setTextFill(Color.WHITE);

		final CheckBox mondayCheckbox = new CheckBox();
		mondayCheckbox.setMinSize(50, 30);

		final Label tuesdayLabel = new Label("Dienstag");
		tuesdayLabel.setMinSize(300, 30);
		tuesdayLabel.setTextFill(Color.WHITE);

		final CheckBox tuesdayCheckbox = new CheckBox();
		tuesdayCheckbox.setMinSize(50, 30);

		final Label wednesdayLabel = new Label("Mittwoch");
		wednesdayLabel.setMinSize(300, 30);
		wednesdayLabel.setTextFill(Color.WHITE);

		final CheckBox wednesdayCheckbox = new CheckBox();
		wednesdayCheckbox.setMinSize(50, 30);

		final Label thursdayLabel = new Label("Donnerstag");
		thursdayLabel.setMinSize(300, 30);
		thursdayLabel.setTextFill(Color.WHITE);

		final CheckBox thursdayCheckbox = new CheckBox();
		thursdayCheckbox.setMinSize(50, 30);

		final Label fridayLabel = new Label("Freitag");
		fridayLabel.setMinSize(300, 30);
		fridayLabel.setTextFill(Color.WHITE);

		final CheckBox fridayCheckbox = new CheckBox();
		fridayCheckbox.setMinSize(50, 30);

		final Label durationLabel = new Label("Wochen:");
		durationLabel.setMinSize(50, 30);
		durationLabel.setTextFill(Color.WHITE);

		final TextField durationField = new TextField("1");
		durationField.setMinSize(300, 30);

		final Label endDateLabel = new Label("Enddatum");
		endDateLabel.setMinSize(50, 30);
		endDateLabel.setTextFill(Color.WHITE);

		final DatePicker endDatePicker = new DatePicker();
		endDatePicker.setMinSize(300, 30);

		endDatePicker.setOnAction(event -> {
			final Date d = TimeCalculator.today();
			durationField.setText(String.valueOf(ChronoUnit.WEEKS.between(LocalDate.of(d.getYear(), d.getMonth(), d.getDay()).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)), endDatePicker.getValue().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY))) + 1));
		});
		final Button okayButton = new Button("Hinzufügen");
		okayButton.setMinWidth(350);
		okayButton.setOnMouseClicked(event -> {
			if (nameField.getText().isEmpty() || datePicker.getValue() == null || !NumberUtils.isCreatable(durationField.getText()) || Integer.parseInt(durationField.getText()) <= 0)
				return;
			if (!isRecurringCheckbox.isSelected())
				TaskManager.addTask(TimeCalculator.decepticons(new Date(datePicker.getValue().getDayOfMonth(), datePicker.getValue().getMonthValue(), datePicker.getValue().getYear())), new Task(new Time(Integer.parseInt(hourField.getText()), Integer.parseInt(minuteField.getText())), nameField.getText(), colorPicker.getValue().toString(), false));
			else {
				final ArrayList<Date> dates = new ArrayList<>();
				for (int i = 0; i < Integer.parseInt(durationField.getText()); i++) {
					final ArrayList<Date> weekDates = TimeCalculator.getDatesWithOffset(i);
					if (!mondayCheckbox.isSelected()) weekDates.set(0, null);
					if (!tuesdayCheckbox.isSelected()) weekDates.set(1, null);
					if (!wednesdayCheckbox.isSelected()) weekDates.set(2, null);
					if (!thursdayCheckbox.isSelected()) weekDates.set(3, null);
					if (!fridayCheckbox.isSelected()) weekDates.set(4, null);
					weekDates.removeIf(Objects::isNull);
					dates.addAll(weekDates);
				}
				System.out.println(dates.toString());
				dates.forEach(date -> TaskManager.addTask(date, new Task(new Time(Integer.parseInt(hourField.getText()), Integer.parseInt(minuteField.getText())), nameField.getText(), colorPicker.getValue().toString(), false)));
			}
			close();
		});

		isRecurringCheckbox.setOnMouseClicked(event -> {
			root.getChildren().clear();
			root.getChildren().addAll(
					nameLabel,
					nameField,
					dateLabel,
					datePicker,
					timeLabel,
					new HBox(
							hourField,
							dividerLabel,
							minuteField
					),
					new HBox(recurringLabel, isRecurringCheckbox)
			);
			if (isRecurringCheckbox.isSelected()) root.getChildren().addAll(
					new HBox(mondayLabel, mondayCheckbox),
					new HBox(tuesdayLabel, tuesdayCheckbox),
					new HBox(wednesdayLabel, wednesdayCheckbox),
					new HBox(thursdayLabel, thursdayCheckbox),
					new HBox(fridayLabel, fridayCheckbox),
					new HBox(endDateLabel, endDatePicker),
					new HBox(durationLabel, durationField)
			);
			root.getChildren().addAll(
					colorLabel,
					colorPicker,
					new Label("") {{
						setMinSize(350, 30);
					}}, okayButton
			);
		});
		//------------------Widgets------------------//
		root.getChildren().addAll(
				nameLabel,
				nameField,
				dateLabel,
				datePicker,
				timeLabel,
				new HBox(
						hourField,
						dividerLabel,
						minuteField
				),
				new HBox(recurringLabel, isRecurringCheckbox),
				colorLabel,
				colorPicker,
				new Label("") {{
					setMinSize(350, 30);
				}}, okayButton
		);
		getDialogPane().setContent(root);
	}
}
