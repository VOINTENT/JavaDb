package ru.andrey.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.andrey.domain.Registration;
import ru.andrey.navigation.AlertNavigation;
import ru.andrey.navigation.AlertNavigationImpl;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class EditRegistrationController implements Initializable {
    @FXML
    private TextField registrationNameField;

    @FXML
    private TextField registrationDateField;

    @FXML
    private TextField registrationCountPositionsField;

    private Stage dialogStage;
    private Registration registration;
    private boolean okClicked = false;

    // Устанавливает сцену для этого окна.
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    // Устанавливает контакт, который мы редактируем в этом окне
    public void setRegistration(Registration registration) {
        this.registration = registration;

        if (registration.getName() != null) {
            registrationNameField.setText(registration.getName());
            registrationNameField.setDisable(true);
        }
        if (registration.getDate() != null) {
            registrationDateField.setText(registration.getDate().toString());
        }
        if (registration.getCountPositions() != null) {
            registrationCountPositionsField.setText(String.valueOf(registration.getCountPositions()));
        }
    }

    // Проверка нажатия кнопки OK
    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    // Нажатие кнопки "OK"
    private void handleOK() {
        try {
            registration.setName(registrationNameField.getText());
            registration.setDate(Date.valueOf(registrationDateField.getText()));
            registration.setCountPositions(Integer.parseInt(registrationCountPositionsField.getText()));

            okClicked = true;
            dialogStage.close();
        } catch (IllegalArgumentException e) {
            AlertNavigation alertNavigation = new AlertNavigationImpl();
            alertNavigation.createAlertError();
        }
    }

    @FXML
    // Кнопка "Отмена"
    private void handleCancel() {
        dialogStage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
