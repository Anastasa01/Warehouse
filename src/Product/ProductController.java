package Product;

import java.io.IOException;
import java.net.URL;
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

public class ProductController implements Initializable {

    ObservableList<Objectt> ProductData = FXCollections.observableArrayList();

    ObservableList<String> CategoryData = FXCollections.observableArrayList("Корм", "Наполнитель", "Клетки и переноски", "Аксессуары", "Игрушки", "Лакомства");
    @FXML
    private Button MainButton;

    @FXML
    private TableView<Objectt> ProductTable;

    @FXML
    private TableColumn<Objectt, Integer> ColumnId;

    @FXML
    private TableColumn<Objectt, String> ColumnName;

    @FXML
    private TableColumn<Objectt, String> ColumnCategory;

    @FXML
    private Button AddButton;

    @FXML
    private TextField NameTextField;

    @FXML
    private TextField DeleteIdTextField;

    @FXML
    private Button DeleteButton;

    @FXML
    private ComboBox CategoryComboBox;

    @FXML
    private TextField FindTextField;

    @FXML
    private Button FindButton;

    @FXML
    private Button ResetButton;

    Alert alertError = new Alert(AlertType.ERROR);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        getComboBoxCategory();

        tableProduct();

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
            addProduct();
            tableProduct();
        });

        DeleteButton.setOnAction(event -> {
            deleteProduct();
            tableProduct();
        });

        FindButton.setOnAction(event -> {
            findProduct();
        });

        ResetButton.setOnAction(event -> {
            tableProduct();
            FindTextField.clear();
        });
    }

    private void tableProduct(){

        ProductData.clear();

        try {
            DBWarehouse.readDBProduct();
        }
        catch (Exception e) {
        }

        ProductData = DBWarehouse.getProductData();

        ColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColumnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        ColumnCategory.setCellValueFactory(new PropertyValueFactory<>("Category"));
        ProductTable.setItems(ProductData);
    }

    private void addProduct(){
        if (NameTextField.getText().equals("") && (CategoryComboBox.getValue() == null)) {
            showDialogErrorNullAll();
        }
        else {
            if (NameTextField.getText().equals("") || (CategoryComboBox.getValue() == null)) {
                showDialogErrorNull();
            }
            else {
                String Name = NameTextField.getText();
                String Category = CategoryComboBox.getValue().toString();

                try {

                    DBWarehouse.writeDBProduct(Name, Category);

                    NameTextField.clear();
                    CategoryComboBox.setValue(null);
                }
                catch (Exception e) {
                }
            }
        }
    }

    private void deleteProduct(){

        String Id = DeleteIdTextField.getText();

        if (Id.equals("") ) {
            showDialogErrorNullId();
        }
        else {
            try {
                int id = Integer.parseInt(DeleteIdTextField.getText());

                DBWarehouse.deleteBDProduct(id);

                DeleteIdTextField.clear();

            } catch (Exception e) {
                DeleteIdTextField.clear();
                showDialogErrorIncorrect();
            }
        }
    }

    public void getComboBoxCategory() {
        CategoryComboBox.getItems().addAll(CategoryData);
    }

    private void findProduct(){
        String Name = FindTextField.getText();

        if (Name.equals("") ) {
            showDialogErrorNullName();
        }
        else {
            try {
                DBWarehouse.selectProduct(Name);

                ProductData = DBWarehouse.getProductData();

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
    private void showDialogErrorNullName() {
        alertError.setTitle("Ошибка");
        alertError.setHeaderText(null);
        alertError.setContentText("Введите наименование товара");
        alertError.showAndWait();
    }
}
