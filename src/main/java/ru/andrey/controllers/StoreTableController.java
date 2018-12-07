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
import ru.andrey.dao.SellerDAOImpl;
import ru.andrey.dao.StoreDAOImpl;
import ru.andrey.domain.Seller;
import ru.andrey.domain.Store;
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

public class StoreTableController implements Initializable {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TableView<Store> storeTable;

    @FXML
    private TableColumn<Object, Object> storeNameColumn;

    @FXML
    private TableColumn<Object, Object> storeTypeColumn;

    private ObservableList<Store> stores;

    private void setTable() {
        GenericDAO storeDao = new StoreDAOImpl();
        stores = FXCollections.observableArrayList(storeDao.getAll());
        storeNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        storeTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        storeTable.setItems(stores);
    }

    @FXML
    private void addStore() {
        Store tempStore = new Store();
        boolean okClicked = showStoreEditDialog(tempStore);

        if (okClicked) {
            GenericDAO storeDao = new StoreDAOImpl();
            tempStore = (Store) storeDao.create(tempStore);
            stores.add(tempStore);
        }
    }

    @FXML
    private void editStore() {
        Store selectedStore = storeTable.getSelectionModel().getSelectedItem();
        int selectedIndex = storeTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            boolean okClicked = showStoreEditDialog(selectedStore);
            if (okClicked) {
                GenericDAO storeDao = new StoreDAOImpl();
                storeDao.update(selectedStore);
                stores.set(selectedIndex, selectedStore);
            }
        }
    }

    @FXML
    private void deleteStore() {
        Store selectedStore = storeTable.getSelectionModel().getSelectedItem();
        int selectedIndex = storeTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            GenericDAO storeDao = new StoreDAOImpl();
            selectedStore = (Store) storeDao.delete(selectedStore);
            if (selectedStore != null) {
                storeTable.getItems().remove(selectedIndex);
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
        GenericDAO<Store> storeDao = new StoreDAOImpl();
        ArrayList<Store> stores = (ArrayList<Store>) storeDao.getAll();
        PrintWriter out = new PrintWriter(new File("src/main/resources/Магазин.txt"));
        for (Store store : stores) {
            out.print(store.getName() + ";" + store.getType() + ";\r\n");
        }
        out.close();
    }

    @FXML
    private void loadData() {
        try {
            DbWork dbWork = DbWork.getInstance();
            dbWork.cleanTable("Магазин");
            GenericDAO<Store> storeDao = new StoreDAOImpl();
            Scanner in = new Scanner(new File("src/main/resources/Магазин.txt"));
            while (in.hasNext()) {
                String line = in.nextLine();
                String[] data = (line.split(";"));
                Store store = new Store(data[0], data[1]);
                storeDao.create(store);
            }
            setTable();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Отображение окна редактирования
    public boolean showStoreEditDialog(Store store) {
        try {
            // Загружаем fxml-файл и создаем новую сцену
            // для всплываюего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(EditStoreController.class.getResource("/fxml/EditStore.fxml"));
            AnchorPane page = loader.load();

            // Создаем диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Store");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(null);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаем адресата в контроллер.
            EditStoreController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setStore(store);

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
