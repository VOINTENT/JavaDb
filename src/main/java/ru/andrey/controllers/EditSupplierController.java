package ru.andrey.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.andrey.domain.Store;
import ru.andrey.domain.Supplier;
import ru.andrey.navigation.AlertNavigation;
import ru.andrey.navigation.AlertNavigationImpl;

import java.net.URL;
import java.util.ResourceBundle;

public class EditSupplierController implements Initializable {

    @FXML
    private Label supplierTypeLabel;

    @FXML
    private TextField supplierSupplierField;

    private Stage dialogStage;
    private Supplier supplier;
    private boolean okClicked = false;

    // Устанавливает сцену для этого окна.
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    // Устанавливает тип телефона, который мы редактируем в этом окне
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;

        if (supplier.getType() != null) {
            supplierTypeLabel.setText(supplier.getType());
        } else {
            supplierTypeLabel.setText("");
        }
        if (supplier.getSupplier() != null) {
            supplierSupplierField.setText(supplier.getSupplier());
        } else {
            supplierSupplierField.setText("");
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
            supplier.setType(supplierSupplierField.getText());

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
