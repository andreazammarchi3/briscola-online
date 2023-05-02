package it.zammarchi.briscola.server;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * Launcher class for the server. Start a new server and start the GUI.
 */
public class ServiceLauncher extends Application {

    private static final int DEFAULT_PORT = 7777;

    static BriscolaService service;

    public static void main(String[] args) {
        service = new BriscolaService(args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        service.startService();
        primaryStage.setOnCloseRequest(event -> {
            service.stopService();
        });

        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.setWidth(600);
        primaryStage.setHeight(200);
        primaryStage.setTitle("Briscola Online Server");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("/imgs/back.png"));

        Label label = new Label("Il server e' attivo. Chiudere questa finestra per terminare server.");
        label.setTextAlignment(TextAlignment.CENTER);
        borderPane.setCenter(label);

        primaryStage.show();
    }
}
