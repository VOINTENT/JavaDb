package ru.andrey.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.andrey.dao.GenericDAO;
import ru.andrey.dao.RegistrationDAOImpl;
import ru.andrey.domain.Registration;
import ru.andrey.navigation.AlertNavigation;
import ru.andrey.navigation.AlertNavigationImpl;
import ru.andrey.navigation.LinkNavigation;
import ru.andrey.navigation.LinkNavigationImpl;
import ru.andrey.util.DbWork;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class RegistrationTableController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TableView<Registration> registrationTable;

    @FXML
    private TableColumn<Object, Object> registrationNameColumn;

    @FXML
    private TableColumn<Object, Object> registrationDateColumn;

    @FXML
    private TableColumn<Object, Object> registrationCountPositionsColumn;

    private ObservableList<Registration> registrations;

    private void setTable() {
        GenericDAO registrationDAO = new RegistrationDAOImpl();
        registrations = FXCollections.observableArrayList(registrationDAO.getAll());
        registrationNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        registrationDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        registrationCountPositionsColumn.setCellValueFactory(new PropertyValueFactory<>("countPositions"));
        registrationTable.setItems(registrations);
    }

    @FXML
    private void addRegistration() {
        Registration tempRegistration = new Registration();
        boolean okClicked = showRegistrationEditDialog(tempRegistration);

        if (okClicked) {
            GenericDAO registrationDAO = new RegistrationDAOImpl();
            tempRegistration = (Registration) registrationDAO.create(tempRegistration);
            registrations.add(tempRegistration);
        }
    }

    @FXML
    private void editRegistration() {
        Registration selectedRegistration = registrationTable.getSelectionModel().getSelectedItem();
        int selectedIndex = registrationTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            boolean okClicked = showRegistrationEditDialog(selectedRegistration);
            if (okClicked) {
                GenericDAO contactDao = new RegistrationDAOImpl();
                contactDao.update(selectedRegistration);
                registrations.set(selectedIndex, selectedRegistration);
            }
        }
    }

    @FXML
    private void deleteRegistration() {
        Registration selectedRegistration = registrationTable.getSelectionModel().getSelectedItem();
        int selectedIndex = registrationTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            GenericDAO registrationDao = new RegistrationDAOImpl();
            selectedRegistration = (Registration) registrationDao.delete(selectedRegistration);
            if (selectedRegistration != null) {
                registrationTable.getItems().remove(selectedIndex);
            } else {
                AlertNavigation alertNavigation = new AlertNavigationImpl();
                alertNavigation.createAlertError();
            }
        }
    }

    @FXML
    private void backToMainStage() {
        LinkNavigation linkNavigation = new LinkNavigationImpl();
        linkNavigation.backToMainStage(anchorPane);
    }

    @FXML
    private void saveData() throws FileNotFoundException {
        GenericDAO<Registration> registrationDao = new RegistrationDAOImpl();
        ArrayList<Registration> registrations = (ArrayList<Registration>) registrationDao.getAll();
        PrintWriter out = new PrintWriter(new File("src/main/resources/Регистрация.txt"));
        for (Registration registration : registrations) {
            out.print(registration.getName() + ";" + registration.getDate() + ";" + registration.getCountPositions() + ";\r\n");
        }
        out.close();
    }

    @FXML
    private void loadData() {
        try {
            DbWork dbWork = DbWork.getInstance();
            dbWork.cleanTable("Регистрация");
            GenericDAO<Registration> registrationDao = new RegistrationDAOImpl();
            Scanner in = new Scanner(new File("src/main/resources/Регистрация.txt"));
            while (in.hasNext()) {
                String line = in.nextLine();
                String[] data = (line.split(";"));
                Registration registration = new Registration(data[0], Date.valueOf(data[1]), Integer.valueOf(data[2]));
                registrationDao.create(registration);
            }
            setTable();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Отображение окна редактирования
    public boolean showRegistrationEditDialog(Registration registration) {
        try {
            // Загружаем fxml-файл и создаем новую сцену
            // для всплываюего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(EditRegistrationController.class.getResource("/fxml/EditRegistration.fxml"));
            AnchorPane page = loader.load();

            // Создаем диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Registration");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(null);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаем адресата в контроллер.
            EditRegistrationController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setRegistration(registration);

            // Отображаем диалоговое окно и ждем, пока пользователь его не закроет
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTable();
    }
}
