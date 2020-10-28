/*
Emily Farrell
CSC 360
RecursiveSwirls.java
This program draws swirls with varying orders and numbers of branches according to user input.
To update the order or branch count, type the desired number into its designated text field and
press enter. RecursiveSwirls.java will then draw the appropriate shape out of many randomly-colored
lines.
 */


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.Random;


public class RecursiveSwirls extends Application {
    @Override //Override the start method in the Application class
    public void start(Stage primaryStage) {
        SwirlPane pane = new SwirlPane();
        TextField tfBranches = new TextField();
        TextField tfOrder = new TextField();
        tfOrder.setOnAction(
                event -> {
                    pane.setDepth(Integer.parseInt(tfOrder.getText()));
                    pane.setBranches(Integer.parseInt(tfBranches.getText()));
                });
        tfOrder.setPrefColumnCount(8);
        tfOrder.setAlignment(Pos.BOTTOM_RIGHT);
        tfBranches.setOnAction(
                event -> {
                    pane.setDepth(Integer.parseInt(tfOrder.getText()));
                    pane.setBranches(Integer.parseInt(tfBranches.getText()));
                });
        tfBranches.setPrefColumnCount(8);
        tfBranches.setAlignment(Pos.BOTTOM_LEFT);

        // Pane to hold label, text field, and a button
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(new Label("Enter an order: "), tfOrder, new Label("Enter branches: "), tfBranches);
        hBox.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(pane);
        borderPane.setBottom(hBox);

        //Create a scene and place it in the stage
        Scene scene = new Scene(borderPane, 630, 630);
        primaryStage.setTitle("Recursive Swirls"); //Set the stage title
        primaryStage.setScene(scene); //Place the scene in the stage
        primaryStage.show(); //Display the stage

        scene.widthProperty().addListener(ov -> pane.paint());
        scene.heightProperty().addListener(ov -> pane.paint());
    }

    //Pane for displaying triangles
    static class SwirlPane extends Pane {
        private int depth = 0;
        private int branches = 0;

        public void setDepth(int depth) {
            this.depth = depth;
            paint();
        }

        public void setBranches(int branches) {
            this.branches = branches;
            paint();
        }

        public void paint() {
            getChildren().clear();

            paintSwirl(depth, getWidth()/2, getHeight()/2, getHeight()/4, Math.PI/12);
        }

        public static int randomColor() {
            Random random = new Random();
            int value = random.nextInt(255);
            return value;
        }

        public void paintSwirl(int depth, double x1, double y1, double length, double angle) {
            if (depth >= 0) {

                for (int i = branches; i > 0; i--) {
                    double x2 = x1 + Math.cos(angle) * length;
                    double y2 = y1 - Math.sin(angle) * length;
                    // Draw the line
                    Line line = new Line(x1,y1,x2,y2);
                    line.setStroke(Color.rgb(randomColor(),randomColor(),randomColor()));
                    getChildren().add(line);


                    //recursive call
                    paintSwirl(depth-1,x2,y2,length*0.4,angle + Math.PI / 3);

                    //calculate angle for next branch
                    angle += 2 * Math.PI / branches;
                }


            }
        }
    }
}