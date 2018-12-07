package ru.andrey.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainStageController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }


    @FXML
    private void selectRegistrationTable() {
        Stage old_stage = (Stage) anchorPane.getScene().getWindow();
        old_stage.close();

        Stage stageNew = new Stage();
        stageNew.setTitle("Таблица \"Регистрация\"");
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/RegistrationTable.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stageNew.setScene(scene);
        stageNew.show();
    }

    @FXML
    private void selectSellerTable() {
        Stage stageOld = (Stage) anchorPane.getScene().getWindow();
        stageOld.close();

        Stage stageNew = new Stage();
        stageNew.setTitle("Таблица \"Продавцы\"");
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/SellerTable.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stageNew.setScene(scene);
        stageNew.show();
    }

    @FXML
    private void selectStoreTable() {
        Stage old_stage = (Stage) anchorPane.getScene().getWindow();
        old_stage.close();

        Stage stageNew = new Stage();
        stageNew.setTitle("Таблица \"Магазин\"");
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/StoreTable.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stageNew.setScene(scene);
        stageNew.show();
    }

    @FXML
    private void selectSupplierTable() {
        Stage old_stage = (Stage) anchorPane.getScene().getWindow();
        old_stage.close();

        Stage stageNew = new Stage();
        stageNew.setTitle("Таблица \"Поставщик\"");
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/SupplierTable.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stageNew.setScene(scene);
        stageNew.show();
    }

    @FXML
    private void selectSelect() {
        Stage old_stage = (Stage) anchorPane.getScene().getWindow();
        old_stage.close();

        Stage stageNew = new Stage();
        stageNew.setTitle("Ввод запросов");
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/Select.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stageNew.setScene(scene);
        stageNew.show();
    }
}
