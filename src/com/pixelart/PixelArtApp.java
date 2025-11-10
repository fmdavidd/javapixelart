package com.pixelart;

import com.pixelart.controller.GestorLienzo;
import com.pixelart.model.Dibujo;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class PixelArtApp extends Application {

    private GestorLienzo gestor;
    private Canvas canvas;
    private GraphicsContext gc;
    private static final int CANVAS_SIZE = 640;
    private int gridSize = 32; // Tamaño de la cuadrícula
    private int pixelSize; // Tamaño de cada píxel

    private ColorPicker colorPicker;
    private Label infoLabel;
    private ComboBox<Integer> gridSizeCombo;

    @Override
    public void start(Stage primaryStage) {
        gestor = new GestorLienzo();
        pixelSize = CANVAS_SIZE / gridSize;

        // Layout principal
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Panel superior - Controles
        VBox topPanel = crearPanelSuperior();
        root.setTop(topPanel);

        // Canvas central
        canvas = new Canvas(CANVAS_SIZE, CANVAS_SIZE);
        gc = canvas.getGraphicsContext2D();

        // Eventos del canvas
        canvas.setOnMouseClicked(e -> {
            int x = (int) (e.getX() / pixelSize);
            int y = (int) (e.getY() / pixelSize);

            if (x >= 0 && x < gridSize && y >= 0 && y < gridSize) {
                gestor.dibujarPixel(x, y);
                dibujarCanvas();
                actualizarInfo();
            }
        });

        canvas.setOnMouseDragged(e -> {
            int x = (int) (e.getX() / pixelSize);
            int y = (int) (e.getY() / pixelSize);

            if (x >= 0 && x < gridSize && y >= 0 && y < gridSize) {
                gestor.dibujarPixel(x, y);
                dibujarCanvas();
            }
        });

        // Panel derecho - Paleta
        VBox rightPanel = crearPanelDerecho();

        // Contenedor del canvas
        StackPane canvasContainer = new StackPane(canvas);
        canvasContainer.setStyle("-fx-background-color: #f0f0f0;");

        root.setCenter(canvasContainer);
        root.setRight(rightPanel);

        // Panel inferior - Info
        infoLabel = new Label("Listo para dibujar");
        infoLabel.setPadding(new Insets(5));
        root.setBottom(infoLabel);

        // Dibujar canvas inicial
        dibujarCanvas();

        Scene scene = new Scene(root, 900, 700);
        primaryStage.setTitle("Pixel Art Editor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox crearPanelSuperior() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(10));

        HBox toolBar = new HBox(15);
        toolBar.setAlignment(Pos.CENTER_LEFT);

        // Selector de color
        Label colorLabel = new Label("Color:");
        colorPicker = new ColorPicker(Color.BLACK);
        colorPicker.setOnAction(e -> {
            Color color = colorPicker.getValue();
            String hexColor = String.format("#%02X%02X%02X",
                    (int)(color.getRed() * 255),
                    (int)(color.getGreen() * 255),
                    (int)(color.getBlue() * 255));
            gestor.setColor(hexColor);
        });

        // Selector de tamaño de cuadrícula
        Label gridLabel = new Label("Tamaño cuadrícula:");
        gridSizeCombo = new ComboBox<>();
        for (int size : GestorLienzo.getTamaniosDisponibles()) {
            gridSizeCombo.getItems().add(size);
        }
        gridSizeCombo.setValue(gridSize);
        gridSizeCombo.setOnAction(e -> cambiarTamanioCuadricula());

        // Botón limpiar
        Button clearBtn = new Button("Limpiar");
        clearBtn.setOnAction(e -> {
            gestor.limpiarLienzo();
            dibujarCanvas();
            actualizarInfo();
        });

        // Botón borrador
        Button eraserBtn = new Button("Borrador");
        eraserBtn.setOnAction(e -> {
            gestor.setColor("#FFFFFF");
            colorPicker.setValue(Color.WHITE);
        });

        toolBar.getChildren().addAll(
                colorLabel, colorPicker,
                new Separator(),
                gridLabel, gridSizeCombo,
                new Separator(),
                eraserBtn, clearBtn
        );

        panel.getChildren().add(toolBar);
        return panel;
    }

    private VBox crearPanelDerecho() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(10));
        panel.setPrefWidth(200);

        Label paletaLabel = new Label("Paleta de colores:");
        paletaLabel.setStyle("-fx-font-weight: bold;");

        GridPane paleta = new GridPane();
        paleta.setHgap(5);
        paleta.setVgap(5);

        String[] colores = GestorLienzo.getColoresPermitidos();
        int col = 0, row = 0;

        for (String colorHex : colores) {
            Button colorBtn = new Button();
            colorBtn.setPrefSize(40, 40);
            colorBtn.setStyle("-fx-background-color: " + colorHex + "; -fx-border-color: black;");
            colorBtn.setOnAction(e -> {
                gestor.setColor(colorHex);
                colorPicker.setValue(Color.web(colorHex));
            });

            paleta.add(colorBtn, col, row);
            col++;
            if (col > 1) {
                col = 0;
                row++;
            }
        }

        panel.getChildren().addAll(paletaLabel, paleta);
        return panel;
    }

    private void dibujarCanvas() {
        // Limpiar canvas
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, CANVAS_SIZE, CANVAS_SIZE);

        Dibujo dibujo = gestor.getDibujo();

        // Dibujar píxeles coloreados
        if (dibujo != null) {
            for (var cuadricula : dibujo.getCuadriculas()) {
                int x = cuadricula.getIndiceX();
                int y = cuadricula.getIndiceY();
                String colorHex = cuadricula.getColor();

                gc.setFill(Color.web(colorHex));
                gc.fillRect(x * pixelSize, y * pixelSize, pixelSize, pixelSize);
            }
        }

        // Dibujar cuadrícula
        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(0.5);

        for (int i = 0; i <= gridSize; i++) {
            // Líneas verticales
            gc.strokeLine(i * pixelSize, 0, i * pixelSize, CANVAS_SIZE);
            // Líneas horizontales
            gc.strokeLine(0, i * pixelSize, CANVAS_SIZE, i * pixelSize);
        }
    }

    private void cambiarTamanioCuadricula() {
        gridSize = gridSizeCombo.getValue();
        pixelSize = CANVAS_SIZE / gridSize;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cambiar tamaño");
        alert.setHeaderText("¿Crear nuevo dibujo?");
        alert.setContentText("Cambiar el tamaño de la cuadrícula creará un nuevo dibujo. ¿Continuar?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            gestor.nuevoDibujo("Nuevo dibujo", gridSize);
            dibujarCanvas();
            actualizarInfo();
        } else {
            gridSizeCombo.setValue(gridSize);
        }
    }

    private void actualizarInfo() {
        Dibujo dibujo = gestor.getDibujo();
        int pixelesPintados = dibujo != null ? dibujo.getCuadriculas().size() : 0;
        int coloresUsados = dibujo != null ? dibujo.getClavesColores().size() : 0;

        infoLabel.setText(String.format(
                "Píxeles pintados: %d | Colores en paleta: %d | Cuadrícula: %dx%d",
                pixelesPintados, coloresUsados, gridSize, gridSize
        ));
    }

    public static void main(String[] args) {
        launch(args);
    }
}