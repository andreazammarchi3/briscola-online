package it.zammarchi.briscola.client.gui.view;

import it.zammarchi.briscola.client.RemoteBriscola;
import it.zammarchi.briscola.client.gui.controller.LauncherController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * View of the login window.
 */
public class LauncherView extends AbstractView {
    /**
     * TextField where user enters his nickname.
     */
    @FXML
    private TextField nicknameTextField;

    /**
     * Login button.
     */
    @FXML
    private Button okNicknameButton;

    private final LauncherController launcherController;

    public LauncherView(Stage stage, RemoteBriscola client) {
        super(stage, client, null, null);
        launcherController = new LauncherController(this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Login button click event.
     * @param event
     */
    @FXML
    public void okNicknameButtonPress(ActionEvent event) {
        setNickname(nicknameTextField.getText());
        launcherController.okNicknameButtonPress();
    }
}
