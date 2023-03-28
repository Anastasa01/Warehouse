package Archive;
import java.io.IOException;
import java.net.URL;
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
public class ArchiveController implements Initializable {

    ObservableList<Invoice> ArchiveReceiptInvoiceData = FXCollections.observableArrayList();
    ObservableList<Invoice> ArchiveExpenditureInvoiceData = FXCollections.observableArrayList();
    @FXML
    private Button MainButton;

    @FXML
    private TableView<Invoice> ArchiveReceiptInvoiceTable;

    @FXML
    private TableColumn<Invoice, Integer> ColumnIdReceiptInvoice;

    @FXML
    private TableColumn<Invoice, String> ColumnSupplierReceiptInvoice;

    @FXML
    private TableColumn<Invoice, String> ColumnProductReceiptInvoice;

    @FXML
    private TableColumn<Invoice, Integer> ColumnPriceReceiptInvoice;

    @FXML
    private TableColumn<Invoice, Integer> ColumnDateReceiptInvoice;

    @FXML
    private TableView<Invoice> ArchiveExpenditureInvoiceTable;

    @FXML
    private TableColumn<Invoice, Integer> ColumnIdExpenditureInvoice;

    @FXML
    private TableColumn<Invoice, String> ColumnBuyerExpenditureInvoice;

    @FXML
    private TableColumn<Invoice, String> ColumnProductExpenditureInvoice;

    @FXML
    private TableColumn<Invoice, Integer> ColumnPriceExpenditureInvoice;

    @FXML
    private TableColumn<Invoice, Integer> ColumnDateExpenditureInvoice;

    @FXML
    private TextField DeleteIdArchiveReceiptInvoiceTextField;

    @FXML
    private Button DeleteArchiveReceiptInvoiceButton;

    @FXML
    private TextField DeleteIdArchiveExpenditureInvoiceTextField;

    @FXML
    private Button DeleteArchiveExpenditureInvoiceButton;

    @FXML
    private RadioButton RadioButtonReceiptInvoicePriceMore;

    @FXML
    private RadioButton RadioButtonReceiptInvoicePriceLess;

    @FXML
    private RadioButton RadioButtonReceiptInvoiceDateMore;

    @FXML
    private RadioButton RadioButtonReceiptInvoiceDateLess;

    @FXML
    private Button ResetArchiveReceiptInvoiceButton;

    @FXML
    private RadioButton RadioButtonExpenditureInvoicePriceMore;

    @FXML
    private RadioButton RadioButtonExpenditureInvoicePriceLess;

    @FXML
    private RadioButton RadioButtonExpenditureInvoiceDateMore;

    @FXML
    private RadioButton RadioButtonExpenditureInvoiceDateLess;

    @FXML
    private Button ResetArchiveExpenditureInvoiceButton;

    Alert alertError = new Alert(AlertType.ERROR);

    public void initialize(URL url, ResourceBundle resourceBundle) {

        tableReceiptInvoice();
        tableExpenditureInvoice();

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

        DeleteArchiveReceiptInvoiceButton.setOnAction(event -> {
            deleteArchiveReceiptInvoice();
            tableReceiptInvoice();
        });

        DeleteArchiveExpenditureInvoiceButton.setOnAction(event -> {
            deleteArchiveExpenditureInvoice();
            tableExpenditureInvoice();
        });

        ResetArchiveReceiptInvoiceButton.setOnAction(event -> {
            tableReceiptInvoice();
        });

        ResetArchiveExpenditureInvoiceButton.setOnAction(event -> {
            tableExpenditureInvoice();
        });

        RadioButtonReceiptInvoicePriceMore.setOnAction(event -> {
            getArchiveReceiptInvoicePriceMore();
        });

        RadioButtonReceiptInvoicePriceLess.setOnAction(event -> {
            getArchiveReceiptInvoicePriceLess();
        });

        RadioButtonReceiptInvoiceDateMore.setOnAction(event -> {
            getArchiveReceiptInvoiceDateMore();
        });

        RadioButtonReceiptInvoiceDateLess.setOnAction(event -> {
            getArchiveReceiptInvoiceDateLess();
        });

        RadioButtonExpenditureInvoicePriceMore.setOnAction(event -> {
            getArchiveExpenditureInvoicePriceMore();
        });

        RadioButtonExpenditureInvoicePriceLess.setOnAction(event -> {
            getArchiveExpenditureInvoicePriceLess();
        });

        RadioButtonExpenditureInvoiceDateMore.setOnAction(event -> {
            getArchiveExpenditureInvoiceDateMore();
        });

        RadioButtonExpenditureInvoiceDateLess.setOnAction(event -> {
            getArchiveExpenditureInvoiceDateLess();
        });

    }

    private void tableReceiptInvoice(){

        ArchiveReceiptInvoiceData.clear();

        try {
            DBWarehouse.readDBArchiveReceiptInvoice();
        }
        catch (Exception e) {
        }

        ArchiveReceiptInvoiceData = DBWarehouse.getArchiveReceiptInvoiceData();

        ColumnIdReceiptInvoice.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColumnSupplierReceiptInvoice.setCellValueFactory(new PropertyValueFactory<>("Supplier"));
        ColumnProductReceiptInvoice.setCellValueFactory(new PropertyValueFactory<>("Product"));
        ColumnPriceReceiptInvoice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        ColumnDateReceiptInvoice.setCellValueFactory(new PropertyValueFactory<>("Date"));
        ArchiveReceiptInvoiceTable.setItems(ArchiveReceiptInvoiceData);
    }

    private void tableExpenditureInvoice(){

        ArchiveExpenditureInvoiceData.clear();

        try {
            DBWarehouse.readDBArchiveExpenditureInvoice();
        }
        catch (Exception e) {
        }

        ArchiveExpenditureInvoiceData = DBWarehouse.getArchiveExpenditureInvoiceData();

        ColumnIdExpenditureInvoice.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColumnBuyerExpenditureInvoice.setCellValueFactory(new PropertyValueFactory<>("Buyer"));
        ColumnProductExpenditureInvoice.setCellValueFactory(new PropertyValueFactory<>("Product"));
        ColumnPriceExpenditureInvoice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        ColumnDateExpenditureInvoice.setCellValueFactory(new PropertyValueFactory<>("Date"));
        ArchiveExpenditureInvoiceTable.setItems(ArchiveExpenditureInvoiceData);
    }

    private void deleteArchiveReceiptInvoice(){

        String Id = DeleteIdArchiveReceiptInvoiceTextField.getText();

        if (Id.equals("") ) {
            showDialogErrorNullId();
        }
        else {
            try {
                int id = Integer.parseInt(DeleteIdArchiveReceiptInvoiceTextField.getText());

                DBWarehouse.deleteBDArchiveReceiptInvoice(id);

                DeleteIdArchiveReceiptInvoiceTextField.clear();

            } catch (Exception e) {
                DeleteIdArchiveReceiptInvoiceTextField.clear();
                showDialogErrorIncorrect();
            }
        }
    }

    private void deleteArchiveExpenditureInvoice(){

        String Id = DeleteIdArchiveExpenditureInvoiceTextField.getText();

        if (Id.equals("") ) {
            showDialogErrorNullId();
        }
        else {
            try {
                int id = Integer.parseInt(DeleteIdArchiveExpenditureInvoiceTextField.getText());

                DBWarehouse.deleteBDArchiveExpenditureInvoice(id);

                DeleteIdArchiveExpenditureInvoiceTextField.clear();

            } catch (Exception e) {
                DeleteIdArchiveExpenditureInvoiceTextField.clear();
                showDialogErrorIncorrect();
            }
        }
    }

    private void getArchiveReceiptInvoicePriceMore(){
        try {
            ArchiveReceiptInvoiceData.clear();

            DBWarehouse.selectArchiveReceiptInvoicePriceMore();

            ArchiveReceiptInvoiceData = DBWarehouse.getArchiveReceiptInvoiceData();
        }
        catch (Exception e) {
        }
    }
    private void getArchiveReceiptInvoicePriceLess(){
        try {
            ArchiveReceiptInvoiceData.clear();

            DBWarehouse.selectArchiveReceiptInvoicePriceLess();

            ArchiveReceiptInvoiceData = DBWarehouse.getArchiveReceiptInvoiceData();
        }
        catch (Exception e) {
        }
    }
    private void getArchiveReceiptInvoiceDateMore(){
        try {
            ArchiveReceiptInvoiceData.clear();

            DBWarehouse.selectArchiveReceiptInvoiceDateMore();

            ArchiveReceiptInvoiceData = DBWarehouse.getArchiveReceiptInvoiceData();
        }
        catch (Exception e) {
        }
    }
    private void getArchiveReceiptInvoiceDateLess(){
        try {
            ArchiveReceiptInvoiceData.clear();

            DBWarehouse.selectArchiveReceiptInvoiceDateLess();

            ArchiveReceiptInvoiceData = DBWarehouse.getArchiveReceiptInvoiceData();
        }
        catch (Exception e) {
        }
    }

    private void getArchiveExpenditureInvoicePriceMore(){
        try {
            ArchiveExpenditureInvoiceData.clear();

            DBWarehouse.selectArchiveExpenditureInvoicePriceMore();

            ArchiveExpenditureInvoiceData = DBWarehouse.getArchiveExpenditureInvoiceData();
        }
        catch (Exception e) {
        }
    }
    private void getArchiveExpenditureInvoicePriceLess(){
        try {
            ArchiveExpenditureInvoiceData.clear();

            DBWarehouse.selectArchiveExpenditureInvoicePriceLess();

            ArchiveExpenditureInvoiceData = DBWarehouse.getArchiveExpenditureInvoiceData();
        }
        catch (Exception e) {
        }
    }
    private void getArchiveExpenditureInvoiceDateMore(){
        try {
            ArchiveExpenditureInvoiceData.clear();

            DBWarehouse.selectArchiveExpenditureInvoiceDateMore();

            ArchiveExpenditureInvoiceData = DBWarehouse.getArchiveExpenditureInvoiceData();
        }
        catch (Exception e) {
        }
    }
    private void getArchiveExpenditureInvoiceDateLess(){
        try {
            ArchiveExpenditureInvoiceData.clear();

            DBWarehouse.selectArchiveExpenditureInvoiceDateLess();

            ArchiveExpenditureInvoiceData = DBWarehouse.getArchiveExpenditureInvoiceData();
        }
        catch (Exception e) {
        }
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
