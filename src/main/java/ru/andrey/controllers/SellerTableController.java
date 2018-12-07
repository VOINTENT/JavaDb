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
import ru.andrey.dao.SellerDAOImpl;
import ru.andrey.domain.Registration;
import ru.andrey.domain.Seller;
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

public class SellerTableController implements Initializable {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TableView<Seller> sellerTable;

    @FXML
    private TableColumn<Object, Object> sellerNameColumn;

    @FXML
    private TableColumn<Object, Object> sellerAreaColumn;

    @FXML
    private TableColumn<Object, Object> sellerNameProductColumn;

    @FXML
    private TableColumn<Object, Object> sellerPriceColumn;

    @FXML
    private TableColumn<Object, Object> sellerCountColumn;

    private ObservableList<Seller> sellers;

    private void setTable() {
        GenericDAO sellerDao = new SellerDAOImpl();
        sellers = FXCollections.observableArrayList(sellerDao.getAll());
        sellerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        sellerAreaColumn.setCellValueFactory(new PropertyValueFactory<>("area"));
        sellerNameProductColumn.setCellValueFactory(new PropertyValueFactory<>("nameProduct"));
        sellerPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        sellerCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        sellerTable.setItems(sellers);
    }

    @FXML
    private void addSeller() {
        Seller tempSeller = new Seller();
        boolean okClicked = showSellerEditDialog(tempSeller);

        if (okClicked) {
            GenericDAO sellerDao = new SellerDAOImpl();
            tempSeller = (Seller) sellerDao.create(tempSeller);
            sellers.add(tempSeller);
        }
    }

    @FXML
    private void editSeller() {
        Seller selectedSeller = sellerTable.getSelectionModel().getSelectedItem();
        int selectedIndex = sellerTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            boolean okClicked = showSellerEditDialog(selectedSeller);
            if (okClicked) {
                GenericDAO sellerDao = new SellerDAOImpl();
                sellerDao.update(selectedSeller);
                sellers.set(selectedIndex, selectedSeller);
            }
        }
    }

    @FXML
    private void deleteSeller() {
        Seller selectedSeller = sellerTable.getSelectionModel().getSelectedItem();
        int selectedIndex = sellerTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            GenericDAO sellerDao = new SellerDAOImpl();
            selectedSeller = (Seller) sellerDao.delete(selectedSeller);
            if (selectedSeller != null) {
                sellerTable.getItems().remove(selectedIndex);
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
        GenericDAO<Seller> sellerDao = new SellerDAOImpl();
        ArrayList<Seller> sellers = (ArrayList<Seller>) sellerDao.getAll();
        PrintWriter out = new PrintWriter(new File("src/main/resources/Продавцы.txt"));
        for (Seller seller : sellers) {
            out.print(seller.getName() + ";" + seller.getArea() + ";" + seller.getNameProduct() + ";" + seller.getPrice() + ";" + seller.getCount() + ";\r\n");
        }
        out.close();
    }

    @FXML
    private void loadData() {
        try {
            DbWork dbWork = DbWork.getInstance();
            dbWork.cleanTable("Продавцы");
            GenericDAO<Seller> sellerDao = new SellerDAOImpl();
            Scanner in = new Scanner(new File("src/main/resources/Продавцы.txt"));
            while (in.hasNext()) {
                String line = in.nextLine();
                String[] data = (line.split(";"));
                Seller seller = new Seller(data[0], data[1], data[2], Integer.parseInt(data[3]), Integer.parseInt(data[4]));
                sellerDao.create(seller);
            }
            setTable();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Отображение окна редактирования
    public boolean showSellerEditDialog(Seller seller) {
        try {
            // Загружаем fxml-файл и создаем новую сцену
            // для всплываюего диалогового окна.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(EditSellerController.class.getResource("/fxml/EditSeller.fxml"));
            AnchorPane page = loader.load();

            // Создаем диалоговое окно Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Seller");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(null);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Передаем адресата в контроллер.
            EditSellerController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setSeller(seller);

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
