import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.shape.Polygon;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Poly extends Application{
    private IntegerProperty n = new SimpleIntegerProperty(3);
    @Override
    public void start(Stage stage){
            /* Create a rootPane to format the layout of our nodes on Stage */
        BorderPane rootPane = new BorderPane();
        Scene scene = new Scene(rootPane, 500, 500);
        RegularPolygonPane myPoly = new RegularPolygonPane();
        rootPane.setCenter(myPoly);

            /* Create HBox to layout bottom pane */
        HBox lowerPane = new HBox();
        lowerPane.setSpacing(75);

            /* Create and format the buttons */
        Button up = new Button("+1");
        Button down = new Button("-1");
        up.setPadding(new Insets(20, 25, 20, 25));
        down.setPadding(new Insets(20, 25, 20, 25));
        up.setFont(new Font(20));
        down.setFont(new Font(20));

            /* Create a label to display the number of sides.  Format */
        Label num = new Label();
        num.textProperty().bind(n.asString());
        num.setFont(Font.font("Comic Sans", FontWeight.BOLD, 50));


            /* Format and populate lowerPane */
        lowerPane.setPadding(new Insets(10, 10, 10, 10));
        lowerPane.getChildren().addAll(down, num, up);
        lowerPane.setAlignment(Pos.CENTER);
        rootPane.setBottom(lowerPane);

            /* Set up the Stage for displaying the polygon and buttons */
        stage.setTitle("Polygon With N sides");
        stage.setScene(scene);
        stage.show();

            /* Add Functionality for Button up */
        up.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                n.setValue(n.getValue() + 1);
            }
        });

            /* Add functionality for Button down*/
        down.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if ( n.getValue() > 3) {
                    n.setValue(n.getValue() - 1);

                }
                /* Error handling for attempting to have less than 3 sides */
                else {
                    Alert alert = new Alert(AlertType.INFORMATION, "Polygon's cannot have less than 3 sides.");
                    alert.setTitle("Invalid Side Count");
                    alert.setHeaderText("Cannot subtract 1 from side count.");
                    alert.show();
                }
            }
        });
    }

    class RegularPolygonPane extends Pane {
        private void paint() {
                /* Create and format a polygon object */
            Polygon poly = new Polygon();
            poly.setStrokeWidth(4);
            poly.setFill(Color.WHITE);
            poly.setStroke(Color.BLACK);
            ObservableList<Double> list = poly.getPoints();
            IntegerProperty number = new SimpleIntegerProperty((n.getValue()));        // Create a SimpleIntegerProperty that has default value equal to sides
            number.bind(n);

                /* Set the width and height of the polygon so it scales with its parent pane size */
            double centerX = getWidth() / 2;
            double centerY = getHeight() / 2;
            double radius = Math.min(getWidth(), getHeight()) * 0.4;

                /* Create coordinate pairs for each point in the polygon */
            for (int i = 0; i < number.getValue(); i++){
                list.add(centerX + radius * Math.cos(2 * i * Math.PI / number.getValue()));
                list.add(centerY - radius * Math.sin(2 * i * Math.PI / number.getValue()));
            }

                /* Clear any previous polyLgon, rebuild the updated polygon */
            getChildren().clear();
            getChildren().add(poly);
        }

            /* Set the width of the polygon container, calls paint() to update GUI */
        @Override
        public void setWidth(double width){
            super.setWidth(width);
            paint();
        }

        /* Set the height of the polygon container, calls paint() to update GUI */
        @Override
        public void setHeight(double height){
            super.setHeight(height);
            paint();
        }
    }

        /* Main function to launch JavaFX Application */
    public static void main(String[] args){
        launch(args);
    }
}
