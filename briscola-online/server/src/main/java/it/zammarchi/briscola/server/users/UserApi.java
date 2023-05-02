package it.zammarchi.briscola.server.users;

import it.zammarchi.briscola.common.Briscola;
import it.zammarchi.briscola.common.model.User;
import it.zammarchi.briscola.server.users.impl.UserApiImpl;

import java.util.concurrent.CompletableFuture;

/**
 * Interface of the API for the User resource.
 */
public interface UserApi {

    /**
     * Create a new user.
     * @param name the nickname of the user
     * @return the user created
     */
    CompletableFuture<User> createUser(String name);

    /**
     * Get a user.
     * @param name the nickname of the user
     * @return the user
     */
    CompletableFuture<User> getUser(String name);

    /**
     * Delete an existing user.
     * @param name the nickname of the user
     * @return void
     */
    CompletableFuture<Void> deleteUser(String name);

    static UserApi of(Briscola storage) {
        return new UserApiImpl(storage);
    }
}
