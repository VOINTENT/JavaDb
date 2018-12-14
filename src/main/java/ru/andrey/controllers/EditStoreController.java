package ru.andrey.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.andrey.domain.Store;
import ru.andrey.navigation.AlertNavigation;
import ru.andrey.navigation.AlertNavigationImpl;

import java.net.URL;
import java.util.ResourceBundle;

public class EditStoreController implements Initializable {

    @FXML
    private TextField storeNameField;

    @FXML
    private TextField storeTypeField;

    private Stage dialogStage;
    private Store store;
    private boolean okClicked = false;

    // Устанавливает сцену для этого окна.
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    // Устанавливает тип телефона, который мы редактируем в этом окне
    public void setStore(Store store) {
        this.store = store;
        if (store.getName() != null) {
            storeNameField.setText(store.getName());
            storeNameField.setDisable(true);
        }
        if (store.getType() != null) {
            storeTypeField.setText(store.getType());
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
            store.setType(storeTypeField.getText());
            store.setName(storeNameField.getText());

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
