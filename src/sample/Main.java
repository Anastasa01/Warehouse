package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import system.DBWarehouse;


public class Main extends Application { //класс наследуется от javafx.application.Application

    @Override
    public void start(Stage primaryStage) throws Exception{ //start чтобы можно было управлять свойствами (функционалом нашего окна)
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        primaryStage.setTitle("Учет товара на складе"); //устанавливает заголовок окна
        primaryStage.setScene(new Scene(root, 300, 400)); //уст. размеры окна, setScene - устанавливает сцену (объект Scene) для объекта Stage, создает Scene с корневым узлом root, с шириной width и высотой height
        primaryStage.show(); //отображает окно

        DBWarehouse.connectionBD();

        DBWarehouse.newTable();
    }


    public static void main(String[] args) {
        launch(args);
    } //вызываем статический метод Application — launch() для запуска нашего окна
}
