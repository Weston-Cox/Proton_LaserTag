package com.photon.UI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.photon.DB.PostgreSQL;
import com.photon.Models.InitialScreenModel;
import com.photon.Helpers.TextFieldHelper;


public class InitialScreenController {
    private InitialScreenModel initialScreenModel;

    @FXML
    private TextField g_0_1, g_1_1, g_0_2, g_1_2, g_0_3, g_1_3, g_0_4, g_1_4, g_0_5, g_1_5, g_0_6, g_1_6, g_0_7, g_1_7, g_0_8, g_1_8, g_0_9, g_1_9, g_0_10, g_1_10, g_0_11, g_1_11, g_0_12, g_1_12, g_0_13, g_1_13, g_0_14, g_1_14, g_0_15, g_1_15;
    @FXML
    private TextField r_0_1, r_1_1, r_0_2, r_1_2, r_0_3, r_1_3, r_0_4, r_1_4, r_0_5, r_1_5, r_0_6, r_1_6, r_0_7, r_1_7, r_0_8, r_1_8, r_0_9, r_1_9, r_0_10, r_1_10, r_0_11, r_1_11, r_0_12, r_1_12, r_0_13, r_1_13, r_0_14, r_1_14, r_0_15, r_1_15;

	@FXML
	private Button btnStartMatch;
	@FXML
	private Button btnClearInputs;

	@FXML
	private VBox rootPane;


    private Map<Integer, TextField[]> greenPlayers = new HashMap<>();
    private Map<Integer, TextField[]> redPlayers = new HashMap<>();
	private boolean codenamePulledFromDB = false;

    public InitialScreenController(PostgreSQL postgreSQL) {
        this.initialScreenModel = new InitialScreenModel(postgreSQL); // Dependency Injection
    }

    //*******************************************************************************************
    // initialize
    // Description: Initializes the greenPlayers and redPlayers arrays and attaches focus lost listeners
    //*******************************************************************************************
    @FXML
    public void initialize() {
        // Initialize the greenPlayers array
        greenPlayers.put(1, new TextField[] {g_0_1, g_1_1});
        greenPlayers.put(2, new TextField[] {g_0_2, g_1_2});
        greenPlayers.put(3, new TextField[] {g_0_3, g_1_3});
        greenPlayers.put(4, new TextField[] {g_0_4, g_1_4});
        greenPlayers.put(5, new TextField[] {g_0_5, g_1_5});
        greenPlayers.put(6, new TextField[] {g_0_6, g_1_6});
        greenPlayers.put(7, new TextField[] {g_0_7, g_1_7});
        greenPlayers.put(8, new TextField[] {g_0_8, g_1_8});
        greenPlayers.put(9, new TextField[] {g_0_9, g_1_9});
        greenPlayers.put(10, new TextField[] {g_0_10, g_1_10});
        greenPlayers.put(11, new TextField[] {g_0_11, g_1_11});
        greenPlayers.put(12, new TextField[] {g_0_12, g_1_12});
        greenPlayers.put(13, new TextField[] {g_0_13, g_1_13});
        greenPlayers.put(14, new TextField[] {g_0_14, g_1_14});
        greenPlayers.put(15, new TextField[] {g_0_15, g_1_15});

        // Initialize the redPlayers array
        redPlayers.put(1, new TextField[] {r_0_1, r_1_1});
        redPlayers.put(2, new TextField[] {r_0_2, r_1_2});
        redPlayers.put(3, new TextField[] {r_0_3, r_1_3});
        redPlayers.put(4, new TextField[] {r_0_4, r_1_4});
        redPlayers.put(5, new TextField[] {r_0_5, r_1_5});
        redPlayers.put(6, new TextField[] {r_0_6, r_1_6});
        redPlayers.put(7, new TextField[] {r_0_7, r_1_7});
        redPlayers.put(8, new TextField[] {r_0_8, r_1_8});
        redPlayers.put(9, new TextField[] {r_0_9, r_1_9});
        redPlayers.put(10, new TextField[] {r_0_10, r_1_10});
        redPlayers.put(11, new TextField[] {r_0_11, r_1_11});
        redPlayers.put(12, new TextField[] {r_0_12, r_1_12});
        redPlayers.put(13, new TextField[] {r_0_13, r_1_13});
        redPlayers.put(14, new TextField[] {r_0_14, r_1_14});
        redPlayers.put(15, new TextField[] {r_0_15, r_1_15});


        attachFocusLostListeners(greenPlayers);
        attachFocusLostListeners(redPlayers);

		setupKeyEventHandler(); // Sets up a key event handler for the root pane

    }

	@FXML private void startMatchBtnPressed() {
		initialScreenModel.printAllPlayers();
	}

	@FXML private void clearInputsBtnPressed() {
		for (int i = 1; i <= greenPlayers.size(); i++) {
			greenPlayers.get(i)[0].setText("");
			greenPlayers.get(i)[1].setText("");
			redPlayers.get(i)[0].setText("");
			redPlayers.get(i)[1].setText("");
		}
	}

    //*******************************************************************************************
    // attachFocusLostListeners
    // Description: Loops through the player arrays and attaches focus lost listeners to the text fields
    //*******************************************************************************************
    private void attachFocusLostListeners(Map<Integer, TextField[]> players) {
        for (int i = 1; i <= players.size(); i++) {

            if (players.get(i)[0] == null || players.get(i)[1] == null) {continue;}

            final int row = i;

            TextFieldHelper.applyNumericConstraint(players.get(row)[0]);  // Applies a numeric contraint to the ID column text fields
			TextFieldHelper.applyTextConstraint(players.get(row)[1]); // Applies a text constraint to the codename column text fields            
            players.get(row)[0].focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (!newValue) { // Focus lost
                        onFocusLost(players.get(row), row, 0); // Calls the onFocusLost method that we defined on the specifid TextField.
                    }
                }
            });

            players.get(row)[1].focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (!newValue) { // Focus lost
                        onFocusLost(players.get(row), row, 1); // Calls the onFocusLost method that we defined on the specifid TextField.
                    }
                }
            });
        }  
    }


	private void attachFocusLostListeners(TextField[] textFieldRow, int row, String teamColor) {
		final int r = row;
		final int c = 1;
		TextField emptyField = new TextField(); // Empty text field to act as a placeholder
		emptyField.setId(teamColor);
		TextFieldHelper.applyTextConstraint(textFieldRow[1]); // Applies a text constraint to the codename column text fields
		textFieldRow[1].focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) { // Focus lost
					onFocusLost(textFieldRow, r, c); // Calls the onFocusLost method that we defined on the specifid TextField.
				}
			}
		});
	}


    //*******************************************************************************************
    // onFocusLost
    // Description: Handles the focus lost event for a text field
	//*******************************************************************************************
	private void onFocusLost(TextField[] textFieldRow, int row, int column) {
		String safePattern = "^[a-zA-Z0-9\\s._-]*$"; // Safe pattern for codenames to prevent SQL injection
		String teamColor = textFieldRow[0].getId().substring(0, 1); // Gets the team color from the ID of the first text field
		System.out.println("row:" + row + " column:" + column);

		if (column == 0 && textFieldRow[column].getText().isEmpty()) {
			System.out.println("ID is empty");
			return;
		} else if (column == 0) {
			int id = Integer.parseInt(textFieldRow[column].getText()); // Converts the string id to an integer
			String codename = initialScreenModel.getCodenameOfExistingID(id); // Grabs the codename from the database
			Platform.runLater(() -> { // Run on the JavaFX thread
				try {
					// If the text field is a green player
					if (teamColor.equals("g")) { 
						if (!codename.isEmpty() && textFieldRow[1].getText().isEmpty()) { // If the codename is not empty and another codename isn't in the TextField, set the codename to the one in the database
							codenamePulledFromDB = true;
							// Create a new TextField
							TextField newTextField = new TextField(codename);
							// Grab the old TextField
							TextField oldTextField = textFieldRow[1];
							newTextField = TextFieldHelper.copyOldTFProperties(newTextField, oldTextField);
							// Replace the old TextField with the new one in the parent
							Parent parent = textFieldRow[1].getParent();
							if (parent instanceof GridPane) {
								GridPane gridPane = (GridPane) parent;
								int colIndex = GridPane.getColumnIndex(textFieldRow[1]);
								int rowIndex = GridPane.getRowIndex(textFieldRow[1]);
								// Find the index of the old TextField in the children list
								int oldIndex = gridPane.getChildren().indexOf(textFieldRow[1]);
								gridPane.getChildren().remove(textFieldRow[1]);
								// Add the new TextField at the same indexes
								gridPane.getChildren().add(oldIndex, newTextField);
								GridPane.setColumnIndex(newTextField, colIndex);
								GridPane.setRowIndex(newTextField, rowIndex);
								// Update the reference in the array
								textFieldRow[1] = newTextField; 
								// Set focus to the new TextField
								textFieldRow[1].requestFocus();
								attachFocusLostListeners(textFieldRow, row, "g");
							}

							initialScreenModel.setCodenameOfGreenPlayer(row, codename);
							initialScreenModel.storePlayer(id, codename); // Store/Update the player in the database
						}
						initialScreenModel.setIDOfGreenPlayer(row, id);
						return;

					// If the text field is a red player
					} else if (teamColor.equals("r")) { 
						if (!codename.isEmpty() && textFieldRow[1].getText().isEmpty()) { // If the codename is not empty, set the codename to the one in the database
							codenamePulledFromDB = true;
							// Create a new TextField
							TextField newTextField = new TextField(codename);
							// Grab the old TextField
							TextField oldTextField = textFieldRow[1];
							newTextField = TextFieldHelper.copyOldTFProperties(newTextField, oldTextField);
							// Replace the old TextField with the new one in the parent
							Parent parent = textFieldRow[1].getParent();
							if (parent instanceof GridPane) {
								GridPane gridPane = (GridPane) parent;
								int colIndex = GridPane.getColumnIndex(textFieldRow[1]);
								int rowIndex = GridPane.getRowIndex(textFieldRow[1]);
								// Find the index of the old TextField in the children list
								int oldIndex = gridPane.getChildren().indexOf(textFieldRow[1]);
								gridPane.getChildren().remove(textFieldRow[1]);
								// Add the new TextField at the same indexes
								gridPane.getChildren().add(oldIndex, newTextField);
								GridPane.setColumnIndex(newTextField, colIndex);
								GridPane.setRowIndex(newTextField, rowIndex);
								// Update the reference in the array
								textFieldRow[1] = newTextField; 
								// Set focus to the new TextField
								textFieldRow[1].requestFocus();
								attachFocusLostListeners(textFieldRow, row, "r");
							}

							initialScreenModel.setCodenameOfRedPlayer(row, codename);
							initialScreenModel.storePlayer(id, codename); // Store/Update the player in the database
						}
						initialScreenModel.setIDOfRedPlayer(row, id);
						return;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			return;
		}
		if (column == 1 && textFieldRow[column].getText().isEmpty()) {
			System.out.println("Codename is empty");
		} else if (column == 1 && !codenamePulledFromDB) {
			String inputCodename = textFieldRow[column].getText();
			System.out.println("Input codename: " + inputCodename);
			if (teamColor.equals("g")) {
				if (inputCodename.matches(safePattern)) {
					initialScreenModel.setCodenameOfGreenPlayer(row, inputCodename);
					if (!textFieldRow[0].getText().isEmpty()) { // If the ID text field is not empty, store the player
						String equipmentID = showEquipmentIDModal((Stage) textFieldRow[column].getScene().getWindow());
						initialScreenModel.setEquipmentIDOfGreenPlayer(row, Integer.parseInt(equipmentID));
						initialScreenModel.storePlayer(Integer.parseInt(textFieldRow[0].getText()), inputCodename);
					}
				} else {
					textFieldRow[column].setText("");
					System.out.println("Unsafe green team input detected at [" + column + "][" + row + "]. Input cleared.");
				}
			} else if (teamColor.equals("r")) {
				if (inputCodename.matches(safePattern)) {
					initialScreenModel.setCodenameOfRedPlayer(row, inputCodename);
					if (!textFieldRow[0].getText().isEmpty()) { // If the ID text field is not empty, store the player
						String equipmentID = showEquipmentIDModal((Stage) textFieldRow[column].getScene().getWindow());
						initialScreenModel.setEquipmentIDOfRedPlayer(row, Integer.parseInt(equipmentID));
						initialScreenModel.storePlayer(Integer.parseInt(textFieldRow[0].getText()), inputCodename);
					}
				} else {
					textFieldRow[column].setText("");
					System.out.println("Unsafe red team input detected at [" + column + "][" + row + "]. Input cleared.");
				}
			}
		}
		codenamePulledFromDB = false;
	}

	private void setupKeyEventHandler() {
		rootPane.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.F12)
				clearInputsBtnPressed();
		});
	}

	//*******************************************************************************************
	// showEquipmentIDModal
	// Description: Shows the equipment ID modal and returns the equipment ID
	//*******************************************************************************************
	private String showEquipmentIDModal(Stage owner) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/photon/EquipmentIDModal.fxml"));
            Parent page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Enter Equipment ID");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(owner);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            EquipmentIDModalController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait(); // Show the modal and wait for it to close

            if (controller.isSubmitClicked()) {
                String equipmentID = controller.getEquipmentID();
                // Handle the equipment ID
                System.out.println("Equipment ID: " + equipmentID);
				return equipmentID;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

		return "-1";
    }
}