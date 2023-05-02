package it.zammarchi.briscola.server.users.impl;

import io.javalin.http.BadRequestResponse;
import io.javalin.http.ConflictResponse;
import io.javalin.http.NotFoundResponse;
import io.javalin.http.ServiceUnavailableResponse;
import it.zammarchi.briscola.common.Briscola;
import it.zammarchi.briscola.common.exceptions.ConflictException;
import it.zammarchi.briscola.common.exceptions.MissingException;
import it.zammarchi.briscola.common.exceptions.ServerOfflineException;
import it.zammarchi.briscola.common.model.User;
import it.zammarchi.briscola.server.AbstractApi;
import it.zammarchi.briscola.server.users.UserApi;

import java.util.concurrent.CompletableFuture;

/**
 * Implementation of the User API.
 */
public class UserApiImpl extends AbstractApi implements UserApi {
    public UserApiImpl(Briscola storage) {
        super(storage);
    }

    @Override
    public CompletableFuture<User> createUser(String name) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return getStorage().createUser(name);
                    } catch (ConflictException e) {
                        throw new ConflictResponse(e.getMessage());
                    } catch (IllegalArgumentException e) {
                        throw new BadRequestResponse(e.getMessage());
                    } catch (ServerOfflineException e) {
                        throw  new ServiceUnavailableResponse(e.getMessage());
                    }
                }
        );
    }

    @Override
    public CompletableFuture<User> getUser(String name) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return getStorage().getUser(name);
                    } catch (MissingException e) {
                        throw new NotFoundResponse(e.getMessage());
                    } catch (ServerOfflineException e) {
                        throw new ServiceUnavailableResponse(e.getMessage());
                    }
                }
        );
    }

    @Override
    public CompletableFuture<Void> deleteUser(String name) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        getStorage().deleteUser(name);
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
