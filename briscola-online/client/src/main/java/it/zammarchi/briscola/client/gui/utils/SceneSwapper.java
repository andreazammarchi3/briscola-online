package it.zammarchi.briscola.client.gui.utils;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Class for swapping scenes.
 */
public class SceneSwapper {

    private static final String PATH = "/view/";


    /**
     * Create a scene with a fxml file and assign a controller to it a controller.
     * @param controller
     * 				The controller of the fxml file.
     * @param fxml
     * 				The fxml file name which has to be loaded in the scene.
     * @param stage
     * 				The stage containing the new scene.
     */
    public void swapScene(final Initializable controller, final String fxml, final Stage stage){
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(PATH + fxml)));
        loader.setController(controller);
        try {
            Parent root = loader.load();
            Scene home = new Scene(root);
            stage.setScene(home);
            stage.show();
            // setUpStage(stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * If a new stage has been created, set it up (title, size, icon and position). Use only for menu scenes.
     * @param stage the stage to be set up
     */
    public static void setUpStage(Stage stage) {
        stage.setResizable(true);
        stage.setTitle("Briscola Online");
        stage.show();
        stage.getIcons().add(CardsImages.BACK.getImage());
    }

    /**
     * Update the size of the stage.
     * @param stage the stage to be resized
     */
    public static void setDim(Stage stage, int width, int height) {
        stage.setWidth(width);
        stage.setHeight(height);
    }
}
