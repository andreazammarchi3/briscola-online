package it.zammarchi.briscola.client.gui.controller;

import it.zammarchi.briscola.client.gui.utils.AlertLauncher;
import it.zammarchi.briscola.client.gui.utils.SceneSwapper;
import it.zammarchi.briscola.client.gui.view.HomeView;
import it.zammarchi.briscola.client.gui.view.LobbyView;
import it.zammarchi.briscola.common.exceptions.MissingException;
import it.zammarchi.briscola.common.exceptions.ServerOfflineException;
import it.zammarchi.briscola.common.model.Lobby;
import javafx.scene.control.Alert;

/**
 * Controller of the home menu.
 */
public class HomeController {
    private final HomeView homeView;

    public HomeController(HomeView homeView) {
        this.homeView = homeView;
    }

    /**
     * Refresh the lobbies list in the TableView.
     * @throws ServerOfflineException
     */
    public void refreshLobbiesList() throws ServerOfflineException {
        Lobby selected = homeView.getLobbiesTableView().getSelectionModel().getSelectedItem();
        var lobbies = homeView.getClient().getAllLobbies();
        homeView.getLobbiesTableView().getItems().clear();
        homeView.getLobbiesTableView().getItems().addAll(lobbies);
        homeView.getLobbiesTableView().getSelectionModel().select(selected);
    }

    /**
     * Join to the lobby selected.
     * @throws ServerOfflineException
     */
    public void joinLobby() throws ServerOfflineException {
        Lobby lobby = homeView.getLobbiesTableView().getSelectionModel().getSelectedItem();
        try {
            if (lobby.isFull()) {
                AlertLauncher.showOkAlert(Alert.AlertType.ERROR,
                        "Lobby al completo. Selezionare una lobby differente.");
            } else {
                swapToLobbyView(lobby);
            }
        } catch (MissingException e) {
            AlertLauncher.showOkAlert(Alert.AlertType.ERROR,
                    "Lobby non più disponibile.\n\nDettagli: " + e.getMessage());
        }
    }

    /**
     * Create a new lobby.
     * @throws ServerOfflineException
     * @throws MissingException
     */
    public void createLobby() throws ServerOfflineException, MissingException {
        Lobby lobby = homeView.getClient().createLobby();
        swapToLobbyView(lobby);
    }

    private void swapToLobbyView(Lobby lobby) throws MissingException, ServerOfflineException {
        homeView.getClient().joinUserToLobby(lobby.getId(), homeView.getNickname());
        lobby = homeView.getClient().getLobby(lobby.getId());
        new SceneSwapper().swapScene(
                new LobbyView(homeView.getStage(), homeView.getClient(), homeView.getNickname(), lobby),
                "LobbyView.fxml",
                homeView.getStage()
        );
        SceneSwapper.setDim(homeView.getStage(), 600, 600);
    }
}
