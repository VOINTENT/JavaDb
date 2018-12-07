package ru.andrey.navigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LinkNavigationImpl implements LinkNavigation {
    @Override
    public void backToMainStage(AnchorPane anchorPane) {
        Stage old_stage = (Stage) anchorPane.getScene().getWindow();
        old_stage.close();

        Stage stageNew = new Stage();
        stageNew.setTitle("Главная");
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/MainStage.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stageNew.setScene(scene);
        stageNew.show();
    }
}
