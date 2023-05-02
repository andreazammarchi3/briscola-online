package it.zammarchi.briscola.server.matches;

import io.javalin.http.Context;
import io.javalin.http.HttpResponseException;
import io.javalin.openapi.*;
import it.zammarchi.briscola.common.model.Match;
import it.zammarchi.briscola.server.BriscolaService;
import it.zammarchi.briscola.server.Controller;
import it.zammarchi.briscola.server.matches.impl.MatchControllerImpl;

/**
 * Interface of the controller for the Match resource. Contains all the detailed requests.
 */
public interface MatchController extends Controller {
    @OpenApi(
            operationId = "MatchApi::getAllMatchesId",
            path = BriscolaService.BASE_URL + "/matches",
            methods = {HttpMethod.GET},
            tags = {"matches"},
            description = "Retrieves all matches' ids",
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            description = "An array containing the matches' ids",
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
    void GETAllMatchesId(Context context) throws HttpResponseException;

    @OpenApi(
            operationId = "MatchApi::startMatch",
            path = BriscolaService.BASE_URL + "/matches/{id}",
            methods = {HttpMethod.POST},
            tags = {"matches"},
            description = "Start a new match",
            pathParams = {
                    @OpenApiParam(
                            name = "id",
                            description = "The lobby id",
                            type = Integer.class,
                            required = true
                    )
            },
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            description = "Match started successfully",
                            content = {
                                    @OpenApiContent(
                                            from = Match.class,
                                            mimeType = ContentType.JSON
                                    )
                            }
                    ),
                    @OpenApiResponse(
                            status = "400",
                            description = "Bad request: lobby is not full"
                    ),
                    @OpenApiResponse(
                            status = "404",
                            description = "Not found: the provided id corresponds to no known matches"
                    ),
                    @OpenApiResponse(
                            status = "409",
                            description = "Conflict: a match with given lobby's id already exists"
                    ),
                    @OpenApiResponse(
                            status = "503",
                            description = "Server offline: the server is currently unavailable"
                    )
            }
    )
    void POSTStartMatch(Context context) throws HttpResponseException;

    @OpenApi(
            operationId = "MatchApi::getMatch",
            path = BriscolaService.BASE_URL + "/matches/{id}",
            methods = {HttpMethod.GET},
            tags = {"matches"},
            description = "Gets the data of a match, given its lobby's id",
            pathParams = {
                    @OpenApiParam(
                            name = "id",
                            type = Integer.class,
                            description = "The lobby id of the match which data is being requested",
                            required = true
                    )
            },
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            description = "The provided id corresponds to a match, whose data is thus returned",
                            content = {
                                    @OpenApiContent(
                                            from = Match.class,
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
                            description = "Not found: the provided id corresponds to no known matches"
                    ),
                    @OpenApiResponse(
                            status = "503",
                            description = "Server offline: the server is currently unavailable"
                    )
            }
    )
    void GETMatch(Context context) throws HttpResponseException;

    @OpenApi(
            operationId = "MatchApi::deleteMatch",
            path = BriscolaService.BASE_URL + "/matches/{id}",
            methods = {HttpMethod.DELETE},
            tags = {"matches"},
            description = "Deletes a match, given its lobby's id",
            pathParams = {
                    @OpenApiParam(
                            name = "id",
                            type = Integer.class,
                            description = "The lobby id of the match, whose data is being requested",
                            required = true
                    )
            },
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            description = "The provided id corresponds to a match, which is thus removed. Nothing is returned"
                    ),
                    @OpenApiResponse(
                            status = "400",
                            description = "Bad request"
                    ),
                    @OpenApiResponse(
                            status = "404",
                            description = "Not found: the provided id corresponds to no known matches"
                    ),
                    @OpenApiResponse(
                            status = "503",
                            description = "Server offline: the server is currently unavailable"
                    )
            }
    )
    void DELETEMatch(Context context) throws HttpResponseException;

    @OpenApi(
            operationId = "MatchApi::userPlaysCard",
            path = BriscolaService.BASE_URL + "/matches/{matchId}/{name}/{cardId}",
            methods = {HttpMethod.POST},
            tags = {"matches"},
            description = "User plays a card",
            pathParams = {
                    @OpenApiParam(
                            name = "matchId",
                            type = Integer.class,
                            description = "The match id",
                            required = true
                    ),
                    @OpenApiParam(
                            name = "name",
                            description = "The username",
                            required = true
                    ),
                    @OpenApiParam(
                            name = "cardId",
                            description = "The card id",
                            required = true
                    ),
            },
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            description = "Card successfully played. Updated match is returned",
                            content = {
                                    @OpenApiContent(
                                            from = Match.class,
                                            mimeType = ContentType.JSON
                                    )
                            }
                    ),
                    @OpenApiResponse(
                            status = "404",
                            description = "Not found: the provided match id or username id was not found"
                    ),
                    @OpenApiResponse(
                            status = "503",
                            description = "Server offline: the server is currently unavailable"
                    )
            }
    )
    void POSTUserPlaysCard(Context context) throws HttpResponseException;

    static MatchController of(String root) {
        return new MatchControllerImpl(root);
    }
}
