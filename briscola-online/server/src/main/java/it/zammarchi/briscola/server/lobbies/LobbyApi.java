package it.zammarchi.briscola.server.lobbies;

import it.zammarchi.briscola.common.Briscola;
import it.zammarchi.briscola.common.model.Lobby;
import it.zammarchi.briscola.server.lobbies.impl.LobbyApiImpl;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

/**
 * Interface of the API for the Lobby resource.
 */
public interface LobbyApi {
    /**
     * Get all the ids of the lobbies.
     * @return a collection of all the ids
     */
    CompletableFuture<Collection<? extends Integer>> getAllLobbiesId();

    /**
     * Create a new lobby.
     * @return the lobby created
     */
    CompletableFuture<Lobby> createLobby();

    /**
     * Get a lobby by its id.
     * @param id the id of the lobby
     * @return the lobby
     */
    CompletableFuture<Lobby> getLobby(int id);

    /**
     * Delete a lobby by its id.
     * @param id the id of the lobby.
     * @return void
     */
    CompletableFuture<Void> deleteLobby(int id);

    /**
     * Add a user to a lobby.
     * @param id the id of the lobby
     * @param name the nickname of the user
     * @return the lobby updated
     */
    CompletableFuture<Lobby> joinUserToLobby(int id, String name);

    /**
     * Remove a user from a lobby.
     * @param id the id of the lobby
     * @param name the nickname of the user
     * @return void
     */
    CompletableFuture<Void> deleteUserFromLobby(int id, String name);

    static LobbyApi of(Briscola storage) {
        return new LobbyApiImpl(storage);
    }
}
