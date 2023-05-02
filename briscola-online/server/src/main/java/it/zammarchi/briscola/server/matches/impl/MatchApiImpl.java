package it.zammarchi.briscola.server.matches.impl;

import io.javalin.http.BadRequestResponse;
import io.javalin.http.ConflictResponse;
import io.javalin.http.NotFoundResponse;
import io.javalin.http.ServiceUnavailableResponse;
import it.zammarchi.briscola.common.Briscola;
import it.zammarchi.briscola.common.exceptions.ConflictException;
import it.zammarchi.briscola.common.exceptions.MissingException;
import it.zammarchi.briscola.common.exceptions.ServerOfflineException;
import it.zammarchi.briscola.common.model.Cards;
import it.zammarchi.briscola.common.model.Match;
import it.zammarchi.briscola.server.AbstractApi;
import it.zammarchi.briscola.server.matches.MatchApi;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Implementation of the Match API.
 */
public class MatchApiImpl extends AbstractApi implements MatchApi {

    public MatchApiImpl(Briscola storage) {
        super(storage);
    }

    @Override
    public CompletableFuture<Collection<? extends Integer>> getAllMatchesId() {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return getStorage().getAllMatches().stream()
                                .map(Match::getId)
                                .collect(Collectors.toSet());
                    } catch (ServerOfflineException e) {
                        throw new ServiceUnavailableResponse(e.getMessage());
                    }
                }
        );
    }

    @Override
    public CompletableFuture<Match> startMatch(int id) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return getStorage().startMatch(getStorage().getLobby(id));
                    } catch (MissingException e) {
                        throw new NotFoundResponse(e.getMessage());
                    } catch (ConflictException e) {
                        throw new ConflictResponse(e.getMessage());
                    } catch (IllegalArgumentException e) {
                        throw new BadRequestResponse(e.getMessage());
                    } catch (ServerOfflineException e) {
                        throw new ServiceUnavailableResponse(e.getMessage());
                    }
                }
        );
    }

    @Override
    public CompletableFuture<Match> getMatch(int id) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return getStorage().getMatch(id);
                    } catch (MissingException e) {
                        throw new NotFoundResponse(e.getMessage());
                    } catch (ServerOfflineException e) {
                        throw new ServiceUnavailableResponse(e.getMessage());
                    }
                }
        );
    }

    @Override
    public CompletableFuture<Void> deleteMatch(int id) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        getStorage().deleteMatch(id);
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
    public CompletableFuture<Match> userPlaysCard(int matchId, String username, int cardId) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return getStorage().userPlaysCard(matchId, getStorage().getUser(username), Cards.getCardById(cardId));
                    } catch (MissingException e) {
                        throw new NotFoundResponse(e.getMessage());
                    } catch (ServerOfflineException e) {
                        throw new ServiceUnavailableResponse(e.getMessage());
                    }
                }
        );
    }
}
