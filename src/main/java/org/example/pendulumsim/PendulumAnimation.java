package org.example.pendulumsim;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class PendulumAnimation extends Application {
    //initialization
    private double damping = 0.995;
    private double length = 200;
    private double angle = Math.PI / 4;
    private double angularVelocity = 0;
    private double angularAcceleration = 0;
    private double speed = 1.0;
    private double bobSize = 10;

    public static void main(String[] args) {
        launch(args);
    }

    //build a stage
    @Override
    public void start(Stage primaryStage) {
        AnchorPane root = new AnchorPane();
        Canvas canvas = new Canvas(500, 500);
        root.getChildren().add(canvas);


        //build labels (duh)
        addLabeledSlider(root, "Length", 50, 400, length, 350, 30, newValue -> length = newValue);
        addLabeledSlider(root, "Angle", -Math.PI / 2, Math.PI / 2, angle, 350, 60, newValue -> angle = newValue);
        addLabeledSlider(root, "Speed", 0.1, 8, speed, 350, 90, newValue -> speed = newValue);
        addLabeledSlider(root, "Bob Size", 5, 50, bobSize, 350, 120, newValue -> bobSize = newValue);
        addLabeledSlider(root, "Friction", 0.9, 1.0, damping, 350, 150, newValue -> damping = newValue);

        //Animation time ZOOOOOOOOOOOOOOOOOOOOOMIN
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double g = 9.8; // me when i know gravity
                double dt = 0.02 * speed;  //kinematics DN


                angularAcceleration = (-g / length) * Math.sin(angle);


                angularVelocity += angularAcceleration * dt;
                angularVelocity *= damping;


                angle += angularVelocity * dt;

                // im bad at drawing
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());


                double pivotX = canvas.getWidth() / 2;
                double pivotY = 100;
                double endX = pivotX + length * Math.sin(angle);
                double endY = pivotY + length * Math.cos(angle);

                // pendulum draw fr fr
                gc.strokeLine(pivotX, pivotY, endX, endY);
                gc.fillOval(endX - bobSize / 2, endY - bobSize / 2, bobSize, bobSize);
            }
        };
        timer.start();

        Scene scene = new Scene(root, 500, 500);
        primaryStage.setTitle("Pendulum Simulation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // I FUCKING LOVE HELPER METHODS
    private void addLabeledSlider(AnchorPane root, String labelText,
                                  double sliderMin, double sliderMax,
                                  double sliderValue, double layoutX, double labelY,
                                  Consumer<Double> sliderChangeListener) {
        Label label = new Label(labelText);
        label.setLayoutX(layoutX);
        label.setLayoutY(labelY + 8);
        root.getChildren().add(label);
        Slider slider = new Slider(sliderMin, sliderMax, sliderValue);
        slider.setLayoutX(layoutX);
        slider.setLayoutY(labelY);
        root.getChildren().add(slider);
        slider.valueProperty()
                .addListener((obs, oldVal, newVal)
                        -> sliderChangeListener.accept(newVal.doubleValue()));
    }
}