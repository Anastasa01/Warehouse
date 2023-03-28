package Supplier;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import People.People;

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

public class SupplierController implements Initializable {

    ObservableList<People> SupplierData = FXCollections.observableArrayList();
    @FXML
    private Button MainButton;

    @FXML
    private TableView<People> SupplierTable;

    @FXML
    private TableColumn<People, Integer> ColumnId;

    @FXML
    private TableColumn<People, String> ColumnName;

    @FXML
    private TableColumn<People, String> ColumnSurname;

    @FXML
    private TableColumn<People, String> ColumnPatronymic;

    @FXML
    private TableColumn<People, String> ColumnCompany;

    @FXML
    private TableColumn<People, String> ColumnAddress;

    @FXML
    private TableColumn<People, Integer> ColumnTelephone;

    @FXML
    private Button AddButton;

    @FXML
    private TextField NameTextField;

    @FXML
    private TextField SurnameTextField;

    @FXML
    private TextField PatronymicTextField;

    @FXML
    private TextField CompanyTextField;

    @FXML
    private TextField AddressTextField;

    @FXML
    private TextField TelephoneTextField;

    @FXML
    private TextField DeleteIdTextField;

    @FXML
    private Button DeleteButton;

    @FXML
    private TextField FindTextField;

    @FXML
    private Button FindButton;

    @FXML
    private Button ResetButton;

    Alert alertError = new Alert(AlertType.ERROR);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tableSupplier();

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
            addSupplier();
            tableSupplier();
        });

        DeleteButton.setOnAction(event -> {
            deleteSupplier();
            tableSupplier();
        });

        FindButton.setOnAction(event -> {
            findSupplier();
        });

        ResetButton.setOnAction(event -> {
            tableSupplier();
            FindTextField.clear();
        });
    }

    private void tableSupplier(){
        SupplierData.clear();

        try {
            DBWarehouse.readDBSupplier();
        }
        catch (Exception e) {
        }

        SupplierData = DBWarehouse.getSupplierData();

        ColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColumnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        ColumnSurname.setCellValueFactory(new PropertyValueFactory<>("Surname"));
        ColumnPatronymic.setCellValueFactory(new PropertyValueFactory<>("Patronymic"));
        ColumnCompany.setCellValueFactory(new PropertyValueFactory<>("Company"));
        ColumnAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        ColumnTelephone.setCellValueFactory(new PropertyValueFactory<>("Telephone"));
        SupplierTable.setItems(SupplierData);
    }

    private void addSupplier(){
        String Name = NameTextField.getText();
        String Surname = SurnameTextField.getText();
        String Patronymic = PatronymicTextField.getText();
        String Company = CompanyTextField.getText();
        String Address = AddressTextField.getText();
        String Tel = TelephoneTextField.getText();

        if (Name.equals("") && (Surname.equals("")) && (Patronymic.equals("")) && (Company.equals("")) && (Address.equals("")) && (Tel.equals(""))) {
            showDialogErrorNullAll();
        }
        else {
            if (Name.equals("") || (Surname.equals("")) || (Patronymic.equals("")) || (Company.equals("")) || (Address.equals("")) || (Tel.equals(""))) {
                showDialogErrorNull();
            }
            else {
                try {
                    int Telephone = Integer.parseInt(TelephoneTextField.getText());

                    DBWarehouse.writeDBSupplier(Name, Surname, Patronymic, Company, Address, Telephone);

                    NameTextField.clear();
                    SurnameTextField.clear();
                    PatronymicTextField.clear();
                    CompanyTextField.clear();
                    AddressTextField.clear();
                    TelephoneTextField.clear();

                } catch (Exception e) {
                    TelephoneTextField.clear();
                    showDialogErrorIncorrect();
                }
            }
        }
    }

    private void deleteSupplier(){

        String Id = DeleteIdTextField.getText();

        if (Id.equals("") ) {
            showDialogErrorNullId();
        }
        else {
            try {
                int id = Integer.parseInt(DeleteIdTextField.getText());

                DBWarehouse.deleteBDSupplier(id);

                DeleteIdTextField.clear();


            } catch (Exception e) {
                DeleteIdTextField.clear();
                showDialogErrorIncorrect();
            }
        }
    }

    private void findSupplier(){
        String Company = FindTextField.getText();

        if (Company.equals("") ) {
            showDialogErrorNullCompany();
        }
        else {
            try {
                DBWarehouse.selectSupplier(Company);

                SupplierData = DBWarehouse.getSupplierData();

            } catch (Exception e) {
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

    private void showDialogErrorNullCompany() {
        alertError.setTitle("Ошибка");
        alertError.setHeaderText(null);
        alertError.setContentText("Введите компанию");
        alertError.showAndWait();
    }
}
