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
import ru.andrey.dao.StoreDAOImpl;
import ru.andrey.dao.SupplierDAOImpl;
import ru.andrey.domain.Store;
import ru.andrey.domain.Supplier;
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
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class SupplierTableController implements Initializable {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TableView<Supplier> supplierTable;

    @FXML
    private TableColumn<Object, Object> supplierTypeColumn;

    @FXML
    private TableColumn<Object, Object> supplierSupplierColumn;

    private ObservableList<Supplier> suppliers;

    private void setTable() {
        GenericDAO supplierDao = new SupplierDAOImpl();
        suppliers = FXCollections.observableArrayList(supplierDao.getAll());
        supplierTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        supplierSupplierColumn.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        supplierTable.setItems(suppliers);
    }

    @FXML
    private void addSupplier() {
        Supplier tempSupplier = new Supplier();
        boolean okClicked = showSupplierEditDialog(tempSupplier);

        if (okClicked) {
            GenericDAO supplierDao = new SupplierDAOImpl();
            tempSupplier = (Supplier) supplierDao.create(tempSupplier);
            suppliers.add(tempSupplier);
        }
    }

    @FXML
    private void editSupplier() {
        Supplier selectedSupplier = supplierTable.getSelectionModel().getSelectedItem();
        int selectedIndex = supplierTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            boolean okClicked = showSupplierEditDialog(selectedSupplier);
            if (okClicked) {
                GenericDAO supplierDao = new SupplierDAOImpl();
                supplierDao.update(selectedSupplier);
                suppliers.set(selectedIndex, selectedSupplier);
            }
        }
    }

    @FXML
    private void deleteSupplier() {
        Supplier selectedSupplier = supplierTable.getSelectionModel().getSelectedItem();
        int selectedIndex = supplierTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            GenericDAO supplierDao = new SupplierDAOImpl();
            selectedSupplier = (Supplier) supplierDao.delete(selectedSupplier);
            if (selectedSupplier != null) {
                supplierTable.getItems().remove(selectedIndex);
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
        GenericDAO<Supplier> supplierDao = new SupplierDAOImpl();
        ArrayList<Supplier> suppliers = (ArrayList<Supplier>) supplierDao.getAll();
        PrintWriter out = new PrintWriter(new File("src/main/resources/Поставщик.txt"));
        for (Supplier supplier : suppliers) {
            out.print(supplier.getType() + ";" + supplier.getSupplier() + ";\r\n");
        }
        out.close();
    }

    @FXML
    private void loadData() {
        try {
            DbWork dbWork = DbWork.getInstance();
            dbWork.cleanTable("Поставщик");
            GenericDAO<Supplier> supplierDao = new SupplierDAOImpl();
            Scanner in = new Scanner(new File("src/main/resources/Поставщик.txt"));
            while (in.hasNext()) {
                String line = in.nextLine();
                String[] data = (line.split(";"));
                Supplier supplier = new Supplier(data[0], data[1]);
                supplierDao.create(supplier);
            }
            setTable();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Отображение окна редактирования
    public boolean showSupplierEditDialog(Supplier supplier) {
        try {
            // Загружаем fxml-файл и создаем новую сцену
            // для всплываюего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(EditStoreController.class.getResource("/fxml/EditSupplier.fxml"));
            AnchorPane page = loader.load();

            // Создаем диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Supplier");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(null);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаем адресата в контроллер.
            EditSupplierController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setSupplier(supplier);

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
