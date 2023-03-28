package Buyer;

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

public class BuyerController implements Initializable {

    ObservableList<People> BuyerData = FXCollections.observableArrayList();
    @FXML
    private Button MainButton;

    @FXML
    private TableView<People> BuyerTable;

    @FXML
    private TableColumn<People, Integer> ColumnId;

    @FXML
    private TableColumn<People, String> ColumnName;

    @FXML
    private TableColumn<People, String> ColumnSurname;

    @FXML
    private TableColumn<People, String> ColumnPatronymic;

    @FXML
    private TableColumn<People, Integer> ColumnPassport;

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
    private TextField PassportTextField;

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

        tableBuyer();

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
            addBuyer();
            tableBuyer();
        });

        DeleteButton.setOnAction(event -> {
            deleteBuyer();
            tableBuyer();
        });

        FindButton.setOnAction(event -> {
            findBuyer();
        });

        ResetButton.setOnAction(event -> {
            tableBuyer();
            FindTextField.clear();
        });
    }

    private void tableBuyer(){
        BuyerData.clear();

        try {
            DBWarehouse.readDBBuyer();
        }
        catch (Exception e) {
        }

        BuyerData = DBWarehouse.getBuyerData();

        ColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColumnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        ColumnSurname.setCellValueFactory(new PropertyValueFactory<>("Surname"));
        ColumnPatronymic.setCellValueFactory(new PropertyValueFactory<>("Patronymic"));
        ColumnPassport.setCellValueFactory(new PropertyValueFactory<>("Passport"));
        ColumnAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        ColumnTelephone.setCellValueFactory(new PropertyValueFactory<>("Telephone"));
        BuyerTable.setItems(BuyerData);
    }

    private void addBuyer(){
        String Name = NameTextField.getText();
        String Surname = SurnameTextField.getText();
        String Patronymic = PatronymicTextField.getText();
        String Pass = PassportTextField.getText();
        String Address = AddressTextField.getText();
        String Tel = TelephoneTextField.getText();

        if (Name.equals("") && (Surname.equals("")) && (Patronymic.equals("")) && (Pass.equals("")) && (Address.equals("")) && (Tel.equals(""))) {
            showDialogErrorNullAll();
        }
        else {
            if (Name.equals("") || (Surname.equals("")) || (Patronymic.equals("")) || (Pass.equals("")) || (Address.equals("")) || (Tel.equals(""))) {
                showDialogErrorNull();
            }
            else {
                try {
                    int Telephone = Integer.parseInt(TelephoneTextField.getText());
                    int Passport = Integer.parseInt(PassportTextField.getText());

                    int count = 6;
                    if (PassportTextField.getText().length() == count) {

                        DBWarehouse.writeDBBuyer(Name, Surname, Patronymic, Passport, Address, Telephone);

                        NameTextField.clear();
                        SurnameTextField.clear();
                        PatronymicTextField.clear();
                        PassportTextField.clear();
                        AddressTextField.clear();
                        TelephoneTextField.clear();

                    } else {
                        PassportTextField.clear();
                        showDialogErrorPassport();
                    }
                }
                catch (Exception e) {
                    TelephoneTextField.clear();
                    PassportTextField.clear();
                    showDialogErrorIncorrect();
                }
            }
        }
    }

    private void deleteBuyer(){
        String Id = DeleteIdTextField.getText();

        if (Id.equals("") ) {
            showDialogErrorNullId();
        }
        else {
            try {
                int id = Integer.parseInt(DeleteIdTextField.getText());

                DBWarehouse.deleteBDBuyer(id);

                DeleteIdTextField.clear();

            } catch (Exception e) {
                DeleteIdTextField.clear();
                showDialogErrorIncorrect();
            }
        }
    }

    private void findBuyer(){

        if (FindTextField.getText().equals("") ) {
            showDialogErrorNullPassport();
        }
        else {
            try {
                int Passport = Integer.parseInt(FindTextField.getText());

                int count = 6;
                if (FindTextField.getText().length() == count) {

                    DBWarehouse.selectBuyer(Passport);

                    BuyerData = DBWarehouse.getBuyerData();
                }
                else {
                    showDialogErrorPassportFind();
                }

            } catch (Exception e) {
                FindTextField.clear();
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
    private void showDialogErrorPassport() {
        alertError.setTitle("Ошибка");
        alertError.setHeaderText(null);
        alertError.setContentText("Поле поспорт должно содержать 6 цифр");
        alertError.showAndWait();
    }
    private void showDialogErrorNullPassport() {
        alertError.setTitle("Ошибка");
        alertError.setHeaderText(null);
        alertError.setContentText("Введите паспорт");
        alertError.showAndWait();
    }

    private void showDialogErrorPassportFind() {
        alertError.setTitle("Ошибка");
        alertError.setHeaderText(null);
        alertError.setContentText("Для поиска по паспорту поле должно содержать 6 цифр");
        alertError.showAndWait();
    }
}
