package it.zammarchi.briscola.client.gui.view;

import it.zammarchi.briscola.client.RemoteBriscola;
import it.zammarchi.briscola.client.gui.controller.MatchController;
import it.zammarchi.briscola.client.gui.utils.AlertLauncher;
import it.zammarchi.briscola.client.gui.utils.CardsImages;
import it.zammarchi.briscola.common.exceptions.MissingException;
import it.zammarchi.briscola.common.exceptions.ServerOfflineException;
import it.zammarchi.briscola.common.model.Cards;
import it.zammarchi.briscola.common.model.Lobby;
import it.zammarchi.briscola.common.model.Match;
import it.zammarchi.briscola.common.model.User;
import it.zammarchi.briscola.common.utils.MathUtils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

/**
 * View of the match.
 */
public class MatchView extends AbstractView {
    /**
     * Label containing the turn status.
     */
    @FXML
    private Label statusLabel;

    /**
     * Label containing the current briscola suit.
     */
    @FXML
    private Label briscolaLabel;

    /**
     * ImageView of the deck cards.
     */
    @FXML
    private ImageView deckImageView;

    /**
     * ImageView of the first played card.
     */
    @FXML
    private ImageView firstPlayedCardImageView;

    /**
     * ImageView of the last card, defining the briscola suit.
     */
    @FXML
    private ImageView lastCardImageView;

    /**
     * HBox containing user hand cards.
     */
    @FXML
    private HBox userHandBox;

    /**
     * Label containing the current score of the user cards.
     */
    @FXML
    private Label preseUserLabel;

    /**
     * Label containing the current score of the enemy cards.
     */
    @FXML
    private Label preseEnemyLabel;

    /**
     * List of the user cards.
     */
    @FXML
    private ListView<String> preseUserListView;

    /**
     * List of the enemy cards.
     */
    @FXML
    private ListView<String> preseEnemyListView;

    private final Map<ImageView, Cards> userHandCards = new LinkedHashMap<>();;
    private final MatchController matchController;

    private Match match;
    private User user;
    private User enemy;

    public MatchView(Stage stage, RemoteBriscola client, String nickname, Lobby lobby) throws MissingException, ServerOfflineException {
        super(stage, client, nickname, lobby);
        matchController = new MatchController(this);
        match = getClient().getMatch(lobby.getId());

        setTimeline(new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            try {
                if (checkCardPlayed()) {
                    updateTable();
                }
            } catch (MissingException ex) {
                // do nothing
            } catch (ServerOfflineException ex) {
                AlertLauncher.showServerOfflineAlert(ex.getMessage());
            }
        })));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpImages();

        briscolaLabel.setText("Briscola: " + match.getBriscolaSuit().toString());

        updateTable();
    }

    /**
     * Set up the size of all ImageViews and draw the basic images.
     */
    private void setUpImages() {
        deckImageView.setImage(CardsImages.BACK.getImage());
        lastCardImageView.setImage(CardsImages.getImageById(match.getLastCard().getId()));
        firstPlayedCardImageView.setImage(CardsImages.NULL.getImage());

        setUpImage(deckImageView);
        setUpImage(firstPlayedCardImageView);
        setUpImage(lastCardImageView);
        userHandCards.forEach((key, value) -> setUpImage(key));
    }

    /**
     * Set up the size of the given ImageView.
     * @param imageView the ImageView to be set up.
     */
    private void setUpImage(ImageView imageView) {
        Rectangle2D croppedPortion = new Rectangle2D(0,0,150,200);
        imageView.setViewport(croppedPortion);
        imageView.setFitWidth(150);
        imageView.setFitHeight(200);
        imageView.setSmooth(true);
    }

    /**
     * Check if a card has been played by a player.
     * @return true if a card has been played, else return false
     * @throws MissingException
     * @throws ServerOfflineException
     */
    private boolean checkCardPlayed() throws MissingException, ServerOfflineException {
        int currentPlayedCards = match.getPlayedCards().size();
        refreshMatch();
        return match.getPlayedCards().size() != currentPlayedCards;
    }

    /**
     * Get the match updated from the server, if still exists (which means if the enemy hasn't quit the match)
     * @throws MissingException
     * @throws ServerOfflineException
     */
    private void refreshMatch() throws MissingException, ServerOfflineException {
        if (getClient().getAllMatches().stream().anyMatch(match -> match.getId() == getLobby().getId())) {
            this.match = getClient().getMatch(getLobby().getId());
        } else {
            enemyQuit();
        }
    }

    /**
     * Update all the table view.
     */
    private void updateTable() {
        // 1. Update users order and hand cards
        updateUsers();

        // 2. Update prese of both users
        updatePrese();

        // 3. Update played card image
        if (match.getPlayedCards().size() == 1) {
            firstPlayedCardImageView.setImage(CardsImages.getImageById(match.getPlayedCards().get(0).getId()));
        } else {
            firstPlayedCardImageView.setImage(null);
        }

        // 4. Update deck image
        if (match.getDeck().size() == 0) {
            deckImageView.setImage(null);
        }

        // 5. Update last card image
        if (match.getLastCard() == null) {
            lastCardImageView.setImage(null);
        }

        // 6. Check if match has a winner
        if (match.getWinner() != null) {
            stopTimeline();
            AlertLauncher.showOkAlert(Alert.AlertType.INFORMATION, "Vincitore: " + match.getWinner().getName());
            matchController.quitMatch();
        }
    }

    /**
     * Swap players order and update user hand cards
     */
    private void updateUsers() {
        if (Objects.equals(match.getFirstPlayer().getName(), getNickname())) {
            user = match.getFirstPlayer();
            enemy = match.getSecondPlayer();
            statusLabel.setText("Tocca a te!");
        } else {
            user = match.getSecondPlayer();
            enemy = match.getFirstPlayer();
            statusLabel.setText("Tocca all'avversario...");
        }

        userHandBox.getChildren().clear();
        userHandCards.clear();
        for (int i = 0; i < user.getHandCards().size(); i++) {
            Cards card = user.getHandCards().get(i);
            ImageView cardImageView = new ImageView(CardsImages.getImageById(card.getId()));
            cardImageView.setFitHeight(200);
            cardImageView.setFitWidth(150);
            userHandCards.put(cardImageView, card);
            userHandBox.getChildren().add(cardImageView);
            cardImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                try {
                    if (checkUserTurn()) {
                        matchController.playCard(card);
                    }
                } catch (MissingException e) {
                    enemyQuit();
                } catch (ServerOfflineException e) {
                    AlertLauncher.showServerOfflineAlert(e.getMessage());
                }
            });
        }
    }

    /**
     * Checks if it is the turn of the user.
     * @return true if it is the turn of the user, else return false
     */
    private boolean checkUserTurn() {
        return (match.getPlayedCards().size() == 0 && Objects.equals(match.getFirstPlayer().getName(), getNickname())) ||
                (match.getPlayedCards().size() == 1 && Objects.equals(match.getSecondPlayer().getName(), getNickname()));
    }

    /**
     * Update prese of both players
     */
    private void updatePrese() {
        preseUserLabel.setText("Prese tue: " + MathUtils.getUserCardsTotalValue(user));
        preseEnemyLabel.setText("Prese avversario: " + MathUtils.getUserCardsTotalValue(enemy));
        updatePresa(preseUserListView, user);
        updatePresa(preseEnemyListView, enemy);
    }

    /**
     * Update prese of a single player
     * @param listView the ListView to update
     * @param user the user where to get prese
     */
    private void updatePresa(ListView<String> listView, User user) {
        listView.getItems().clear();
        for (Cards card : user.getCards()) {
            listView.getItems().add(card.toString());
        }
    }

    /**
     * Enemy has quit the match.
     */
    private void enemyQuit() {
        stopTimeline();
        AlertLauncher.showOkAlert(Alert.AlertType.INFORMATION, "L'avversario ha abbandonato la partita.");
        matchController.quitMatch();
    }

    /**
     * Get user.
     * @return user
     */
    public User getUser() {
        return user;
    }

    /**
     * Quit match button click.
     * @param event
     */
    @FXML
    public void quitMatch(ActionEvent event) {
        stopTimeline();
        try {
            getClient().deleteMatch(getLobby().getId());
        } catch (MissingException e) {
            AlertLauncher.showOkAlert(Alert.AlertType.ERROR, e.getMessage());
        } catch (ServerOfflineException e) {
            AlertLauncher.showServerOfflineAlert(e.getMessage());
        }
        matchController.quitMatch();
    }
}
