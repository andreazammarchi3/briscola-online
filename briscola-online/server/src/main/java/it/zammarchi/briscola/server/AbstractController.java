package it.zammarchi.briscola.server;

import io.javalin.http.Context;
import it.zammarchi.briscola.common.Briscola;
import it.zammarchi.briscola.server.utils.Filters;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Abstract class defining the base methods of each resource's controller.
 */
public abstract class AbstractController implements Controller{
    private final String path;
    
    public AbstractController(String path) {
        this.path = path;
    }

    /**
     * Get the local briscola instance.
     * @param context the context
     * @return the briscola instance
     */
    protected Briscola getBriscolaInstance(Context context) {
        return Filters.getSingletonFromContext(Briscola.class, context);
    }

    public String path() {
        return path;
    }

    public String path(String subPath) {
        return path() + subPath;
    }

    /**
     * Response, with a body, of a request.
     * @param ctx the context
     * @param contentType the content type of the response
     * @param futureResult the future result
     * @param <T> the type of the future result
     */
    protected <T> void asyncReplyWithBody(Context ctx, String contentType, CompletableFuture<T> futureResult) {
        ctx.contentType(contentType);
        ctx.future(() -> futureResult.thenAccept(ctx::json));
    }

    /**
     * Response, without a body, of a request
     * @param ctx the context
     * @param contentType the content typer of the response
     * @param futureResult the future result
     */
    protected void asyncReplyWithoutBody(Context ctx, String contentType, CompletableFuture<?> futureResult) {
        ctx.contentType(contentType);
        ctx.future(() -> futureResult);
    }
}
