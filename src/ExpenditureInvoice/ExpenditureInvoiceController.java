package ExpenditureInvoice;

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
public class ExpenditureInvoiceController implements Initializable {
    ObservableList<Invoice> ExpenditureInvoiceData = FXCollections.observableArrayList();
    ArrayList<String> DateBuyer = new ArrayList<String>();
    ArrayList<String> DateReceiptInvoice = new ArrayList<String>();
    @FXML
    private Button MainButton;

    @FXML
    private TableView<Invoice> ExpenditureInvoiceTable;

    @FXML
    private TableColumn<Invoice, Integer> ColumnId;

    @FXML
    private TableColumn<Invoice, String> ColumnBuyer;

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
    private TextField ArchiveTextField;

    @FXML
    private Button ArchiveButton;

    @FXML
    private ComboBox BuyerComboBox;

    @FXML
    private ComboBox ReceiptInvoiceComboBox;

    @FXML
    private DatePicker DatePickerExpenditureInvoice;

    Alert alertError = new Alert(AlertType.ERROR);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        getComboBoxBuyer();
        getComboBoxReceiptInvoice();

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

        AddButton.setOnAction(event -> {
            addExpenditureInvoice();
            tableExpenditureInvoice();
        });

        ArchiveButton.setOnAction(event -> {
            archiveExpenditureInvoice();
            tableExpenditureInvoice();
        });
    }

    private void tableExpenditureInvoice(){

        ExpenditureInvoiceData.clear();

        try {
            DBWarehouse.readDBExpenditureInvoice();
        }
        catch (Exception e) {
        }

        ExpenditureInvoiceData = DBWarehouse.getExpenditureInvoiceData();

        ColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColumnBuyer.setCellValueFactory(new PropertyValueFactory<>("Buyer"));
        ColumnProduct.setCellValueFactory(new PropertyValueFactory<>("Product"));
        ColumnPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        ColumnDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        ExpenditureInvoiceTable.setItems(ExpenditureInvoiceData);
    }

    private void addExpenditureInvoice(){
        if ((BuyerComboBox.getValue() == null) && (ReceiptInvoiceComboBox.getValue() == null) && (PriceTextField.getText().equals("")) && (DatePickerExpenditureInvoice.getValue() == null)) {
            showDialogErrorNullAll();
        }
        else {
            if ((BuyerComboBox.getValue() == null) || (ReceiptInvoiceComboBox.getValue() == null) || (PriceTextField.getText().equals("")) || (DatePickerExpenditureInvoice.getValue() == null)) {
                showDialogErrorNull();
            }
            else {
                try {
                    String Buyer = BuyerComboBox.getValue().toString();
                    String Product = ReceiptInvoiceComboBox.getValue().toString();
                    String Date = DatePickerExpenditureInvoice.getValue().toString();

                    int Price = Integer.parseInt(PriceTextField.getText());

                    DBWarehouse.writeDBExpenditureInvoice(Buyer, Product, Price, Date);

                    BuyerComboBox.setValue(null);
                    ReceiptInvoiceComboBox.setValue(null);
                    PriceTextField.clear();
                    DatePickerExpenditureInvoice.setValue(null);
                }
                catch (Exception e) {
                    PriceTextField.clear();
                    showDialogErrorIncorrect();
                }
            }
        }
    }

    public void getComboBoxBuyer() {

        DateBuyer.clear();

        try {
            DBWarehouse.selectComboBoxBuyer();
        } catch (Exception e) {

        }

        DateBuyer = DBWarehouse.getDataBuyer();

        BuyerComboBox.getItems().addAll(DateBuyer);
    }

    public void getComboBoxReceiptInvoice() {

        DateReceiptInvoice.clear();

        try {
            DBWarehouse.selectComboBoxReceiptInvoice();
        } catch (Exception e) {

        }

        DateReceiptInvoice = DBWarehouse.getDataReceiptInvoice();

        ReceiptInvoiceComboBox.getItems().addAll(DateReceiptInvoice);
    }

    private void archiveExpenditureInvoice(){

        String Id = ArchiveTextField.getText();

        if (Id.equals("") ) {
            showDialogErrorNullId();
        }
        else {
            try {
                int id = Integer.parseInt(ArchiveTextField.getText());

                DBWarehouse.archiveBDExpenditureInvoice(id);

                ArchiveTextField.clear();

            } catch (Exception e) {
                ArchiveTextField.clear();
                showDialogErrorIncorrect();
            }
        }
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
