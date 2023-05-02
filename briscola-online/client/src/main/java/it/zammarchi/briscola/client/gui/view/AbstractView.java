package it.zammarchi.briscola.client.gui.view;

import it.zammarchi.briscola.client.RemoteBriscola;
import it.zammarchi.briscola.common.exceptions.MissingException;
import it.zammarchi.briscola.common.exceptions.ServerOfflineException;
import it.zammarchi.briscola.common.model.Lobby;
import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * A basic View, containing the basic functions of a view. Each View of the App should extend this class.
 */
public abstract class AbstractView implements Initializable {
    private final Stage stage;
    private final RemoteBriscola client;
    private String nickname;
    private Lobby lobby;
    private Timeline timeline;

    public AbstractView(Stage stage, RemoteBriscola client, String nickname, Lobby lobby) {
        this.stage = stage;
        this.client = client;
        this.nickname = nickname;
        this.lobby = lobby;

        getStage().setOnCloseRequest(event -> {
            try {
                disconnect();
            } catch (MissingException | ServerOfflineException e) {
                // do nothing
            }
        });
    }

    /**
     * Set the JavaFX Timeline.
     * @param timeline the timeline
     */
    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.playFromStart();
    }

    /**
     * Stop JavaFX Timeline.
     */
    public void stopTimeline() {
        timeline.stop();
    }

    /**
     * Get the RemoteBriscola instance.
     * @return client instance.
     */
    public RemoteBriscola getClient() {
        return client;
    }

    /**
     * Get primary stage.
     * @return primary stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Get user nickname.
     * @return nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Set user nickname.
     * @param nickname the nickname inserted by the user
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Get the lobby.
     * @return lobby
     */
    public Lobby getLobby() {
        return lobby;
    }

    /**
     * Set the lobby.
     * @param lobby the lobby updated
     */
    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    /**
     * When closing window: if in match, leave the match. If in lobby, leave the lobby. If registered, disconnect.
     * @throws MissingException
     * @throws ServerOfflineException
     */
    private void disconnect() throws MissingException, ServerOfflineException {
        stopTimeline();
        if (lobby != null) {
            if (client.getAllMatches().stream().anyMatch(match -> match.getId() == lobby.getId())) {
                client.deleteMatch(lobby.getId());
            }
            client.deleteUserFromLobby(lobby.getId(), nickname);
        }
        if (nickname != null) {
            client.deleteUser(nickname);
        }
    }
}
