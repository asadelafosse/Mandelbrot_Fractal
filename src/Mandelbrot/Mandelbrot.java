package Mandelbrot;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.CanvasBuilder;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


/**
 * Created by Sebastien on 4-3-2016.
 * Adapted from @author hameister (http://www.hameister.org/JavaFX_MandelbrotSet.html)
 */
public class Mandelbrot extends Application {

    public static final int WIDTH = 585;
    public static final int HEIGHT = 450;
    private static double real_minimum = -2.25;
    private static double real_maximum = 1.0;
    private static double imaginary_minimum = -1.25;
    private static double imaginary_maximum = 1.25;
    static double canvasCurrentX = 0;
    static double canvasCurrentY = 0;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        Group navigateGroup = new Group();

        Group sliderGroup = new Group();
        Rectangle rect = new Rectangle(150,50);
        rect.setFill(Color.WHITE);
        Slider slider = new Slider(1,8,1);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(1);
        slider.setBlockIncrement(0.1);
        sliderGroup.getChildren().addAll(rect, slider);
        sliderGroup.setTranslateX(25);
        sliderGroup.setTranslateY(25);
        navigateGroup.getChildren().add(sliderGroup);

        final Canvas canvas = CanvasBuilder.create().height(HEIGHT).width(WIDTH).layoutX(25).layoutY(25).build();
        paintSet(canvas.getGraphicsContext2D(), real_minimum, real_maximum, imaginary_minimum, imaginary_maximum);
        canvas.scaleXProperty().bind(slider.valueProperty());
        canvas.scaleYProperty().bind(slider.valueProperty());

        Group buttonGroup = new Group();
        Button buttonUp = new Button("Up");
        buttonUp.setTranslateX(50);
        buttonUp.setTranslateY(75);
        buttonUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                canvasCurrentY += 50;
                canvas.setTranslateY(canvasCurrentY);
            }
        });
        Button buttonDown = new Button("Down");
        buttonDown.setTranslateX(50);
        buttonDown.setTranslateY(125);
        buttonDown.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                canvasCurrentY -= 50;
                canvas.setTranslateY(canvasCurrentY);
            }
        });
        Button buttonLeft = new Button("Left");
        buttonLeft.setTranslateX(25);
        buttonLeft.setTranslateY(100);
        buttonLeft.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                canvasCurrentX += 50;
                canvas.setTranslateX(canvasCurrentX);
            }
        });
        Button buttonRight = new Button("Right");
        buttonRight.setTranslateX(75);
        buttonRight.setTranslateY(100);
        buttonRight.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                canvasCurrentX -= 50;
                canvas.setTranslateX(canvasCurrentX);
            }
        });


        buttonGroup.getChildren().addAll(buttonUp, buttonDown, buttonLeft, buttonRight);
        navigateGroup.getChildren().add(buttonGroup);

        root.getChildren().add(canvas);
        root.getChildren().add(navigateGroup);

        Scene scene = new Scene(root, WIDTH + 50, HEIGHT + 50);
        scene.setFill(Color.GREY);
        primaryStage.setTitle("asadelafosse's Mandelbrot Fractal");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void paintSet(GraphicsContext ctx, double reMin, double reMax, double imMin, double imMax) {
        double precision = Math.max((reMax - reMin) / WIDTH, (imMax - imMin) / HEIGHT);
        int convergenceSteps = 50;
        for (double c = reMin, xR = 0; xR < WIDTH; c = c + precision, xR++) {
            for (double ci = imMin, yR = 0; yR < HEIGHT; ci = ci + precision, yR++) {
                double convergenceValue = checkConvergence(ci, c, convergenceSteps);
                double t1 = convergenceValue / convergenceSteps;
                double hsbHue = t1*360;

                if (convergenceValue != convergenceSteps) {
                    Color color = Color.hsb(hsbHue, 0.8, 1.0, 1.0);
                    ctx.setFill(color);
                } else {
                    ctx.setFill(Color.BLACK); // Convergence Color
                }
                ctx.fillRect(xR, yR, 1, 1);
            }
        }
    }

    private int checkConvergence(double ci, double c, int convergenceSteps) {
        double z = 0;
        double zi = 0;
        for (int i = 0; i < convergenceSteps; i++) {
            double ziT = 2 * (z * zi);
            double zT = (z * z) - (zi * zi);
            z = zT + c;
            zi = ziT + ci;

            if (z * z + zi * zi >= 4.0) {
                return i;
            }
        }
        return convergenceSteps;
    }



    public static void main(String[] args) {
        launch(args);
    }

}
