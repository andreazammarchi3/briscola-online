package it.zammarchi.briscola.client.gui.controller;

import it.zammarchi.briscola.client.gui.utils.SceneSwapper;
import it.zammarchi.briscola.client.gui.view.HomeView;
import it.zammarchi.briscola.client.gui.view.LobbyView;
import it.zammarchi.briscola.client.gui.view.MatchView;
import it.zammarchi.briscola.common.exceptions.ConflictException;
import it.zammarchi.briscola.common.exceptions.MissingException;
import it.zammarchi.briscola.common.exceptions.ServerOfflineException;

/**
 * Controller of the lobby menu.
 */
public class LobbyController {
    private final LobbyView lobbyView;

    public LobbyController(LobbyView lobbyView) {
        this.lobbyView = lobbyView;
    }

    private void deletePreviousMatchIfExists() throws ServerOfflineException, MissingException {
        if (lobbyView.getClient().getAllMatches().stream().anyMatch(match -> match.getId() == lobbyView.getLobby().getId())) {
            lobbyView.getClient().deleteMatch(lobbyView.getLobby().getId());
        }
    }

    /**
     * Exit from current lobby and go back to the home menu.
     * @throws MissingException
     * @throws ServerOfflineException
     */
    public void goBack() throws MissingException, ServerOfflineException {
        deletePreviousMatchIfExists();
        lobbyView.getClient().deleteUserFromLobby(lobbyView.getLobby().getId(), lobbyView.getNickname());
        new SceneSwapper().swapScene(
                new HomeView(lobbyView.getStage(), lobbyView.getClient(), lobbyView.getNickname()),
                "HomeView.fxml",
                lobbyView.getStage()
        );
        SceneSwapper.setDim(lobbyView.getStage(), 1000, 600);
    }

    /**
     * Start a new match.
     * @throws MissingException
     * @throws ConflictException
     * @throws ServerOfflineException
     */
    public void startMatch() throws MissingException, ConflictException, ServerOfflineException {
        deletePreviousMatchIfExists();
        lobbyView.getClient().startMatch(lobbyView.getLobby());
        swapToMatchView();
    }

    /**
     * Swap to the match view.
     */
    public void swapToMatchView() throws MissingException, ServerOfflineException {
        new SceneSwapper().swapScene(
                new MatchView(lobbyView.getStage(),
                        lobbyView.getClient(),
                        lobbyView.getNickname(),
                        lobbyView.getLobby()),
                "MatchView.fxml",
                lobbyView.getStage()
        );
        SceneSwapper.setDim(lobbyView.getStage(), 1200, 800);
    }
}
