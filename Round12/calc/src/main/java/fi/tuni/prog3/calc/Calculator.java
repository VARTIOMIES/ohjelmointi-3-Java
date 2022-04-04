package fi.tuni.prog3.calc;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Calculator extends Application{

    @Override
    public void start(Stage stage) {

        stage.setTitle("Benis");


        // Create a new scene where we want everything to be contained in grid
        GridPane grid = new GridPane();
        Scene scene = new Scene(grid,500,500);
        stage.setScene(scene);

        // All needed elements for the scene
        TextField fieldOp1 = new TextField();
        fieldOp1.setId("fieldOp1");
        TextField fieldOp2 = new TextField();
        fieldOp2.setId("fieldOp2");

        final Label labelOp1 = new Label("First operand:");
        labelOp1.setId("labelOp1");
        final Label labelOp2 = new Label("Second operand:");
        labelOp2.setId("labelOp2");

        Button btnAdd = new Button("Add");
        btnAdd.setId("btnAdd");
        Button btnSub = new Button("Subtract");
        btnSub.setId("btnSub");
        Button btnMul = new Button("Multiply");
        btnMul.setId("btnMul");
        Button btnDiv = new Button("Divide");
        btnDiv.setId("btnDiv");

        final Label labelRes = new Label("Result:");
        labelRes.setId("labelRes");
        Label fieldRes = new Label();
        fieldRes.setId("fieldRes");
        fieldRes.setBackground(new Background(new BackgroundFill(Color.WHITE,new CornerRadii(1.0),new Insets(1.0))));

        // Putting all elements in wanted order in ui

        grid.add(labelOp1,0,0);
        grid.add(fieldOp1,2,0);
        grid.add(labelOp2,0,1);
        grid.add(fieldOp2,2,1);
        grid.add(btnAdd,0,2);
        grid.add(btnSub,1,2);
        grid.add(btnMul,2,2);
        grid.add(btnDiv,3,2);
        grid.add(labelRes,0,3);
        grid.add(fieldRes,2,3);

        // All actions for the buttons
        btnAdd.setOnAction(actionEvent -> {
            var a = Double.parseDouble(fieldOp1.getText());
            var b = Double.parseDouble(fieldOp2.getText());
            double result = a+b;
            fieldRes.setText(String.format("%f",result));
        });

        btnSub.setOnAction(actionEvent -> {
            var a = Double.parseDouble(fieldOp1.getText());
            var b = Double.parseDouble(fieldOp2.getText());
            double result = a-b;
            fieldRes.setText(String.format("%f",result));
        });

        btnMul.setOnAction(actionEvent -> {
            var a = Double.parseDouble(fieldOp1.getText());
            var b = Double.parseDouble(fieldOp2.getText());
            double result = a*b;
            fieldRes.setText(String.format("%f",result));
        });

        btnDiv.setOnAction(actionEvent -> {
            var a = Double.parseDouble(fieldOp1.getText());
            var b = Double.parseDouble(fieldOp2.getText());
            double result = a/b;
            fieldRes.setText(String.format("%f",result));
        });


        stage.setOnCloseRequest((event) -> { Platform.exit();});

        stage.show();


    }

    public static void main(String[] args){
        //Application app = new Calculator(new Stage());
        launch();

    }
}
