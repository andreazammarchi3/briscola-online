package it.zammarchi.briscola.common;

import it.zammarchi.briscola.common.exceptions.ConflictException;
import it.zammarchi.briscola.common.exceptions.MissingException;
import it.zammarchi.briscola.common.exceptions.ServerOfflineException;
import it.zammarchi.briscola.common.model.Cards;
import it.zammarchi.briscola.common.model.Lobby;
import it.zammarchi.briscola.common.model.Match;
import it.zammarchi.briscola.common.model.User;

import java.util.Set;

/**
 * Interface containing all the possible requests in the app. May be implemented for local or remote usage.
 */
public interface Briscola {

    // Lobby methods

    /**
     * Create a new lobby.
     * @return the lobby created
     * @throws ServerOfflineException
     */
    Lobby createLobby() throws ServerOfflineException;

    /**
     * Delete a lobby.
     * @param id the id of the lobby to be deleted
     * @throws MissingException
     * @throws ServerOfflineException
     */
    void deleteLobby(int id) throws MissingException, ServerOfflineException;

    /**
     * Get a lobby by its id.
     * @param id the id of the lobby
     * @return
     * @throws MissingException
     * @throws ServerOfflineException
     */
    Lobby getLobby(int id) throws MissingException, ServerOfflineException;

    /**
     * Get all the lobbies.
     * @return a set containing all the lobbies
     * @throws ServerOfflineException
     */
    Set<? extends Lobby> getAllLobbies() throws ServerOfflineException;

    /**
     * Add a user into a lobby.
     * @param id the id of the lobby
     * @param name the nickname of the user
     * @return the lobby updated
     * @throws MissingException
     * @throws ServerOfflineException
     */
    Lobby joinUserToLobby(int id, String name) throws MissingException, ServerOfflineException;

    /**
     * Remove a user from a lobby.
     * @param id the id of the lobby
     * @param name the nickname of the user
     * @throws MissingException
     * @throws ServerOfflineException
     */
    void deleteUserFromLobby(int id, String name) throws MissingException, ServerOfflineException;

    // User methods

    /**
     * Create a new user.
     * @param name the nickname of the new user
     * @return the user created
     * @throws ConflictException
     * @throws IllegalArgumentException
     * @throws ServerOfflineException
     */
    User createUser(String name) throws ConflictException, IllegalArgumentException, ServerOfflineException;

    /**
     * Delete an existing user.
     * @param name the nickname of the user to be deleted.
     * @throws MissingException
     * @throws ServerOfflineException
     */
    void deleteUser(String name) throws MissingException, ServerOfflineException;

    /**
     * Get a user by its nickname
     * @param name the nickname of the user
     * @return the user found
     * @throws MissingException
     * @throws ServerOfflineException
     */
    User getUser(String name) throws MissingException, ServerOfflineException;

    // Match methods

    /**
     * Gel all the active matches.
     * @return a set containing all the matches
     * @throws ServerOfflineException
     */
    Set<? extends Match> getAllMatches() throws ServerOfflineException;

    /**
     * Start a new match.
     * @param lobby the lobby that wants to start a match
     * @return the match created
     * @throws ConflictException
     * @throws MissingException
     * @throws IllegalArgumentException
     * @throws ServerOfflineException
     */
    Match startMatch(Lobby lobby) throws ConflictException, MissingException, IllegalArgumentException,
            ServerOfflineException;

    /**
     * Get a match by its id.
     * @param id the id of the match
     * @return the match
     * @throws MissingException
     * @throws ServerOfflineException
     */
    Match getMatch(int id) throws MissingException, ServerOfflineException;

    /**
     * Delete an existing match.
     * @param id the id of the match
     * @throws MissingException
     * @throws ServerOfflineException
     */
    void deleteMatch(int id) throws MissingException, ServerOfflineException;

    /**
     * A user in a match plays a card.
     * @param id the id of the match
     * @param user the nickname of the user
     * @param card the card played
     * @return the match updated
     * @throws MissingException
     * @throws ServerOfflineException
     */
    Match userPlaysCard(int id, User user, Cards card) throws MissingException, ServerOfflineException;
}
