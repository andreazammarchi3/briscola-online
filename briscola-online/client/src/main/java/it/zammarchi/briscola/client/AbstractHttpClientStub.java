package it.zammarchi.briscola.client;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import it.zammarchi.briscola.common.exceptions.ConflictException;
import it.zammarchi.briscola.common.exceptions.MissingException;
import it.zammarchi.briscola.common.exceptions.ServerOfflineException;
import it.zammarchi.briscola.common.utils.GsonUtils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.function.Function;

/**
 * Abstract class containing base methods for sending HTTP requests.
 */
public class AbstractHttpClientStub {
    private static final HttpResponse.BodyHandler<String> BODY_TO_STRING = HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8);
    private final HttpClient client = HttpClient.newHttpClient();
    private final URI serviceURI;
    private final Gson gson = GsonUtils.createGson();

    public AbstractHttpClientStub(URI host, String root, String version) {
        Objects.requireNonNull(host);
        this.serviceURI = host.resolve(String.format("/%s/v%s", root, version));
    }

    /**
     * Get the URI of a resource.
     * @param resource the resource
     * @return the URI of the resource
     */
    protected URI resourceUri(String resource) {
        return serviceURI.resolve(serviceURI.getPath() + resource);
    }

    /**
     * Serialize into JSON a Java object.
     * @param object the object to be serialized
     * @return the serialized JSON object
     * @param <T> the typer of the object to be serialized
     */
    protected <T> String serialize(T object) {
        return gson.toJson(object);
    }

    /**
     * Deserialize a JSON object.
     * @param type the type of the object
     * @return a function mapping the JSON object to the object deserialized
     * @param <T> the type of the object
     */
    protected <T> Function<String, CompletableFuture<T>> deserializeOne(Class<T> type) {
        return toBeDeserialized -> {
            CompletableFuture<T> promise = new CompletableFuture<>();
            try {
                promise.complete(gson.fromJson(toBeDeserialized, type));
            } catch (JsonParseException e) {
                promise.completeExceptionally(e);
            }
            return promise;
        };
    }

    /**
     * Deserialize many JSON objects of the same type.
     * @param type the type of the objects
     * @return a function mapping the JSON objects to the objects deserialized
     * @param <T> the typer of the objects
     */
    protected <T> Function<String, CompletableFuture<List<T>>> deserializeMany(Class<T> type) {
        return toBeDeserialized -> {
            CompletableFuture<List<T>> promise = new CompletableFuture<>();
            try {
                JsonElement jsonElement = gson.fromJson(toBeDeserialized, JsonElement.class);
                if (jsonElement.isJsonArray()) {
                    JsonArray jsonArray = jsonElement.getAsJsonArray();
                    List<T> items = new ArrayList<>(jsonArray.size());
                    for (JsonElement item : jsonArray) {
                        items.add(gson.fromJson(item, type));
                    }
                    promise.complete(items);
                } else {
                    promise.completeExceptionally(new IllegalStateException("Cannot deserialize: " + toBeDeserialized));
                }
            } catch (JsonParseException e) {
                promise.completeExceptionally(new IllegalStateException(e));
            }
            return promise;
        };
    }

    /**
     * Send and asynchronous request to the client.
     * @param request the HTTP request
     * @return the HTTP response
     */
    protected CompletableFuture<HttpResponse<String>> sendRequestToClient(HttpRequest request) {
        return client.sendAsync(request, BODY_TO_STRING);
    }

    /**
     * The body of the request, with an object
     * @param object the object
     * @return BodyPublisher
     */
    protected HttpRequest.BodyPublisher body(Object object) {
        return HttpRequest.BodyPublishers.ofString(serialize(object), StandardCharsets.UTF_8);
    }

    /**
     * The body of the request, empty
     * @return BodyPublisher
     */
    protected HttpRequest.BodyPublisher body() {
        return HttpRequest.BodyPublishers.noBody();
    }

    /**
     * Check the response obtained.
     * @return a function mapping the HTTP response to a String
     */
    protected Function<HttpResponse<String>, CompletableFuture<String>> checkResponse() {
        return response -> {
            if (response.statusCode() == 200) {
                return CompletableFuture.completedFuture(response.body());
            } else if (response.statusCode() == 400) { // bad content
                return CompletableFuture.failedFuture(new IllegalArgumentException(response.body()));
            } else if (response.statusCode() == 404) { // not found
                return CompletableFuture.failedFuture(new MissingException(response.body()));
            } else if (response.statusCode() == 409) { // conflict
                return CompletableFuture.failedFuture(new ConflictException(response.body()));
            } else if (response.statusCode() == 503) { // server offline
                return CompletableFuture.failedFuture(new ServerOfflineException(response.body()));
            } else {
                return CompletableFuture.failedFuture(
                        new IllegalStateException(
                                String.format(
                                        "Unexpected response while %s %s: %d",
                                        response.request().method(),
                                        response.uri(),
                                        response.statusCode()
                                )
                        )
                );
            }
        };
    }

    /**
     * Get the cause of an exception in a specific type
     * @param e the exception
     * @param type the type wanted
     * @return the exception with the wanted cause
     * @param <E> the type of the exception
     */
    protected <E extends Exception> E getCauseAs(CompletionException e, Class<E> type) {
        if (type.isAssignableFrom(e.getCause().getClass())) {
            return type.cast(e.getCause());
        } else if (e.getCause() instanceof RuntimeException) {
            throw (RuntimeException) e.getCause();
        } else {
            throw e;
        }
    }
}
