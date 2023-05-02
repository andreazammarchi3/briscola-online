package it.zammarchi.briscola.server.matches.impl;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpResponseException;
import it.zammarchi.briscola.server.AbstractController;
import it.zammarchi.briscola.server.matches.MatchApi;
import it.zammarchi.briscola.server.matches.MatchController;
import it.zammarchi.briscola.server.utils.Filters;

/**
 * Implementation of the Match controller.
 */
public class MatchControllerImpl extends AbstractController implements MatchController {

    public MatchControllerImpl(String path) {
        super(path);
    }

    private MatchApi getApi(Context context) {
        return MatchApi.of(getBriscolaInstance(context));
    }

    @Override
    public void GETAllMatchesId(Context context) throws HttpResponseException {
        MatchApi api = getApi(context);
        var futureResult = api.getAllMatchesId();
        asyncReplyWithBody(context, "application/json", futureResult);
    }

    @Override
    public void POSTStartMatch(Context context) throws HttpResponseException {
        MatchApi api = getApi(context);
        var futureResult = api.startMatch(Integer.parseInt(context.pathParam("{id}")));
        asyncReplyWithBody(context, "application/json", futureResult);
    }

    @Override
    public void GETMatch(Context context) throws HttpResponseException {
        MatchApi api = getApi(context);
        var futureResult = api.getMatch(Integer.parseInt(context.pathParam("{id}")));
        asyncReplyWithBody(context, "application/json", futureResult);
    }

    @Override
    public void DELETEMatch(Context context) throws HttpResponseException {
        MatchApi api = getApi(context);
        var futureResult = api.deleteMatch(Integer.parseInt(context.pathParam("{id}")));
        asyncReplyWithoutBody(context, "application/json", futureResult);
    }

    @Override
    public void POSTUserPlaysCard(Context context) throws HttpResponseException {
        MatchApi api = getApi(context);
        var futureResult = api.userPlaysCard(
                Integer.parseInt(context.pathParam("{matchId}")),
                context.pathParam("{name}"),
                Integer.parseInt(context.pathParam("{cardId}"))
        );
        asyncReplyWithBody(context, "application/json", futureResult);
    }


    @Override
    public void registerRoutes(Javalin app) {
        app.before(path("*"), Filters.ensureClientAcceptsMimeType("application", "json"));
        app.get(path("/"), this::GETAllMatchesId);
        app.post(path("/{id}"), this::POSTStartMatch);
        app.get(path("/{id}"), this::GETMatch);
        app.delete(path("/{id}"), this::DELETEMatch);
        app.post(path("/{matchId}/{name}/{cardId}"), this::POSTUserPlaysCard);
    }
}
