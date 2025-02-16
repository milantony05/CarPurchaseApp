package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class CarPurchaseInterface extends Application {

    private ComboBox<String> carComboBox;
    private RadioButton petrolRadio, evRadio;
    private ToggleGroup fuelGroup;
    private TextField amountTextField;
    private Label resultLabel;

    @Override
    public void start(Stage primaryStage) {
        // Title label
        Label titleLabel = new Label("Car Purchase System");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.getStyleClass().add("title-label");

        // Available cars info
        Label availableCarsLabel = new Label("Available Cars: Audi, BMW, Jaguar, Ferrari");
        availableCarsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        // Select car label and dropdown
        Label selectCarLabel = new Label("Select your car:");
        carComboBox = new ComboBox<>();
        carComboBox.getItems().addAll("Audi", "BMW", "Jaguar", "Ferrari");
        carComboBox.setValue("Audi");

        // Fuel type selection using RadioButtons
        Label fuelTypeLabel = new Label("Select Fuel Type:");
        petrolRadio = new RadioButton("Petrol");
        evRadio = new RadioButton("EV");

        fuelGroup = new ToggleGroup();
        petrolRadio.setToggleGroup(fuelGroup);
        evRadio.setToggleGroup(fuelGroup);

        HBox fuelTypeBox = new HBox(10, petrolRadio, evRadio);
        fuelTypeBox.setAlignment(Pos.CENTER_LEFT);

        // Advance payment label and input
        Label amountLabel = new Label("Advance Payment:");
        amountTextField = new TextField();
        amountTextField.setPromptText("Enter amount");

        // Submit button
        Button submitButton = new Button("Submit");
        submitButton.getStyleClass().add("submit-button");
        submitButton.setOnAction(event -> submitCarOrder());

        // Result label
        resultLabel = new Label();
        resultLabel.getStyleClass().add("result-label");

        // Main layout using GridPane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        gridPane.add(availableCarsLabel, 0, 0, 2, 1);
        gridPane.add(selectCarLabel, 0, 1);
        gridPane.add(carComboBox, 1, 1);
        gridPane.add(fuelTypeLabel, 0, 2);
        gridPane.add(fuelTypeBox, 1, 2);
        gridPane.add(amountLabel, 0, 3);
        gridPane.add(amountTextField, 1, 3);
        gridPane.add(submitButton, 1, 4);
        gridPane.add(resultLabel, 0, 5, 2, 1);

        // Root layout
        VBox root = new VBox(15, titleLabel, gridPane);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        // Scene and stage setup
        Scene scene = new Scene(root, 500, 350);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        primaryStage.setTitle("Car Purchase Interface");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void submitCarOrder() {
        String selectedCar = carComboBox.getValue();
        RadioButton selectedFuel = (RadioButton) fuelGroup.getSelectedToggle();

        if (selectedFuel == null) {
            showAlert("Invalid Selection", "Please select a fuel type: Petrol or EV.");
            return;
        }

        String fuelType = selectedFuel.getText();

        try {
            int amount = Integer.parseInt(amountTextField.getText());
            if (amount < 100000) {
                showAlert("Insufficient Amount", "Minimum advance payment is 100000.");
                return;
            }
            resultLabel.setText("Booking Successful!\n" + selectedCar + " (" + fuelType + ") booked with advance: ₹" + amount);
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid number for the amount.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}