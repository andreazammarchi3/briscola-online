package it.zammarchi.briscola.server.users.impl;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpResponseException;
import it.zammarchi.briscola.server.AbstractController;
import it.zammarchi.briscola.server.users.UserApi;
import it.zammarchi.briscola.server.users.UserController;
import it.zammarchi.briscola.server.utils.Filters;

/**
 * Implementation of the User controller.
 */
public class UserControllerImpl extends AbstractController implements UserController {
    public UserControllerImpl(String path) {
        super(path);
    }

    private UserApi getApi(Context context) {
        return UserApi.of(getBriscolaInstance(context));
    }

    @Override
    public void POSTCreateUser(Context context) throws HttpResponseException {
        UserApi api = getApi(context);
        var futureResult = api.createUser(context.pathParam("{name}"));
        asyncReplyWithBody(context, "application/json", futureResult);
    }

    @Override
    public void GETUser(Context context) throws HttpResponseException {
        UserApi api = getApi(context);
        var futureResult = api.getUser(context.pathParam("{name}"));
        asyncReplyWithBody(context, "application/json", futureResult);
    }

    @Override
    public void DELETEUser(Context context) throws HttpResponseException {
        UserApi api = getApi(context);
        var futureResult = api.deleteUser(context.pathParam("{name}"));
        asyncReplyWithoutBody(context, "application/json", futureResult);
    }

    @Override
    public void registerRoutes(Javalin app) {
        app.before(path("*"), Filters.ensureClientAcceptsMimeType("application", "json"));
        app.post(path("/{name}"), this::POSTCreateUser);
        app.get(path("/{name}"), this::GETUser);
        app.delete(path("/{name}"), this::DELETEUser);
    }
}
