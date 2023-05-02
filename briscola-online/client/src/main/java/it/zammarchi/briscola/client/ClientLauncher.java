package it.zammarchi.briscola.client;

import it.zammarchi.briscola.client.gui.utils.SceneSwapper;
import it.zammarchi.briscola.client.gui.view.LauncherView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Launcher class for the client. Start a new client for the Briscola server and start the GUI.
 */
public class ClientLauncher extends Application {
    private static final int DEFAULT_PORT = 7777;
    private static RemoteBriscola client;

    public static void main(String[] args) {
        // Run Client
        client = new RemoteBriscola("localhost", DEFAULT_PORT);

        // Run GUI
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        LauncherView launcherView = new LauncherView(primaryStage, client);
        new SceneSwapper().swapScene(launcherView, "LauncherView.fxml", primaryStage);
        SceneSwapper.setUpStage(primaryStage);
        SceneSwapper.setDim(primaryStage, 600, 400);
    }
}
