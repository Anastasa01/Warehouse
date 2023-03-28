package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class Controller implements Initializable {

    @FXML
    private Button SupplierButton;

    @FXML
    private Button BuyerButton;

    @FXML
    private Button ProductButton;

    @FXML
    private Button ReceiptInvoiceButton;

    @FXML
    private Button ExpenditureInvoiceButton;

    @FXML
    private Button WarehouseButton;

    @FXML
    private Button ArchiveButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SupplierButton.setOnAction((event -> {
            Stage stage = (Stage) SupplierButton.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Supplier/Supplier.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Поставщики");
            stage.setResizable(false);
            stage.show();
        }));

        BuyerButton.setOnAction((event -> {
            Stage stage = (Stage) BuyerButton.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Buyer/Buyer.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Покупатели");
            stage.setResizable(false);
            stage.show();
        }));

        ProductButton.setOnAction((event -> {
            Stage stage = (Stage) ProductButton.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Product/Product.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Товар");
            stage.setResizable(false);
            stage.show();
        }));

        ReceiptInvoiceButton.setOnAction((event -> {
            Stage stage = (Stage) ReceiptInvoiceButton.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ReceiptInvoice/ReceiptInvoice.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Приходные накладные");
            stage.setResizable(false);
            stage.show();
        }));

        ExpenditureInvoiceButton.setOnAction((event -> {
            Stage stage = (Stage) ExpenditureInvoiceButton.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ExpenditureInvoice/ExpenditureInvoice.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Расходные накладные");
            stage.setResizable(false);
            stage.show();
        }));

        WarehouseButton.setOnAction((event -> {
            Stage stage = (Stage) WarehouseButton.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Warehouse/Warehouse.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Склад");
            stage.setResizable(false);
            stage.show();
        }));

        ArchiveButton.setOnAction((event -> {
            Stage stage = (Stage) ArchiveButton.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/Archive/Archive.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Архив");
            stage.setResizable(false);
            stage.show();
        }));
    }
}

