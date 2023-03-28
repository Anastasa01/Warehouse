package system;

import Invoice.Invoice;
import Objectt.Objectt;
import People.People;
import People.Supplier;
import People.Buyer;
import Objectt.Product;
import Invoice.ReceiptInvoice;
import Invoice.ExpenditureInvoice;
import Objectt.Warehouse;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.*;
import java.util.ArrayList;

public class DBWarehouse {

    public static ObservableList<People> SupplierData = FXCollections.observableArrayList(); //объявляем список
    public static ObservableList<People> BuyerData = FXCollections.observableArrayList();
    public static ObservableList<Objectt> ProductData = FXCollections.observableArrayList();
    public static ObservableList<Invoice> ReceiptInvoiceData = FXCollections.observableArrayList();
    public static ObservableList<Invoice> ExpenditureInvoiceData = FXCollections.observableArrayList();
    public static ObservableList<Objectt> WarehouseData = FXCollections.observableArrayList();
    public static ObservableList<Invoice> ArchiveReceiptInvoiceData = FXCollections.observableArrayList();
    public static ObservableList<Invoice> ArchiveExpenditureInvoiceData = FXCollections.observableArrayList();

    public static ArrayList<String> DataSupplier = new ArrayList<String>();
    public static ArrayList<String> DataBuyer = new ArrayList<String>();
    public static ArrayList<String> DataProduct = new ArrayList<String>();
    public static ArrayList<String> DataWarehouse = new ArrayList<String>();
    public static ArrayList<String> DataReceiptInvoice = new ArrayList<String>();

    public static ObservableList<People> getSupplierData() {return SupplierData;}
    public static ObservableList<People> getBuyerData() {return BuyerData;}
    public static ObservableList<Objectt> getProductData() {return ProductData;}
    public static ObservableList<Invoice> getReceiptInvoiceData() {return ReceiptInvoiceData;}
    public static ObservableList<Invoice> getExpenditureInvoiceData() {return ExpenditureInvoiceData;}
    public static ObservableList<Objectt> getWarehouseData() {return WarehouseData;}
    public static ObservableList<Invoice> getArchiveReceiptInvoiceData() {return ArchiveReceiptInvoiceData;}
    public static ObservableList<Invoice> getArchiveExpenditureInvoiceData() {return ArchiveExpenditureInvoiceData;}

    public static ArrayList<String> getDataSupplier() {return DataSupplier;}
    public static ArrayList<String> getDataBuyer() {return DataBuyer;}
    public static ArrayList<String> getDataProduct() {return DataProduct;}
    public static ArrayList<String> getDataWarehouse() {return DataWarehouse;}
    public static ArrayList<String> getDataReceiptInvoice() {return DataReceiptInvoice;}

    public static Alert alertError = new Alert(AlertType.ERROR);
    public static Alert alertInformation = new Alert(AlertType.INFORMATION);

    public static Connection warehouse; //для соединения с БД необходимо использовать класс Connection пакета java.sql.
    public static Statement stab; //используется для выполнения SQL-запросов
    public static ResultSet result;//представляет результирующий набор данных и обеспечивает приложению построчный доступ к результатам запросов

    //Соединение с БД
    public static void connectionBD() throws ClassNotFoundException, SQLException {
        warehouse = null;
        Class.forName("org.sqlite.JDBC");  //("имя движка") вызывает динамическую загрузку класса, org.sqlite.JDBC принадлежит к jar sqlite-jdbc
        warehouse = DriverManager.getConnection("jdbc:sqlite:warehouse.db"); //("протокол:движок:имя_файла_БД")находит java.sql.Driver соответствующей базы данных и вызывает у него метод connect (метод connect всегда создает базу данных заранее)
        System.out.println("Соединение открыто");
    }

    //Закрытие БД
    public static void сloseDB() throws SQLException
    {
        warehouse.close();
        stab.close();
        result.close();
        System.out.println("Соединения закрыты");
    }

    //Создание БД
    public static void newTable() throws SQLException{
        stab = warehouse.createStatement();//создание экземпляра класса Statement
        stab.execute("CREATE TABLE if not exists 'supplier' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'Name' text, 'Surname' text, 'Patronymic' text, 'Company' text, 'Address' text, 'Telephone' int);");// позволяет выполнять различные статичные SQL запросы, используется, когда операторы SQL возвращают более одного набора данных, более одного счетчика обновлений или и то, и другое
        stab.execute("CREATE TABLE if not exists 'buyer' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'Name' text, 'Surname' text, 'Patronymic' text, 'Passport' int, 'Address' text, 'Telephone' int);");
        stab.execute("CREATE TABLE if not exists 'product' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'Name' text, 'Category' text);");
        stab.execute("CREATE TABLE if not exists 'receiptInvoice' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'Supplier' text, 'Product' text, 'Price' int, 'Date' text);");
        stab.execute("CREATE TABLE if not exists 'expenditureInvoice' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'Buyer' text, 'Product' text, 'Price' int, 'Date' text);");
        stab.execute("CREATE TABLE if not exists 'warehouse' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'Name' text, 'Unit' text);");
        stab.execute("CREATE TABLE if not exists 'archiveReceiptInvoice' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'Supplier' text, 'Product' text, 'Price' int, 'Date' int);");
        stab.execute("CREATE TABLE if not exists 'archiveExpenditureInvoice' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'Buyer' text, 'Product' text, 'Price' int, 'Date' int);");
    }

    // Заполнение таблицы БД
    public static void writeDBSupplier(String Name, String Surname, String Patronymic, String Company, String Address, int Telephone) throws SQLException{
        SupplierData.clear();
        result = stab.executeQuery("SELECT * FROM supplier WHERE  Company = '"+Company+"'");
        //Запись данных из БД в List
        Supplier supplier1 = new Supplier(result.getInt("id"), result.getString("Name"),
                result.getString("Surname"), result.getString("Patronymic"),
                result.getString("Company"), result.getString("Address"),
                result.getInt("Telephone"));
        result = stab.executeQuery("SELECT * FROM supplier WHERE Company = '"+supplier1.getCompany()+"'");
        while(result.next()) {
            //выборки данных с помощью команды SELECT
            Supplier supplier = new Supplier(result.getInt("id"), result.getString("Name"),
                    result.getString("Surname"), result.getString("Patronymic"),
                    result.getString("Company"), result.getString("Address"),
                    result.getInt("Telephone"));
            //заполнение списка
            SupplierData.add(supplier);
        }
        if (SupplierData.stream().count() == 0){
            stab.execute("INSERT INTO 'supplier' ('Name','Surname','Patronymic','Company','Address','Telephone') VALUES ('"+Name+"', '"+Surname+"', '"+Patronymic+"', '"+Company+"','"+Address+"','"+Telephone+"'); ");
            showDialogInformationAddSupplier();
        }
        else {
            showDialogErrorAddDoubleSupplier();
        }
    }
    public static void writeDBBuyer(String Name, String Surname, String Patronymic, int Passport, String Address, int Telephone) throws SQLException{
        BuyerData.clear();
        result = stab.executeQuery("SELECT * FROM buyer WHERE  Passport = '"+Passport+"'");
        //Запись данных из БД в List
        Buyer buyer1 = new Buyer(result.getInt("id"), result.getString("Name"),
                result.getString("Surname"), result.getString("Patronymic"),
                result.getInt("Passport"), result.getString("Address"),
                result.getInt("Telephone"));
        result = stab.executeQuery("SELECT * FROM buyer WHERE Passport = '"+buyer1.getPassport()+"'");
        while(result.next()) {
            //выборки данных с помощью команды SELECT
            Buyer buyer = new Buyer(result.getInt("id"), result.getString("Name"),
                    result.getString("Surname"), result.getString("Patronymic"),
                    result.getInt("Passport"), result.getString("Address"),
                    result.getInt("Telephone"));
            //заполнение списка
            BuyerData.add(buyer);
        }
        if (BuyerData.stream().count() == 0){
            stab.execute("INSERT INTO 'buyer' ('Name','Surname','Patronymic','Passport','Address','Telephone') VALUES ('"+Name+"', '"+Surname+"', '"+Patronymic+"', '"+Passport+"','"+Address+"','"+Telephone+"'); ");
            showDialogInformationAddBuyer();
        }
        else {
            showDialogErrorAddDoubleBuyer();
        }
    }
    public static void writeDBProduct(String Name, String Category) throws SQLException{
        ProductData.clear();
        result = stab.executeQuery("SELECT * FROM product WHERE  Name = '"+Name+"'");
        //Запись данных из БД в List
        Product product1 = new Product(result.getInt("id"), result.getString("Name"),
                result.getString("Category"));
        result = stab.executeQuery("SELECT * FROM product WHERE Name = '"+product1.getName()+"'");
        while(result.next()) {
            //выборки данных с помощью команды SELECT
            Product product = new Product(result.getInt("id"), result.getString("Name"),
                    result.getString("Category"));
            //заполнение списка
            ProductData.add(product);
        }
        if (ProductData.stream().count() == 0){
            stab.execute("INSERT INTO 'product' ('Name','Category') VALUES ('"+Name+"', '"+Category+"'); ");
            showDialogInformationAddProduct();
        }
        else {
            showDialogErrorAddDoubleProduct();
        }
    }
    public static void writeDBReceiptInvoice(String Supplier, String Product, int Price, String Date) throws SQLException{

        ReceiptInvoiceData.clear();
        result = stab.executeQuery("SELECT * FROM receiptInvoice WHERE Product = '"+Product+"'");
        //Запись данных из БД в List
        ReceiptInvoice receiptInvoice1 = new ReceiptInvoice(result.getInt("id"), result.getString("Supplier"),
                result.getString("Product"), result.getInt("Price"), result.getString("Date"));
        result = stab.executeQuery("SELECT * FROM receiptInvoice WHERE Product = '"+receiptInvoice1.getProduct()+"'");
        while(result.next()) {
            //выборки данных с помощью команды SELECT
            ReceiptInvoice receiptInvoice = new ReceiptInvoice(result.getInt("id"), result.getString("Supplier"),
                    result.getString("Product"), result.getInt("Price"), result.getString("Date"));
            //заполнение списка
            ReceiptInvoiceData.add(receiptInvoice);
        }
        if (ReceiptInvoiceData.stream().count() == 0){
            stab.execute("INSERT INTO 'receiptInvoice' ('Supplier','Product','Price','Date') VALUES ('"+Supplier+"', '"+Product+"', '"+Price+"','"+Date+"'); ");
            stab.execute("INSERT INTO 'archiveReceiptInvoice' ('Supplier','Product','Price','Date') VALUES ('"+Supplier+"', '"+Product+"', '"+Price+"','"+Date+"'); ");
            showDialogInformationAddReceiptInvoice();
        }
        else {
            showDialogErrorAddDoubleReceiptInvoice();
        }
    }
    public static void writeDBExpenditureInvoice(String Buyer, String Product, int Price, String Date) throws SQLException{

        ExpenditureInvoiceData.clear();
        result = stab.executeQuery("SELECT * FROM expenditureInvoice WHERE Product = '"+Product+"'");
        //Запись данных из БД в List
        ExpenditureInvoice expenditureInvoice1 = new ExpenditureInvoice(result.getInt("id"), result.getString("Buyer"),
                result.getString("Product"), result.getInt("Price"), result.getString("Date"));
        result = stab.executeQuery("SELECT * FROM expenditureInvoice WHERE Product = '"+expenditureInvoice1.getProduct()+"'");
        while(result.next()) {
            //выборки данных с помощью команды SELECT
            ExpenditureInvoice expenditureInvoice = new ExpenditureInvoice(result.getInt("id"), result.getString("Buyer"),
                    result.getString("Product"), result.getInt("Price"), result.getString("Date"));
            //заполнение списка
            ExpenditureInvoiceData.add(expenditureInvoice);
        }
        if (ExpenditureInvoiceData.stream().count() == 0){
            stab.execute("INSERT INTO 'expenditureInvoice' ('Buyer','Product','Price','Date') VALUES ('"+Buyer+"', '"+Product+"', '"+Price+"','"+Date+"'); ");
            stab.execute("INSERT INTO 'archiveExpenditureInvoice' ('Buyer','Product','Price','Date') VALUES ('"+Buyer+"', '"+Product+"', '"+Price+"','"+Date+"'); ");
            showDialogInformationAddExpenditureInvoice();
        }
        else {
            showDialogErrorAddDoubleExpenditureInvoice();
        }
    }
    public static void writeDBWarehouse(String Name, String Unit) throws SQLException{

        WarehouseData.clear();
        result = stab.executeQuery("SELECT * FROM warehouse WHERE Name = '"+Name+"'");
        //Запись данных из БД в List
        Warehouse warehouse1 = new Warehouse(result.getInt("id"), result.getString("Name"),
                result.getString("Unit"));
        result = stab.executeQuery("SELECT * FROM warehouse WHERE Name = '"+warehouse1.getName()+"'");
        while(result.next()) {
            //выборки данных с помощью команды SELECT
            Warehouse warehouse = new Warehouse(result.getInt("id"), result.getString("Name"),
                    result.getString("Unit"));
            //заполнение списка
            WarehouseData.add(warehouse);
        }
        if (WarehouseData.stream().count() == 0){
            stab.execute("INSERT INTO 'warehouse' ('Name', 'Unit') VALUES ('"+Name+"', '"+Unit+"'); ");
            showDialogInformationAddWarehouse();
        }
        else {
            showDialogErrorAddDoubleWarehouse();
        }
    }

    //Чтение БД
    public static void readDBSupplier() throws SQLException {
        // Очистка списка
        SupplierData.clear();
        //выборки данных с помощью команды SELECT
        result = stab.executeQuery("SELECT * FROM supplier");
        //Запись данных из БД в List
        while(result.next()) {
            //выборки данных с помощью команды SELECT
            Supplier supplier = new Supplier(result.getInt("id"), result.getString("Name"),
                    result.getString("Surname"), result.getString("Patronymic"),
                    result.getString("Company"), result.getString("Address"),
                    result.getInt("Telephone"));
            //заполнение списка
            SupplierData.add(supplier);
        }
    }
    public static void readDBBuyer() throws SQLException {
        // Очистка списка
        BuyerData.clear();
        //выборки данных с помощью команды SELECT
        result = stab.executeQuery("SELECT * FROM buyer");
        //Запись данных из БД в List
        while(result.next()) {
            //выборки данных с помощью команды SELECT
            Buyer buyer = new Buyer(result.getInt("id"), result.getString("Name"),
                    result.getString("Surname"), result.getString("Patronymic"),
                    result.getInt("Passport"), result.getString("Address"),
                    result.getInt("Telephone"));
            //заполнение списка
            BuyerData.add(buyer);
        }
    }
    public static void readDBProduct() throws SQLException {
        // Очистка списка
        ProductData.clear();
        //выборки данных с помощью команды SELECT
        result = stab.executeQuery("SELECT * FROM product");
        //Запись данных из БД в List
        while(result.next()) {
            //выборки данных с помощью команды SELECT
            Product product = new Product(result.getInt("id"), result.getString("Name"),
                    result.getString("Category"));
            //заполнение списка
            ProductData.add(product);
        }
    }
    public static void readDBReceiptInvoice() throws SQLException {
        // Очистка списка
        ReceiptInvoiceData.clear();
        //выборки данных с помощью команды SELECT
        result = stab.executeQuery("SELECT * FROM receiptInvoice");
        //Запись данных из БД в List
        while(result.next()) {
            //выборки данных с помощью команды SELECT
            ReceiptInvoice receiptInvoice = new ReceiptInvoice(result.getInt("id"), result.getString("Supplier"),
                    result.getString("Product"), result.getInt("Price"), result.getString("Date"));
            //заполнение списка
            ReceiptInvoiceData.add(receiptInvoice);
        }
    }
    public static void readDBExpenditureInvoice() throws SQLException {
        // Очистка списка
        ExpenditureInvoiceData.clear();
        //выборки данных с помощью команды SELECT
        result = stab.executeQuery("SELECT * FROM expenditureInvoice");
        //Запись данных из БД в List
        while(result.next()) {
            //выборки данных с помощью команды SELECT
            ExpenditureInvoice expenditureInvoice = new ExpenditureInvoice(result.getInt("id"), result.getString("Buyer"),
                    result.getString("Product"), result.getInt("Price"), result.getString("Date"));
            //заполнение списка
            ExpenditureInvoiceData.add(expenditureInvoice);
        }
    }
    public static void readDBWarehouse() throws SQLException {
        // Очистка списка
        WarehouseData.clear();
        //выборки данных с помощью команды SELECT
        result = stab.executeQuery("SELECT * FROM warehouse");
        //Запись данных из БД в List
        while(result.next()) {
            //выборки данных с помощью команды SELECT
            Warehouse warehouse = new Warehouse(result.getInt("id"), result.getString("Name"),
                    result.getString("Unit"));
            //заполнение списка
            WarehouseData.add(warehouse);
        }
    }
    public static void readDBArchiveReceiptInvoice() throws SQLException {
        // Очистка списка
        ArchiveReceiptInvoiceData.clear();
        //выборки данных с помощью команды SELECT
        result = stab.executeQuery("SELECT * FROM archiveReceiptInvoice");
        //Запись данных из БД в List
        while(result.next()) {
            //выборки данных с помощью команды SELECT
            ReceiptInvoice receiptInvoice = new ReceiptInvoice(result.getInt("id"), result.getString("Supplier"),
                    result.getString("Product"), result.getInt("Price"), result.getString("Date"));
            //заполнение списка
            ArchiveReceiptInvoiceData.add(receiptInvoice);
        }
    }
    public static void readDBArchiveExpenditureInvoice() throws SQLException {
        // Очистка списка
        ArchiveExpenditureInvoiceData.clear();
        //выборки данных с помощью команды SELECT
        result = stab.executeQuery("SELECT * FROM archiveExpenditureInvoice");
        //Запись данных из БД в List
        while(result.next()) {
            //выборки данных с помощью команды SELECT
            ExpenditureInvoice expenditureInvoice = new ExpenditureInvoice(result.getInt("id"), result.getString("Buyer"),
                    result.getString("Product"), result.getInt("Price"), result.getString("Date"));
            //заполнение списка
            ArchiveExpenditureInvoiceData.add(expenditureInvoice);
        }
    }

    //Вывод данных в ComboBox
    public static void selectComboBoxSupplier() throws SQLException {
        // Очистка списка
        DataSupplier.clear();
        //выборки данных с помощью команды SELECT
        result = stab.executeQuery("SELECT Company FROM 'supplier'");
        //Запись данных из БД в List
        while(result.next()) {
            //создание экемпляра с заданным из таблицы параметрам
            String supplier = (
                    result.getString("Company"));
            //заполнение списка
            DataSupplier.add(supplier);
        }
    }

    public static void selectComboBoxBuyer() throws SQLException {
        // Очистка списка
        DataBuyer.clear();
        //выборки данных с помощью команды SELECT
        result = stab.executeQuery("SELECT Passport FROM 'buyer'");
        //Запись данных из БД в List
        while(result.next()) {
            //создание экемпляра с заданным из таблицы параметрам
            String buyer = (
                    result.getString("Passport"));
            //заполнение списка
            DataBuyer.add(buyer);
        }
    }
    public static void selectComboBoxProduct() throws SQLException {
        // Очистка списка
        DataProduct.clear();
        //выборки данных с помощью команды SELECT
        result = stab.executeQuery("SELECT Name FROM 'product'");
        //Запись данных из БД в List
        while(result.next()) {
            //создание экемпляра с заданным из таблицы параметрам
            String product = (
                    result.getString("Name"));
            //заполнение списка
            DataProduct.add(product);
        }
    }
    public static void selectComboBoxWarehouse() throws SQLException {
        // Очистка списка
        DataWarehouse.clear();
        //выборки данных с помощью команды SELECT
        result = stab.executeQuery("SELECT Name FROM 'warehouse'");
        //Запись данных из БД в List
        while(result.next()) {
            //создание экемпляра с заданным из таблицы параметрам
            String warehouse = (
                    result.getString("Name"));
            //заполнение списка
            DataWarehouse.add(warehouse);
        }
    }
    public static void selectComboBoxReceiptInvoice() throws SQLException {
        // Очистка списка
        DataReceiptInvoice.clear();
        //выборки данных с помощью команды SELECT
        result = stab.executeQuery("SELECT Product FROM 'receiptInvoice'");
        //Запись данных из БД в List
        while(result.next()) {
            //создание экемпляра с заданным из таблицы параметрам
            String receiptInvoice = (
                    result.getString("Product"));
            //заполнение списка
            DataReceiptInvoice.add(receiptInvoice);
        }
    }

    //Удаление БД
    public static void deleteBDSupplier(int id) throws SQLException {
        SupplierData.clear();
        result = stab.executeQuery("SELECT * FROM supplier WHERE  id = '"+id+"'");
        //Запись данных из БД в List
        Supplier supplier1 = new Supplier(result.getInt("id"), result.getString("Name"),
                result.getString("Surname"), result.getString("Patronymic"),
                result.getString("Company"), result.getString("Address"),
                result.getInt("Telephone"));
        result = stab.executeQuery("SELECT * FROM supplier WHERE id = '"+supplier1.getId()+"'");
        while(result.next()) {
            //выборки данных с помощью команды SELECT
            Supplier supplier = new Supplier(result.getInt("id"), result.getString("Name"),
                    result.getString("Surname"), result.getString("Patronymic"),
                    result.getString("Company"), result.getString("Address"),
                    result.getInt("Telephone"));
            //заполнение списка
            SupplierData.add(supplier);
        }
        if (SupplierData.stream().count() == 0){
            showDialogErrorNullSupplier();
        }
        else {
            ReceiptInvoiceData.clear();
            result = stab.executeQuery("SELECT * FROM supplier WHERE  id = '"+id+"'");
            //Запись данных из БД в List
            Supplier supplier = new Supplier(result.getInt("id"), result.getString("Name"),
                    result.getString("Surname"), result.getString("Patronymic"),
                    result.getString("Company"), result.getString("Address"),
                    result.getInt("Telephone"));
            result = stab.executeQuery("SELECT * FROM receiptInvoice WHERE Supplier = '"+supplier.getCompany()+"'");
            while(result.next()) {
                //выборки данных с помощью команды SELECT
                ReceiptInvoice receiptInvoice = new ReceiptInvoice(result.getInt("id"), result.getString("Supplier"),
                        result.getString("Product"), result.getInt("Price"), result.getString("Date"));
                //заполнение списка
                ReceiptInvoiceData.add(receiptInvoice);
            }
            if (ReceiptInvoiceData.stream().count() == 0){
                stab.executeUpdate("DELETE FROM supplier WHERE  id = '"+id+"'");
                showDialogInformationDeleteSupplier();
            }
            else {
                showDialogErrorNotNullSupplier();
            }
        }
    }
    public static void deleteBDBuyer(int id) throws SQLException {
        BuyerData.clear();
        result = stab.executeQuery("SELECT * FROM buyer WHERE  id = '"+id+"'");
        //Запись данных из БД в List
        Buyer buyer1 = new Buyer(result.getInt("id"), result.getString("Name"),
                result.getString("Surname"), result.getString("Patronymic"),
                result.getInt("Passport"), result.getString("Address"),
                result.getInt("Telephone"));
        result = stab.executeQuery("SELECT * FROM buyer WHERE id = '"+buyer1.getId()+"'");
        while(result.next()) {
            //выборки данных с помощью команды SELECT
            Buyer buyer = new Buyer(result.getInt("id"), result.getString("Name"),
                    result.getString("Surname"), result.getString("Patronymic"),
                    result.getInt("Passport"), result.getString("Address"),
                    result.getInt("Telephone"));
            //заполнение списка
            BuyerData.add(buyer);
        }
        if (BuyerData.stream().count() == 0){
            showDialogErrorNullBuyer();
        }
        else {
            ExpenditureInvoiceData.clear();
            result = stab.executeQuery("SELECT * FROM buyer WHERE  id = '"+id+"'");
            //Запись данных из БД в List
            Buyer buyer = new Buyer(result.getInt("id"), result.getString("Name"),
                    result.getString("Surname"), result.getString("Patronymic"),
                    result.getInt("Passport"), result.getString("Address"),
                    result.getInt("Telephone"));
            result = stab.executeQuery("SELECT * FROM expenditureInvoice WHERE Buyer = '"+buyer.getPassport()+"'");
            while(result.next()) {
                //выборки данных с помощью команды SELECT
                ExpenditureInvoice expenditureInvoice = new ExpenditureInvoice(result.getInt("id"), result.getString("Buyer"),
                        result.getString("Product"), result.getInt("Price"), result.getString("Date"));
                //заполнение списка
                ExpenditureInvoiceData.add(expenditureInvoice);
            }
            if (ExpenditureInvoiceData.stream().count() == 0){
                stab.executeUpdate("DELETE FROM buyer WHERE  id = '"+id+"'");
                showDialogInformationDeleteBuyer();
            }
            else {
                showDialogErrorNotNullBuyer();
            }
        }
    }
    public static void deleteBDProduct(int id) throws SQLException {
        ProductData.clear();
        result = stab.executeQuery("SELECT * FROM product WHERE  id = '"+id+"'");
        //Запись данных из БД в List
        Product product1 = new Product(result.getInt("id"), result.getString("Name"),
                result.getString("Category"));
        result = stab.executeQuery("SELECT * FROM product WHERE id = '"+product1.getId()+"'");
        while(result.next()) {
            //выборки данных с помощью команды SELECT
            Product product = new Product(result.getInt("id"), result.getString("Name"),
                    result.getString("Category"));
            //заполнение списка
            ProductData.add(product);
        }
        if (ProductData.stream().count() == 0){
            showDialogErrorNullProduct();
        }
        else {
            WarehouseData.clear();
            result = stab.executeQuery("SELECT * FROM product WHERE  id = '"+id+"'");
            //Запись данных из БД в List
            Product product = new Product(result.getInt("id"), result.getString("Name"),
                    result.getString("Category"));
            result = stab.executeQuery("SELECT * FROM warehouse WHERE Name = '"+product.getName()+"'");
            while(result.next()) {
                //выборки данных с помощью команды SELECT
                Warehouse warehouse = new Warehouse(result.getInt("id"), result.getString("Name"),
                        result.getString("Unit"));
                //заполнение списка
                WarehouseData.add(warehouse);
            }
            if (WarehouseData.stream().count() == 0){
                stab.executeUpdate("DELETE FROM product WHERE  id = '"+id+"'");
                showDialogInformationDeleteProduct();
            }
            else {
                showDialogErrorNotNullProduct();
            }
        }
    }
    public static void deleteBDWarehouse(int id) throws SQLException {
        WarehouseData.clear();
        result = stab.executeQuery("SELECT * FROM warehouse WHERE  id = '"+id+"'");
        //Запись данных из БД в List
        Warehouse warehouse1 = new Warehouse(result.getInt("id"), result.getString("Name"),
                result.getString("Unit"));
        result = stab.executeQuery("SELECT * FROM warehouse WHERE id = '"+warehouse1.getId()+"'");
        while(result.next()) {
            //выборки данных с помощью команды SELECT
            Warehouse warehouse = new Warehouse(result.getInt("id"), result.getString("Name"),
                    result.getString("Unit"));
            //заполнение списка
            WarehouseData.add(warehouse);
        }
        if (WarehouseData.stream().count() == 0){
            showDialogErrorNullWarehouse();
        }
        else {
            ReceiptInvoiceData.clear();
            result = stab.executeQuery("SELECT * FROM warehouse WHERE  id = '"+id+"'");
            //Запись данных из БД в List
            Warehouse warehouse = new Warehouse(result.getInt("id"), result.getString("Name"),
                    result.getString("Unit"));
            result = stab.executeQuery("SELECT * FROM receiptInvoice WHERE Product = '"+warehouse.getName()+"'");
            while(result.next()) {
                //выборки данных с помощью команды SELECT
                ReceiptInvoice receiptInvoice = new ReceiptInvoice(result.getInt("id"), result.getString("Supplier"),
                        result.getString("Product"), result.getInt("Price"), result.getString("Date"));
                //заполнение списка
                ReceiptInvoiceData.add(receiptInvoice);
            }
            if (ReceiptInvoiceData.stream().count() == 0){
                stab.executeUpdate("DELETE FROM warehouse WHERE  id = '"+id+"'");
                showDialogInformationDeleteWarehouse1();
            }
            else {
                showDialogErrorNotNullWarehouse();
            }
        }
    }

    public static void archiveBDExpenditureInvoice(int id) throws SQLException {
        ExpenditureInvoiceData.clear();
        result = stab.executeQuery("SELECT * FROM expenditureInvoice WHERE  id = '"+id+"'");
        //Запись данных из БД в List
        ExpenditureInvoice expenditureInvoice1 = new ExpenditureInvoice(result.getInt("id"), result.getString("Buyer"),
                result.getString("Product"), result.getInt("Price"), result.getString("Date"));
        result = stab.executeQuery("SELECT * FROM expenditureInvoice WHERE id = '"+expenditureInvoice1.getId()+"'");
        while(result.next()) {
            //выборки данных с помощью команды SELECT
            ExpenditureInvoice expenditureInvoice = new ExpenditureInvoice(result.getInt("id"), result.getString("Buyer"),
                    result.getString("Product"), result.getInt("Price"), result.getString("Date"));
            //заполнение списка
            ExpenditureInvoiceData.add(expenditureInvoice);
        }
        if (ExpenditureInvoiceData.stream().count() == 0){
            showDialogErrorNullExpenditureInvoice();
        }
        else {
            ReceiptInvoiceData.clear();
            result = stab.executeQuery("SELECT * FROM expenditureInvoice WHERE  id = '"+id+"'");
            //Запись данных из БД в List
            ExpenditureInvoice expenditureInvoice = new ExpenditureInvoice(result.getInt("id"), result.getString("Buyer"),
                    result.getString("Product"), result.getInt("Price"), result.getString("Date"));
            result = stab.executeQuery("SELECT * FROM receiptInvoice WHERE Product = '"+expenditureInvoice.getProduct()+"'");
            while(result.next()) {
                //выборки данных с помощью команды SELECT
                ReceiptInvoice receiptInvoice = new ReceiptInvoice(result.getInt("id"), result.getString("Supplier"),
                        result.getString("Product"), result.getInt("Price"), result.getString("Date"));
                //заполнение списка
                ReceiptInvoiceData.add(receiptInvoice);
            }
            WarehouseData.clear();
            result = stab.executeQuery("SELECT * FROM warehouse WHERE Name = '"+expenditureInvoice.getProduct()+"'");
            while(result.next()) {
                //выборки данных с помощью команды SELECT
                Warehouse warehouse = new Warehouse(result.getInt("id"), result.getString("Name"),
                        result.getString("Unit"));
                //заполнение списка
                WarehouseData.add(warehouse);
            }
            if ((ReceiptInvoiceData.stream().count() == 0) && (WarehouseData.stream().count() == 0)){
            }
            else {
                stab.executeUpdate("DELETE FROM expenditureInvoice WHERE  id = '"+id+"'");
                for (int i=0;i<ReceiptInvoiceData.stream().count();i++) {
                    stab.executeUpdate("DELETE FROM receiptInvoice WHERE  id = '"+ReceiptInvoiceData.get(i).getId()+"'");
                }
                for (int i=0;i<WarehouseData.stream().count();i++) {
                    stab.executeUpdate("DELETE FROM warehouse WHERE  id = '"+WarehouseData.get(i).getId()+"'");
                }
                showDialogInformationDeleteWarehouse2();
            }
        }
    }
    public static void deleteBDArchiveReceiptInvoice(int id) throws SQLException {
        ArchiveReceiptInvoiceData.clear();
        result = stab.executeQuery("SELECT * FROM archiveReceiptInvoice WHERE  id = '"+id+"'");
        //Запись данных из БД в List
        ReceiptInvoice receiptInvoice1 = new ReceiptInvoice(result.getInt("id"), result.getString("Supplier"),
                result.getString("Product"), result.getInt("Price"), result.getString("Date"));
        result = stab.executeQuery("SELECT * FROM archiveReceiptInvoice WHERE id = '"+receiptInvoice1.getId()+"'");
        while(result.next()) {
            //выборки данных с помощью команды SELECT
            ReceiptInvoice receiptInvoice = new ReceiptInvoice(result.getInt("id"), result.getString("Supplier"),
                    result.getString("Product"), result.getInt("Price"), result.getString("Date"));
            //заполнение списка
            ArchiveReceiptInvoiceData.add(receiptInvoice);
        }
        if (ArchiveReceiptInvoiceData.stream().count() == 0){
            showDialogErrorNullReceiptInvoice();
        }
        else {
            stab.executeUpdate("DELETE FROM archiveReceiptInvoice WHERE  id = '"+id+"'");
            showDialogInformationDeleteReceiptInvoice();
        }
    }
    public static void deleteBDArchiveExpenditureInvoice(int id) throws SQLException {
        ArchiveExpenditureInvoiceData.clear();
        result = stab.executeQuery("SELECT * FROM archiveExpenditureInvoice WHERE  id = '"+id+"'");
        //Запись данных из БД в List
        ExpenditureInvoice expenditureInvoice1 = new ExpenditureInvoice(result.getInt("id"), result.getString("Buyer"),
                result.getString("Product"), result.getInt("Price"), result.getString("Date"));
        result = stab.executeQuery("SELECT * FROM archiveExpenditureInvoice WHERE id = '"+expenditureInvoice1.getId()+"'");
        while(result.next()) {
            //выборки данных с помощью команды SELECT
            ExpenditureInvoice expenditureInvoice = new ExpenditureInvoice(result.getInt("id"), result.getString("Buyer"),
                    result.getString("Product"), result.getInt("Price"), result.getString("Date"));
            //заполнение списка
            ArchiveExpenditureInvoiceData.add(expenditureInvoice);
        }
        if (ArchiveExpenditureInvoiceData.stream().count() == 0){
            showDialogErrorNullExpenditureInvoice();
        }
        else {
            stab.executeUpdate("DELETE FROM archiveExpenditureInvoice WHERE  id = '"+id+"'");
            showDialogInformationDeleteExpenditureInvoice ();
        }
    }

    //Сортировка RadioButton
    public static void selectArchiveReceiptInvoicePriceMore() throws SQLException {
        // Очистка списка
        ArchiveReceiptInvoiceData.clear();
        //выборки данных с помощью команды SELECT
        result = stab.executeQuery("SELECT * FROM 'archiveReceiptInvoice' ORDER BY Price DESC");
        //Запись данных из БД в List
        while(result.next()) {
            //создание экемпляра с заданным из таблицы параметрам
            ReceiptInvoice receiptInvoice = new ReceiptInvoice(result.getInt("id"), result.getString("Supplier"),
                    result.getString("Product"), result.getInt("Price"), result.getString("Date"));
            //заполнение списка
            ArchiveReceiptInvoiceData.add(receiptInvoice);
        }
    }
    public static void selectArchiveReceiptInvoicePriceLess() throws SQLException {
        // Очистка списка
        ArchiveReceiptInvoiceData.clear();
        //выборки данных с помощью команды SELECT
        result = stab.executeQuery("SELECT * FROM 'archiveReceiptInvoice' ORDER BY Price");
        //Запись данных из БД в List
        while(result.next()) {
            //создание экемпляра с заданным из таблицы параметрам
            ReceiptInvoice receiptInvoice = new ReceiptInvoice(result.getInt("id"), result.getString("Supplier"),
                    result.getString("Product"), result.getInt("Price"), result.getString("Date"));
            //заполнение списка
            ArchiveReceiptInvoiceData.add(receiptInvoice);
        }
    }
    public static void selectArchiveReceiptInvoiceDateMore() throws SQLException {
        // Очистка списка
        ArchiveReceiptInvoiceData.clear();
        //выборки данных с помощью команды SELECT
        result = stab.executeQuery("SELECT * FROM 'archiveReceiptInvoice' ORDER BY Date DESC");
        //Запись данных из БД в List
        while(result.next()) {
            //создание экемпляра с заданным из таблицы параметрам
            ReceiptInvoice receiptInvoice = new ReceiptInvoice(result.getInt("id"), result.getString("Supplier"),
                    result.getString("Product"), result.getInt("Price"), result.getString("Date"));
            //заполнение списка
            ArchiveReceiptInvoiceData.add(receiptInvoice);
        }
    }
    public static void selectArchiveReceiptInvoiceDateLess() throws SQLException {
        // Очистка списка
        ArchiveReceiptInvoiceData.clear();
        //выборки данных с помощью команды SELECT
        result = stab.executeQuery("SELECT * FROM 'archiveReceiptInvoice' ORDER BY Date");
        //Запись данных из БД в List
        while(result.next()) {
            //создание экемпляра с заданным из таблицы параметрам
            ReceiptInvoice receiptInvoice = new ReceiptInvoice(result.getInt("id"), result.getString("Supplier"),
                    result.getString("Product"), result.getInt("Price"), result.getString("Date"));
            //заполнение списка
            ArchiveReceiptInvoiceData.add(receiptInvoice);
        }
    }
    public static void selectArchiveExpenditureInvoicePriceMore() throws SQLException {
        // Очистка списка
        ArchiveExpenditureInvoiceData.clear();
        //выборки данных с помощью команды SELECT
        result = stab.executeQuery("SELECT * FROM 'archiveExpenditureInvoice' ORDER BY Price DESC");
        //Запись данных из БД в List
        while(result.next()) {
            //создание экемпляра с заданным из таблицы параметрам
            ExpenditureInvoice expenditureInvoice = new ExpenditureInvoice(result.getInt("id"), result.getString("Buyer"),
                    result.getString("Product"), result.getInt("Price"), result.getString("Date"));
            //заполнение списка
            ArchiveExpenditureInvoiceData.add(expenditureInvoice);
        }
    }
    public static void selectArchiveExpenditureInvoicePriceLess() throws SQLException {
        // Очистка списка
        ArchiveExpenditureInvoiceData.clear();
        //выборки данных с помощью команды SELECT
        result = stab.executeQuery("SELECT * FROM 'archiveExpenditureInvoice' ORDER BY Price");
        //Запись данных из БД в List
        while(result.next()) {
            //создание экемпляра с заданным из таблицы параметрам
            ExpenditureInvoice expenditureInvoice = new ExpenditureInvoice(result.getInt("id"), result.getString("Buyer"),
                    result.getString("Product"), result.getInt("Price"), result.getString("Date"));
            //заполнение списка
            ArchiveExpenditureInvoiceData.add(expenditureInvoice);
        }
    }
    public static void selectArchiveExpenditureInvoiceDateMore() throws SQLException {
        // Очистка списка
        ArchiveExpenditureInvoiceData.clear();
        //выборки данных с помощью команды SELECT
        result = stab.executeQuery("SELECT * FROM 'archiveExpenditureInvoice' ORDER BY Date DESC");
        //Запись данных из БД в List
        while(result.next()) {
            //создание экемпляра с заданным из таблицы параметрам
            ExpenditureInvoice expenditureInvoice = new ExpenditureInvoice(result.getInt("id"), result.getString("Buyer"),
                    result.getString("Product"), result.getInt("Price"), result.getString("Date"));
            //заполнение списка
            ArchiveExpenditureInvoiceData.add(expenditureInvoice);
        }
    }
    public static void selectArchiveExpenditureInvoiceDateLess() throws SQLException {
        // Очистка списка
        ArchiveExpenditureInvoiceData.clear();
        //выборки данных с помощью команды SELECT
        result = stab.executeQuery("SELECT * FROM 'archiveExpenditureInvoice' ORDER BY Date");
        //Запись данных из БД в List
        while(result.next()) {
            //создание экемпляра с заданным из таблицы параметрам
            ExpenditureInvoice expenditureInvoice = new ExpenditureInvoice(result.getInt("id"), result.getString("Buyer"),
                    result.getString("Product"), result.getInt("Price"), result.getString("Date"));
            //заполнение списка
            ArchiveExpenditureInvoiceData.add(expenditureInvoice);
        }
    }

    //Поиск
    public static void selectSupplier(String Company) throws SQLException {
        // Очистка списка
        SupplierData.clear();
        //выборки данных с помощью команды SELECT
        result = stab.executeQuery("SELECT * FROM 'supplier' WHERE Company = '"+Company+"'");
        //Запись данных из БД в List
        while(result.next()) {
            //создание экемпляра с заданным из таблицы параметрам
            Supplier supplier = new Supplier(result.getInt("id"), result.getString("Name"),
                    result.getString("Surname"), result.getString("Patronymic"),
                    result.getString("Company"), result.getString("Address"),
                    result.getInt("Telephone"));
            //заполнение списка
            SupplierData.add(supplier);
        }
    }
    public static void selectBuyer(int Passport) throws SQLException {
        // Очистка списка
        BuyerData.clear();
        //выборки данных с помощью команды SELECT
        result = stab.executeQuery("SELECT * FROM 'buyer' WHERE Passport = '"+Passport+"'");
        //Запись данных из БД в List
        while(result.next()) {
            //создание экемпляра с заданным из таблицы параметрам
            Buyer buyer = new Buyer(result.getInt("id"), result.getString("Name"),
                    result.getString("Surname"), result.getString("Patronymic"),
                    result.getInt("Passport"), result.getString("Address"),
                    result.getInt("Telephone"));
            //заполнение списка
            BuyerData.add(buyer);
        }
    }
    public static void selectProduct(String Name) throws SQLException {
        // Очистка списка
        ProductData.clear();
        //выборки данных с помощью команды SELECT
        result = stab.executeQuery("SELECT * FROM 'product' WHERE Name = '"+Name+"'");
        //Запись данных из БД в List
        while(result.next()) {
            //создание экемпляра с заданным из таблицы параметрам
            Product product = new Product(result.getInt("id"), result.getString("Name"),
                    result.getString("Category"));
            //заполнение списка
            ProductData.add(product);
        }
    }
    //Окна с сообщениями и ошибками
    public static void showDialogInformationAddSupplier(){
        alertInformation.setTitle("Информация");
        alertInformation.setHeaderText(null);
        alertInformation.setContentText("Поставщик добавлен");
        alertInformation.showAndWait();
    }
    public static void showDialogErrorAddDoubleSupplier(){
        alertError.setTitle("Ошибка");
        alertError.setHeaderText(null);
        alertError.setContentText("Поставщик с такой компанией уже существует");
        alertError.showAndWait();
    }
    public static void showDialogErrorNullSupplier(){
        alertError.setTitle("Ошибка");
        alertError.setHeaderText(null);
        alertError.setContentText("Такого поставщика не существует");
        alertError.showAndWait();
    }
    public static void showDialogInformationDeleteSupplier(){
        alertInformation.setTitle("Информация");
        alertInformation.setHeaderText(null);
        alertInformation.setContentText("Поставщик удален");
        alertInformation.showAndWait();
    }
    public static void showDialogErrorNotNullSupplier(){
        alertError.setTitle("Ошибка");
        alertError.setHeaderText(null);
        alertError.setContentText("Данный поставщик прикреплен к приходной накладной");
        alertError.showAndWait();
    }
    public static void showDialogInformationAddBuyer(){
        alertInformation.setTitle("Информация");
        alertInformation.setHeaderText(null);
        alertInformation.setContentText("Покупатель добавлен");
        alertInformation.showAndWait();
    }
    public static void showDialogErrorAddDoubleBuyer(){
        alertError.setTitle("Ошибка");
        alertError.setHeaderText(null);
        alertError.setContentText("Покупатель с таким паспортом уже существует");
        alertError.showAndWait();
    }
    public static void showDialogErrorNullBuyer(){
        alertError.setTitle("Ошибка");
        alertError.setHeaderText(null);
        alertError.setContentText("Такого покупателя не существует");
        alertError.showAndWait();
    }
    public static void showDialogInformationDeleteBuyer(){
        alertInformation.setTitle("Информация");
        alertInformation.setHeaderText(null);
        alertInformation.setContentText("Покупатель удален");
        alertInformation.showAndWait();
    }
    public static void showDialogErrorNotNullBuyer(){
        alertError.setTitle("Ошибка");
        alertError.setHeaderText(null);
        alertError.setContentText("Данный покупатель прикреплен к расходной накладной");
        alertError.showAndWait();
    }
    public static void showDialogInformationAddProduct(){
        alertInformation.setTitle("Информация");
        alertInformation.setHeaderText(null);
        alertInformation.setContentText("Товар добавлен");
        alertInformation.showAndWait();
    }
    public static void showDialogErrorAddDoubleProduct(){
        alertError.setTitle("Ошибка");
        alertError.setHeaderText(null);
        alertError.setContentText("Товар с таким наименованием уже существует");
        alertError.showAndWait();
    }
    public static void showDialogInformationDeleteProduct(){
        alertInformation.setTitle("Информация");
        alertInformation.setHeaderText(null);
        alertInformation.setContentText("Товар удален");
        alertInformation.showAndWait();
    }
    public static void showDialogErrorNullProduct(){
        alertError.setTitle("Ошибка");
        alertError.setHeaderText(null);
        alertError.setContentText("Такого товара не существует");
        alertError.showAndWait();
    }
    public static void showDialogErrorNotNullProduct(){
        alertError.setTitle("Ошибка");
        alertError.setHeaderText(null);
        alertError.setContentText("Данный товар лежит на складе");
        alertError.showAndWait();
    }
    public static void showDialogInformationAddWarehouse(){
        alertInformation.setTitle("Информация");
        alertInformation.setHeaderText(null);
        alertInformation.setContentText("Товар добавлен на склад");
        alertInformation.showAndWait();
    }
    public static void showDialogErrorAddDoubleWarehouse(){
        alertError.setTitle("Ошибка");
        alertError.setHeaderText(null);
        alertError.setContentText("Данный товар уже лежит на складе");
        alertError.showAndWait();
    }
    public static void showDialogInformationDeleteWarehouse1(){
        alertInformation.setTitle("Информация");
        alertInformation.setHeaderText(null);
        alertInformation.setContentText("Товар убран со склада");
        alertInformation.showAndWait();
    }
    public static void showDialogErrorNullWarehouse(){
        alertError.setTitle("Ошибка");
        alertError.setHeaderText(null);
        alertError.setContentText("Такой товара не лежит на складе");
        alertError.showAndWait();
    }
    public static void showDialogErrorNotNullWarehouse(){
        alertError.setTitle("Ошибка");
        alertError.setHeaderText(null);
        alertError.setContentText("Данный товар прикреплен к приходной накладной");
        alertError.showAndWait();
    }
    public static void showDialogInformationAddReceiptInvoice(){
        alertInformation.setTitle("Информация");
        alertInformation.setHeaderText(null);
        alertInformation.setContentText("Приходная накладаная составлена. В Архиве добавлена запись");
        alertInformation.showAndWait();
    }
    public static void showDialogErrorAddDoubleReceiptInvoice(){
        alertError.setTitle("Ошибка");
        alertError.setHeaderText(null);
        alertError.setContentText("Данный товар уже прикреплен к приходной накладной");
        alertError.showAndWait();
    }
    public static void showDialogErrorNullReceiptInvoice(){
        alertError.setTitle("Ошибка");
        alertError.setHeaderText(null);
        alertError.setContentText("Такой приходной накладной не существует");
        alertError.showAndWait();
    }
    public static void showDialogInformationDeleteReceiptInvoice(){
        alertInformation.setTitle("Информация");
        alertInformation.setHeaderText(null);
        alertInformation.setContentText("Приходная накладная удалена");
        alertInformation.showAndWait();
    }
    public static void showDialogInformationAddExpenditureInvoice(){
        alertInformation.setTitle("Информация");
        alertInformation.setHeaderText(null);
        alertInformation.setContentText("Расходная накладаная составлена. В Архиве добавлена запись");
        alertInformation.showAndWait();
    }
    public static void showDialogErrorAddDoubleExpenditureInvoice(){
        alertError.setTitle("Ошибка");
        alertError.setHeaderText(null);
        alertError.setContentText("Данный товар уже прикреплен к расходной накладной");
        alertError.showAndWait();
    }
    public static void showDialogErrorNullExpenditureInvoice(){
        alertError.setTitle("Ошибка");
        alertError.setHeaderText(null);
        alertError.setContentText("Такой расходной накладной не существует");
        alertError.showAndWait();
    }

    public static void showDialogInformationDeleteExpenditureInvoice(){
        alertInformation.setTitle("Информация");
        alertInformation.setHeaderText(null);
        alertInformation.setContentText("Расходная накладная удалена");
        alertInformation.showAndWait();
    }
    public static void showDialogInformationDeleteWarehouse2(){
        alertInformation.setTitle("Информация");
        alertInformation.setHeaderText(null);
        alertInformation.setContentText("Товар со склада был отгружен");
        alertInformation.showAndWait();
    }
}
