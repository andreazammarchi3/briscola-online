package it.zammarchi.briscola.server.lobbies;

import io.javalin.http.Context;
import io.javalin.http.HttpResponseException;
import io.javalin.openapi.*;
import it.zammarchi.briscola.common.model.Lobby;
import it.zammarchi.briscola.server.BriscolaService;
import it.zammarchi.briscola.server.Controller;
import it.zammarchi.briscola.server.lobbies.impl.LobbyControllerImpl;

/**
 * Interface of the controller for the Lobby resource. Contains all the detailed requests.
 */
public interface LobbyController extends Controller {
    @OpenApi(
            operationId = "LobbyApi::getAllLobbiesId",
            path = BriscolaService.BASE_URL + "/lobbies",
            methods = {HttpMethod.GET},
            tags = {"lobbies"},
            description = "Retrieves all lobbies' ids",
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            description = "An array containing the lobbies' ids",
                            content = {
                                    @OpenApiContent(
                                            from = Integer[].class,
                                            mimeType = ContentType.JSON
                                    )
                            }
                    ),
                    @OpenApiResponse(
                            status = "400",
                            description = "Bad request"
                    ),
                    @OpenApiResponse(
                            status = "503",
                            description = "Server offline: the server is currently unavailable"
                    )
            }
    )
    void GETAllLobbiesId(Context context) throws HttpResponseException;

    @OpenApi(
            operationId = "LobbyApi::createLobby",
            path = BriscolaService.BASE_URL + "/lobbies",
            methods = {HttpMethod.POST},
            tags = {"lobbies"},
            description = "Create a new lobby",
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            description = "The newly created lobby",
                            content = {
                                    @OpenApiContent(
                                            from = Lobby.class,
                                            mimeType = ContentType.JSON
                                    )
                            }
                    ),
                    @OpenApiResponse(
                            status = "400",
                            description = "Bad request"
                    ),
                    @OpenApiResponse(
                            status = "503",
                            description = "Server offline: the server is currently unavailable"
                    )
            }
    )
    void POSTCreateLobby(Context context) throws HttpResponseException;

    @OpenApi(
            operationId = "LobbyApi::getLobby",
            path = BriscolaService.BASE_URL + "/lobbies/{id}",
            methods = {HttpMethod.GET},
            tags = {"lobbies"},
            description = "Gets the data of a lobby, given its id",
            pathParams = {
                    @OpenApiParam(
                            name = "id",
                            type = Integer.class,
                            description = "The lobby id",
                            required = true
                    )
            },
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            description = "The provided id corresponds to a lobby, whose data is thus returned",
                            content = {
                                    @OpenApiContent(
                                            from = Lobby.class,
                                            mimeType = ContentType.JSON
                                    )
                            }
                    ),
                    @OpenApiResponse(
                            status = "400",
                            description = "Bad request"
                    ),
                    @OpenApiResponse(
                            status = "404",
                            description = "Not found: the provided id corresponds to no known lobby"
                    ),
                    @OpenApiResponse(
                            status = "503",
                            description = "Server offline: the server is currently unavailable"
                    )
            }
    )
    void GETLobby(Context context) throws HttpResponseException;

    @OpenApi(
            operationId = "LobbyApi::deleteLobby",
            path = BriscolaService.BASE_URL + "/lobbies/{id}",
            methods = {HttpMethod.DELETE},
            tags = {"lobbies"},
            description = "Delete a lobby, given the id",
            pathParams = {
                    @OpenApiParam(
                            name = "id",
                            type = Integer.class,
                            description = "The lobby id",
                            required = true
                    )
            },
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            description = "The provided id corresponds to a lobby, which is thus removed. " +
                                    "Nothing is returned"
                    ),
                    @OpenApiResponse(
                            status = "400",
                            description = "Bad request"
                    ),
                    @OpenApiResponse(
                            status = "404",
                            description = "Not found: the provided id corresponds to no known lobby"
                    ),
                    @OpenApiResponse(
                            status = "503",
                            description = "Server offline: the server is currently unavailable"
                    )
            }
    )
    void DELETELobby(Context context) throws HttpResponseException;

    @OpenApi(
            operationId = "LobbyApi::joinUserToLobby",
            path = BriscolaService.BASE_URL + "/lobbies/{id}/{name}",
            methods = {HttpMethod.PUT},
            tags = {"lobbies"},
            description = "Join a user to a lobby",
            pathParams = {
                    @OpenApiParam(
                            name = "id",
                            type = Integer.class,
                            description = "The lobby id",
                            required = true
                    ),
                    @OpenApiParam(
                            name = "name",
                            description = "The username",
                            required = true
                    ),
            },
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            description = "User successfully joined to lobby",
                            content = {
                                    @OpenApiContent(
                                            from = Lobby.class,
                                            mimeType = ContentType.JSON
                                    )
                            }
                    ),
                    @OpenApiResponse(
                            status = "400",
                            description = "Bad request"
                    ),
                    @OpenApiResponse(
                            status = "404",
                            description = "Not found: " +
                                    "the provided user or lobby corresponds to no known existing user or lobby"
                    ),
                    @OpenApiResponse(
                            status = "503",
                            description = "Server offline: the server is currently unavailable"
                    )
            }
    )
    void PUTJoinUserToLobby(Context context) throws HttpResponseException;

    @OpenApi(
            operationId = "LobbyApi::deleteUserFromLobby",
            path = BriscolaService.BASE_URL + "/lobbies/{id}/{name}",
            methods = {HttpMethod.DELETE},
            tags = {"lobbies"},
            description = "Remove a user from a lobby, given the id of the lobby and the username",
            pathParams = {
                    @OpenApiParam(
                            name = "id",
                            type = Integer.class,
                            description = "The lobby id",
                            required = true
                    ),
                    @OpenApiParam(
                            name = "name",
                            description = "The username",
                            required = true
                    ),
            },
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            description = "User successfully removed from lobby"
                    ),
                    @OpenApiResponse(
                            status = "400",
                            description = "Bad request"
                    ),
                    @OpenApiResponse(
                            status = "404",
                            description = "Not found: no user with this username found in this lobby"
                    ),
                    @OpenApiResponse(
                            status = "503",
                            description = "Server offline: the server is currently unavailable"
                    )
            }
    )
    void DELETEUserFromLobby(Context context) throws HttpResponseException;

    static LobbyController of(String root) {
        return new LobbyControllerImpl(root);
    }
}
