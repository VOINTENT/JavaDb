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
    private TextField storeTypeField;

    @FXML
    private Label storeNameLabel;

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
            storeNameLabel.setText(store.getName());
        } else {
            storeNameLabel.setText("");
        }
        if (store.getType() != null) {
            storeTypeField.setText(store.getType());
        } else {
            storeTypeField.setText("");
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
