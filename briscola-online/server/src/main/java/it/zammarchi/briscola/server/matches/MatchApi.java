package it.zammarchi.briscola.server.matches;

import it.zammarchi.briscola.common.Briscola;
import it.zammarchi.briscola.common.model.Lobby;
import it.zammarchi.briscola.common.model.Match;
import it.zammarchi.briscola.server.matches.impl.MatchApiImpl;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

/**
 * Interface of the API for the Match resource.
 */
public interface MatchApi {

    /**
     * Get all the ids of the matches.
     * @return a collection of all the ids
     */
    CompletableFuture<Collection<? extends Integer>> getAllMatchesId();

    /**
     * Start a new match.
     * @param id the id of the lobby starting a new match
     * @return the match created
     */
    CompletableFuture<Match> startMatch(int id);

    /**
     * Get a match.
     * @param id the id of the match
     * @return the match
     */
    CompletableFuture<Match> getMatch(int id);

    /**
     * Delete an existing match.
     * @param id the id of the match
     * @return void
     */
    CompletableFuture<Void> deleteMatch(int id);

    /**
     * A user in a match plays a card.
     * @param matchId the id of the match
     * @param username the nickname of the user
     * @param cardId the id of the card played
     * @return the match updated
     */
    CompletableFuture<Match> userPlaysCard(int matchId, String username, int cardId);

    static MatchApi of(Briscola storage) {
        return new MatchApiImpl(storage);
    }
}
