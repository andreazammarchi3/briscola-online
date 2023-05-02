package it.zammarchi.briscola.client.gui.view;

import it.zammarchi.briscola.client.RemoteBriscola;
import it.zammarchi.briscola.client.gui.controller.LobbyController;
import it.zammarchi.briscola.client.gui.utils.AlertLauncher;
import it.zammarchi.briscola.common.exceptions.ConflictException;
import it.zammarchi.briscola.common.exceptions.MissingException;
import it.zammarchi.briscola.common.exceptions.ServerOfflineException;
import it.zammarchi.briscola.common.model.Lobby;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * View of the lobby menu.
 */
public class LobbyView extends AbstractView {
    /**
     * TextField showing the lobby's id.
     */
    @FXML
    private TextField idTextField;

    /**
     * TextField showing the lobby's userA nickname.
     */
    @FXML
    private TextField userATextField;

    /**
     * TextField showing the lobby's userB nickname.
     */
    @FXML
    private TextField userBTextField;

    /**
     * Label showing the status of the lobby (if it is full or not).
     */
    @FXML
    private Label statusLabel;

    /**
     * Button for starting a new match.
     */
    @FXML
    private Button startButton;

    private final LobbyController lobbyController;

    public LobbyView(Stage stage, RemoteBriscola client, String nickname, Lobby lobby) {
        super(stage, client, nickname, lobby);
        lobbyController = new LobbyController(this);

        setTimeline(new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            try {
                updateLobby();
            } catch (MissingException ex) {
                stopTimeline();
                AlertLauncher.showOkAlert(Alert.AlertType.ERROR, ex.getMessage());
            } catch (ServerOfflineException ex) {
                stopTimeline();
                AlertLauncher.showServerOfflineAlert(ex.getMessage());
            }
        })));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idTextField.setText(String.valueOf(getLobby().getId()));
    }

    /**
     * Update the lobby's details.
     */
    public void updateLobby() throws MissingException, ServerOfflineException {
        setLobby(getClient().getLobby(getLobby().getId()));

        updateUsersInLobby();
        updateStatusLabel();
        updateStartButton();
        checkIfMatchStarted();
    }

    private void updateUsersInLobby() {
        if (getLobby().getUserA() == null) {
            userATextField.setText("-");
        } else {
            userATextField.setText(getLobby().getUserA().getName());
        }

        if (getLobby().getUserB() == null) {
            userBTextField.setText("-");
        } else {
            userBTextField.setText(getLobby().getUserB().getName());
        }
    }

    private void updateStatusLabel() {
        if (getLobby().isFull()) {
            statusLabel.setText("Lobby al completo!");
        } else {
            statusLabel.setText("In attesa di un altro giocatore...");
        }
    }

    private void updateStartButton() {
        startButton.setDisable(!getLobby().isFull());
    }

    private void checkIfMatchStarted() throws ServerOfflineException, MissingException {
        if (getClient().getAllMatches().stream().anyMatch(match -> match.getId() == getLobby().getId())) {
            stopTimeline();
            lobbyController.swapToMatchView();
        }
    }

    /**
     * Button go back click.
     * @param event
     */
    @FXML
    public void goBack(ActionEvent event) {
        stopTimeline();
        try {
            lobbyController.goBack();
        } catch (ServerOfflineException e) {
            AlertLauncher.showServerOfflineAlert(e.getMessage());
        } catch (MissingException e) {
            AlertLauncher.showOkAlert(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    /**
     * Button start match click.
     * @param event
     */
    @FXML
    public void startMatch(ActionEvent event) {
        stopTimeline();
        try {
            lobbyController.startMatch();
        } catch (ServerOfflineException | ConflictException e) {
            AlertLauncher.showServerOfflineAlert(e.getMessage());
        } catch (MissingException e) {
            AlertLauncher.showOkAlert(Alert.AlertType.ERROR, e.getMessage());
        }
    }

}
