package Warehouse;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Objectt.Objectt;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import system.DBWarehouse;
public class WarehouseController implements Initializable{
    ObservableList<Objectt> WarehouseData = FXCollections.observableArrayList();
    ObservableList<String> UnitData = FXCollections.observableArrayList("шт", "кг", "м", "л");

    ArrayList<String> DateProduct = new ArrayList<String>();
    @FXML
    private Button MainButton;

    @FXML
    private TableView<Objectt> WarehouseTable;

    @FXML
    private TableColumn<Objectt, Integer> ColumnId;

    @FXML
    private TableColumn<Objectt, String> ColumnName;

    @FXML
    private TableColumn<Objectt, Integer> ColumnUnit;

    @FXML
    private Button AddButton;

    @FXML
    private TextField DeleteIdTextField;

    @FXML
    private Button DeleteButton;

    @FXML
    private ComboBox ProductComboBox;

    @FXML
    private ComboBox UnitComboBox;

    Alert alertError = new Alert(AlertType.ERROR);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        getComboBoxProduct();
        getComboBoxUnit();

        tableWarehouse();

        MainButton.setOnAction((event -> {
            Stage stage = (Stage) MainButton.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/Main.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Учет товара на складе");
            stage.setResizable(false);
            stage.show();
        }));

        AddButton.setOnAction(event -> {
            addWarehouse();
            tableWarehouse();
        });

        DeleteButton.setOnAction(event -> {
            deleteWarehouse();
            tableWarehouse();
        });
    }

    private void tableWarehouse(){

        WarehouseData.clear();

        try {
            DBWarehouse.readDBWarehouse();
        }
        catch (Exception e) {
        }

        WarehouseData = DBWarehouse.getWarehouseData();

        ColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColumnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        ColumnUnit.setCellValueFactory(new PropertyValueFactory<>("Unit"));
        WarehouseTable.setItems(WarehouseData);
    }

    private void addWarehouse(){
        if ((ProductComboBox.getValue() == null) && (UnitComboBox.getValue() == null)) {
            showDialogErrorNullAll();
        }
        else {
            if ((ProductComboBox.getValue() == null) || (UnitComboBox.getValue() == null)) {
                showDialogErrorNull();
            }
            else {
                String Name = ProductComboBox.getValue().toString();
                String Unit = UnitComboBox.getValue().toString();

                try {

                    DBWarehouse.writeDBWarehouse(Name, Unit);

                    ProductComboBox.setValue(null);
                    UnitComboBox.setValue(null);
                }
                catch (Exception e) {
                }
            }
        }
    }

    private void deleteWarehouse(){

        String Id = DeleteIdTextField.getText();

        if (Id.equals("") ) {
            showDialogErrorNullId();
        }
        else {
            try {
                int id = Integer.parseInt(DeleteIdTextField.getText());

                DBWarehouse.deleteBDWarehouse(id);

                DeleteIdTextField.clear();

            } catch (Exception e) {
                DeleteIdTextField.clear();
                showDialogErrorIncorrect();
            }
        }
    }

    public void getComboBoxProduct() {

        DateProduct.clear();

        try {
            DBWarehouse.selectComboBoxProduct();
        } catch (Exception e) {

        }

        DateProduct = DBWarehouse.getDataProduct();

        ProductComboBox.getItems().addAll(DateProduct);
    }

    public void getComboBoxUnit() {
        UnitComboBox.getItems().addAll(UnitData);
    }

    private void showDialogErrorNullAll() {
        alertError.setTitle("Ошибка");
        alertError.setHeaderText(null);
        alertError.setContentText("Введите данные");
        alertError.showAndWait();
    }
    private void showDialogErrorNull() {
        alertError.setTitle("Ошибка");
        alertError.setHeaderText(null);
        alertError.setContentText("Не все поля заполнены");
        alertError.showAndWait();
    }
    private void showDialogErrorIncorrect() {
        alertError.setTitle("Ошибка");
        alertError.setHeaderText(null);
        alertError.setContentText("Некорректный ввод данных");
        alertError.showAndWait();
    }
    private void showDialogErrorNullId() {
        alertError.setTitle("Ошибка");
        alertError.setHeaderText(null);
        alertError.setContentText("Введите идентификатор");
        alertError.showAndWait();
    }
}
