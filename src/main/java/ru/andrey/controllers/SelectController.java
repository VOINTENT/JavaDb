package ru.andrey.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.postgresql.util.PSQLException;
import ru.andrey.domain.Registration;
import ru.andrey.navigation.AlertNavigation;
import ru.andrey.navigation.AlertNavigationImpl;
import ru.andrey.navigation.LinkNavigation;
import ru.andrey.navigation.LinkNavigationImpl;
import ru.andrey.util.DbWork;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class SelectController implements Initializable {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField selectField;

    @FXML
    // Нажатие кнопки "Ввод"
    private void handleEnter() throws SQLException {
        DbWork db = DbWork.getInstance();
        Statement statement = db.getConnection().createStatement();
        String SQL_SELECT = selectField.getText();
        ResultSet resultSet1 = null;
        try {
            resultSet1 = statement.executeQuery(SQL_SELECT);
        } catch (PSQLException e) {
            e.getStackTrace();
        }

        int count = 0;
        while (resultSet1.next()) {
            count++;
        }

        ResultSet resultSet2 = statement.executeQuery(SQL_SELECT);
        String[][] data = new String[count + 1][resultSet2.getMetaData().getColumnCount()];
        {
            for (int t = 0; t < data[0].length; t++) {
                data[0][t] = resultSet2.getMetaData().getColumnName(t + 1);
            }

            int i = 1, j = 0;
            while (resultSet2.next()) {
                for (int k = 0; k < data[i].length; k++) {
                    data[i][j] = resultSet2.getString(k + 1);
                    j = (j + 1) % data[i].length;
                }
                i = (i + 1) % data.length;
            }
        }


        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                System.out.print(data[i][j] + " ");
            }
        }
    }

    @FXML
    private void backToMainStage() {
        LinkNavigation linkNavigation = new LinkNavigationImpl();
        linkNavigation.backToMainStage(anchorPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
