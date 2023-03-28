package ReceiptInvoice;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Invoice.Invoice;

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

public class ReceiptInvoiceController implements Initializable{
    ObservableList<Invoice> ReceiptInvoiceData = FXCollections.observableArrayList();
    ArrayList<String> DateSupplier = new ArrayList<String>();
    ArrayList<String> DateWarehouse = new ArrayList<String>();
    @FXML
    private Button MainButton;

    @FXML
    private TableView<Invoice> ReceiptInvoiceTable;

    @FXML
    private TableColumn<Invoice, Integer> ColumnId;

    @FXML
    private TableColumn<Invoice, String> ColumnSupplier;

    @FXML
    private TableColumn<Invoice, String> ColumnProduct;

    @FXML
    private TableColumn<Invoice, Integer> ColumnPrice;

    @FXML
    private TableColumn<Invoice, Integer> ColumnDate;

    @FXML
    private Button AddButton;

    @FXML
    private TextField PriceTextField;

    @FXML
    private ComboBox SupplierComboBox;

    @FXML
    private ComboBox WarehouseComboBox;

    @FXML
    private DatePicker DatePickerReceiptInvoice;

    Alert alertError = new Alert(AlertType.ERROR);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        getComboBoxSupplier();
        getComboBoxWarehouse();

        tableReceiptInvoice();

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
            addReceiptInvoice();
            tableReceiptInvoice();
        });
    }

    private void tableReceiptInvoice(){

        ReceiptInvoiceData.clear();

        try {
            DBWarehouse.readDBReceiptInvoice();
        }
        catch (Exception e) {
        }

        ReceiptInvoiceData = DBWarehouse.getReceiptInvoiceData();

        ColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColumnSupplier.setCellValueFactory(new PropertyValueFactory<>("Supplier"));
        ColumnProduct.setCellValueFactory(new PropertyValueFactory<>("Product"));
        ColumnPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        ColumnDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        ReceiptInvoiceTable.setItems(ReceiptInvoiceData);
    }

    private void addReceiptInvoice(){
        if ((SupplierComboBox.getValue() == null) && (WarehouseComboBox.getValue() == null) && (PriceTextField.getText().equals("")) && (DatePickerReceiptInvoice.getValue() == null)) {
            showDialogErrorNullAll();
        }
        else {
            if ((SupplierComboBox.getValue() == null) || (WarehouseComboBox.getValue() == null) || (PriceTextField.getText().equals("")) || (DatePickerReceiptInvoice.getValue() == null)) {
                showDialogErrorNull();
            }
            else {
                try {
                    String Supplier = SupplierComboBox.getValue().toString();
                    String Product = WarehouseComboBox.getValue().toString();
                    String Date = DatePickerReceiptInvoice.getValue().toString();

                    int Price = Integer.parseInt(PriceTextField.getText());

                    DBWarehouse.writeDBReceiptInvoice(Supplier, Product, Price, Date);

                    SupplierComboBox.setValue(null);
                    WarehouseComboBox.setValue(null);
                    PriceTextField.clear();
                    DatePickerReceiptInvoice.setValue(null);
                }
                catch (Exception e) {
                    PriceTextField.clear();
                    showDialogErrorIncorrect();
                }
            }
        }
    }

    public void getComboBoxSupplier() {

        DateSupplier.clear();

        try {
            DBWarehouse.selectComboBoxSupplier();
        } catch (Exception e) {

        }

        DateSupplier = DBWarehouse.getDataSupplier();

        SupplierComboBox.getItems().addAll(DateSupplier);
    }

    public void getComboBoxWarehouse() {

        DateWarehouse.clear();

        try {
            DBWarehouse.selectComboBoxWarehouse();
        } catch (Exception e) {

        }

        DateWarehouse = DBWarehouse.getDataWarehouse();

        WarehouseComboBox.getItems().addAll(DateWarehouse);
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
}
