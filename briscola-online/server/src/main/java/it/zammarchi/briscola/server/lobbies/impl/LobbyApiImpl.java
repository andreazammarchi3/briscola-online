package it.zammarchi.briscola.server.lobbies.impl;

import io.javalin.http.BadRequestResponse;
import io.javalin.http.NotFoundResponse;
import io.javalin.http.ServiceUnavailableResponse;
import it.zammarchi.briscola.common.Briscola;
import it.zammarchi.briscola.common.exceptions.MissingException;
import it.zammarchi.briscola.common.exceptions.ServerOfflineException;
import it.zammarchi.briscola.common.model.Lobby;
import it.zammarchi.briscola.server.AbstractApi;
import it.zammarchi.briscola.server.lobbies.LobbyApi;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Implementation of the Lobby API.
 */
public class LobbyApiImpl extends AbstractApi implements LobbyApi {

    public LobbyApiImpl(Briscola storage) {
        super(storage);
    }

    @Override
    public CompletableFuture<Collection<? extends Integer>> getAllLobbiesId() {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return getStorage().getAllLobbies().stream()
                                .map(Lobby::getId)
                                .collect(Collectors.toSet());
                    } catch (ServerOfflineException e) {
                        throw new ServiceUnavailableResponse(e.getMessage());
                    }
                }
        );
    }

    @Override
    public CompletableFuture<Lobby> createLobby() {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return getStorage().createLobby();
                    } catch (ServerOfflineException e) {
                        throw new ServiceUnavailableResponse(e.getMessage());
                    }
                }
        );
    }

    @Override
    public CompletableFuture<Lobby> getLobby(int id) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return getStorage().getLobby(id);
                    } catch (MissingException e) {
                        throw new NotFoundResponse(e.getMessage());
                    } catch (ServerOfflineException e) {
                        throw new ServiceUnavailableResponse(e.getMessage());
                    }
                }
        );
    }

    @Override
    public CompletableFuture<Void> deleteLobby(int id) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        getStorage().deleteLobby(id);
                        return null;
                    } catch (MissingException e) {
                        throw new NotFoundResponse(e.getMessage());
                    } catch (ServerOfflineException e) {
                        throw new ServiceUnavailableResponse(e.getMessage());
                    }
                }
        );
    }

    @Override
    public CompletableFuture<Lobby> joinUserToLobby(int id, String name) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return getStorage().joinUserToLobby(id, name);
                    } catch (MissingException e) {
                        throw new NotFoundResponse(e.getMessage());
                    } catch (IllegalArgumentException e) {
                        throw new BadRequestResponse(e.getMessage());
                    } catch (ServerOfflineException e) {
                        throw new ServiceUnavailableResponse(e.getMessage());
                    }
                }
        );
    }

    @Override
    public CompletableFuture<Void> deleteUserFromLobby(int id, String name) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        getStorage().deleteUserFromLobby(id, name);
                        return null;
                    } catch (MissingException e) {
                        throw new NotFoundResponse(e.getMessage());
                    } catch (ServerOfflineException e) {
                        throw new ServiceUnavailableResponse(e.getMessage());
                    }
                }
        );
    }
}
