package it.zammarchi.briscola.client.gui.view;

import it.zammarchi.briscola.client.RemoteBriscola;
import it.zammarchi.briscola.client.gui.controller.HomeController;
import it.zammarchi.briscola.client.gui.utils.AlertLauncher;
import it.zammarchi.briscola.common.exceptions.MissingException;
import it.zammarchi.briscola.common.exceptions.ServerOfflineException;
import it.zammarchi.briscola.common.model.Lobby;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * View fot the home menu. Shows the lobbies list.
 */
public class HomeView extends AbstractView {
    /**
     * Nickname label.
     */
    @FXML
    private Label userLabel;

    /**
     * TableView with the lobbies list.
     */
    @FXML
    private TableView<Lobby> lobbiesTableView;

    /**
     * Lobby's id column.
     */
    @FXML
    private TableColumn<Lobby, Integer> idColumn;

    /**
     * Lobby's userA column.
     */
    @FXML
    private TableColumn<Lobby, String> userAColumn;

    /**
     * Lobby's userB column.
     */
    @FXML
    private TableColumn<Lobby, String> userBColumn;

    /**
     * Button for creating a new lobby.
     */
    @FXML
    private Button joinLobbyButton;

    /**
     * Button for joining to existing lobby.
     */
    @FXML
    private Button createLobbyButton;

    private final HomeController homeController;

    public HomeView(Stage stage, RemoteBriscola client, String nickname) {
        super(stage, client, nickname, null);
        this.homeController = new HomeController(this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userLabel.setText(getNickname());

        joinLobbyButton.disableProperty().bind(
                Bindings.isEmpty(lobbiesTableView.getSelectionModel().getSelectedItems()));

        setUpTableView();

        setTimeline(new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            try {
                homeController.refreshLobbiesList();
            } catch (ServerOfflineException ex) {
                stopTimeline();
                AlertLauncher.showServerOfflineAlert(ex.getMessage());
            }
        })));
    }

    private void setUpTableView() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        userAColumn.setCellValueFactory(param -> {
            if (param.getValue() != null && param.getValue().getUserA() != null) {
                return new SimpleStringProperty(param.getValue().getUserA().getName());
            } else {
                return new SimpleStringProperty("-");
            }
        });

        userBColumn.setCellValueFactory(param -> {
            if (param.getValue() != null && param.getValue().getUserB() != null) {
                return new SimpleStringProperty(param.getValue().getUserB().getName());
            } else {
                return new SimpleStringProperty("-");
            }
        });

        lobbiesTableView.prefHeightProperty().bind(getStage().heightProperty());
        lobbiesTableView.prefWidthProperty().bind(getStage().widthProperty());
    }

    /**
     * Get the TableView with the lobbies list.
     * @return the TableView
     */
    public TableView<Lobby> getLobbiesTableView() {
        return lobbiesTableView;
    }

    /**
     * Join lobby button click.
     * @param event
     */
    @FXML
    public void joinLobby(ActionEvent event) {
        stopTimeline();
        try {
            homeController.joinLobby();
        } catch (ServerOfflineException e) {
            AlertLauncher.showServerOfflineAlert(e.getMessage());
        }
    }

    /**
     * Create lobby button click.
     * @param event
     */
    @FXML
    public void createLobby(ActionEvent event) {
        stopTimeline();
        try {
            homeController.createLobby();
        } catch (ServerOfflineException e) {
            AlertLauncher.showServerOfflineAlert(e.getMessage());
        } catch (MissingException e) {
            AlertLauncher.showOkAlert(Alert.AlertType.ERROR, e.getMessage());
        }
    }
}
