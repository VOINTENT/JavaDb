package ru.andrey.navigation;

import javafx.scene.control.Alert;

public class AlertNavigationImpl implements AlertNavigation {
    @Override
    public void createAlertError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка действия");
        alert.setContentText("Не удалось выполнить действие");
        alert.showAndWait();
    }
}
