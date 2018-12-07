package ru.andrey.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.andrey.domain.Seller;
import ru.andrey.navigation.AlertNavigation;
import ru.andrey.navigation.AlertNavigationImpl;

import java.net.URL;
import java.util.ResourceBundle;

public class EditSellerController implements Initializable {

    @FXML
    private Label sellerNameLabel;

    @FXML
    private Label sellerAreaLabel;

    @FXML
    private Label sellerNameProductLabel;

    @FXML
    private TextField sellerPriceField;

    @FXML
    private TextField sellerCountField;

    private Stage dialogStage;
    private Seller seller;
    private boolean okClicked = false;

    // Устанавливает сцену для этого окна.
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    // Устанавливает контакт, который мы редактируем в этом окне
    public void setSeller(Seller seller) {

        this.seller = seller;

        if (seller.getName() != null) {
            sellerNameLabel.setText(seller.getName().toString());
        }
        if (seller.getArea() != null) {
            sellerAreaLabel.setText(seller.getArea().toString());
        }
        if (seller.getNameProduct() != null) {
            sellerNameProductLabel.setText(seller.getNameProduct().toString());
        }
        if (seller.getPrice() != null) {
            sellerPriceField.setText(seller.getPrice().toString());
        }
        if (seller.getCount() != null) {
            sellerCountField.setText(seller.getCount().toString());
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
            seller.setPrice(Integer.parseInt(sellerPriceField.getText()));
            seller.setCount(Integer.parseInt(sellerCountField.getText()));

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
