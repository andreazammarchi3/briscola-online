package it.zammarchi.briscola.client.gui.controller;

import it.zammarchi.briscola.client.gui.utils.SceneSwapper;
import it.zammarchi.briscola.client.gui.view.LobbyView;
import it.zammarchi.briscola.client.gui.view.MatchView;
import it.zammarchi.briscola.common.exceptions.MissingException;
import it.zammarchi.briscola.common.exceptions.ServerOfflineException;
import it.zammarchi.briscola.common.model.Cards;

/**
 * Controller of the match view.
 */
public class MatchController {
    private final MatchView matchView;

    public MatchController(MatchView matchView) {
        this.matchView = matchView;
    }

    /**
     * User plays a card.
     * @param card the card played
     * @throws MissingException
     * @throws ServerOfflineException
     */
    public void playCard(Cards card) throws MissingException, ServerOfflineException {
        matchView.getClient().userPlaysCard(matchView.getLobby().getId(), matchView.getUser(), card);
    }

    /**
     * User quits match.
     */
    public void quitMatch() {
        new SceneSwapper().swapScene(
                new LobbyView(matchView.getStage(),
                        matchView.getClient(),
                        matchView.getNickname(),
                        matchView.getLobby()),
                "LobbyView.fxml",
                matchView.getStage());
        SceneSwapper.setDim(matchView.getStage(), 600, 600);
    }
}
