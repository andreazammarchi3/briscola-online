package it.zammarchi.briscola.server.lobbies.impl;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpResponseException;
import it.zammarchi.briscola.server.AbstractController;
import it.zammarchi.briscola.server.lobbies.LobbyApi;
import it.zammarchi.briscola.server.lobbies.LobbyController;
import it.zammarchi.briscola.server.utils.Filters;

/**
 * Implementation of the Lobby controller.
 */
public class LobbyControllerImpl extends AbstractController implements LobbyController {
    public LobbyControllerImpl(String path) {
        super(path);
    }

    private LobbyApi getApi(Context context) {
        return LobbyApi.of(getBriscolaInstance(context));
    }

    @Override
    public void GETAllLobbiesId(Context context) throws HttpResponseException {
        LobbyApi api = getApi(context);
        var futureResult = api.getAllLobbiesId();
        asyncReplyWithBody(context, "application/json", futureResult);
    }

    @Override
    public void POSTCreateLobby(Context context) throws HttpResponseException {
        LobbyApi api = getApi(context);
        var futureResult = api.createLobby();
        asyncReplyWithBody(context, "application/json", futureResult);
    }

    @Override
    public void GETLobby(Context context) throws HttpResponseException {
        LobbyApi api = getApi(context);
        var futureResult = api.getLobby(Integer.parseInt(context.pathParam("{id}")));
        asyncReplyWithBody(context, "application/json", futureResult);
    }

    @Override
    public void DELETELobby(Context context) throws HttpResponseException {
        LobbyApi api = getApi(context);
        var futureResult = api.deleteLobby(Integer.parseInt(context.pathParam("{id}")));
        asyncReplyWithoutBody(context, "application/json", futureResult);
    }

    @Override
    public void PUTJoinUserToLobby(Context context) throws HttpResponseException {
        LobbyApi api = getApi(context);
        var futureResult = api.joinUserToLobby(Integer.parseInt(context.pathParam("{id}")),
                context.pathParam("{name}"));
        asyncReplyWithBody(context, "application/json", futureResult);
    }

    @Override
    public void DELETEUserFromLobby(Context context) throws HttpResponseException {
        LobbyApi api = getApi(context);
        var futureResult = api.deleteUserFromLobby(Integer.parseInt(context.pathParam("{id}")),
                context.pathParam("{name}"));
        asyncReplyWithoutBody(context, "application/json", futureResult);
    }

    @Override
    public void registerRoutes(Javalin app) {
        app.before(path("*"), Filters.ensureClientAcceptsMimeType("application", "json"));
        app.get(path("/"), this::GETAllLobbiesId);
        app.post(path("/"), this::POSTCreateLobby);
        app.get(path("/{id}"), this::GETLobby);
        app.delete(path("/{id}"), this::DELETELobby);
        app.put(path("/{id}/{name}"), this::PUTJoinUserToLobby);
        app.delete(path("/{id}/{name}"), this::DELETEUserFromLobby);
    }
}
