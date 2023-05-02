package it.zammarchi.briscola.server.users;

import io.javalin.http.Context;
import io.javalin.http.HttpResponseException;
import io.javalin.openapi.*;
import it.zammarchi.briscola.common.model.User;
import it.zammarchi.briscola.server.BriscolaService;
import it.zammarchi.briscola.server.Controller;
import it.zammarchi.briscola.server.users.impl.UserControllerImpl;

/**
 * Interface of the controller for the User resource. Contains all the detailed requests.
 */
public interface UserController extends Controller {
    @OpenApi(
            operationId = "UserApi::createUser",
            path = BriscolaService.BASE_URL + "/users/{name}",
            methods = {HttpMethod.POST},
            tags = {"users"},
            description = "Create a new user",
            pathParams = {
                    @OpenApiParam(
                            name = "name",
                            description = "The username of the user whose data is being requested",
                            required = true
                    )
            },
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            description = "User created successfully",
                            content = {
                                    @OpenApiContent(
                                            from = User.class,
                                            mimeType = ContentType.JSON
                                    )
                            }
                    ),
                    @OpenApiResponse(
                            status = "400",
                            description = "Bad request: some field is missing or invalid in the provided username"
                    ),
                    @OpenApiResponse(
                            status = "409",
                            description = "Conflict: username provided has already been taken"
                    ),
                    @OpenApiResponse(
                            status = "503",
                            description = "Server offline: the server is currently unavailable"
                    )
            }
    )
    void POSTCreateUser(Context context) throws HttpResponseException;

    @OpenApi(
            operationId = "UserApi::getUser",
            path = BriscolaService.BASE_URL + "/users/{name}",
            methods = {HttpMethod.GET},
            tags = {"users"},
            description = "Gets the data of a user, given its name",
            pathParams = {
                    @OpenApiParam(
                            name = "name",
                            description = "The username of the user whose data is being requested",
                            required = true
                    )
            },
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            description = "The provided username corresponds to a user, whose data is thus returned",
                            content = {
                                    @OpenApiContent(
                                            from = User.class,
                                            mimeType = ContentType.JSON
                                    )
                            }
                    ),
                    @OpenApiResponse(
                            status = "400",
                            description = "Bad request: some field is missing or invalid in the provided username"
                    ),
                    @OpenApiResponse(
                            status = "404",
                            description = "Not found: the provided username corresponds to no known user"
                    ),
                    @OpenApiResponse(
                            status = "503",
                            description = "Server offline: the server is currently unavailable"
                    )
            }
    )
    void GETUser(Context context) throws HttpResponseException;

    @OpenApi(
            operationId = "UserApi::deleteUser",
            path = BriscolaService.BASE_URL + "/users/{name}",
            methods = {HttpMethod.DELETE},
            tags = {"users"},
            description = "Deletes a user, given its username",
            pathParams = {
                    @OpenApiParam(
                            name = "name",
                            description = "Username of the user whose data is being requested",
                            required = true
                    )
            },
            responses = {
                    @OpenApiResponse(
                            status = "201",
                            description = "The provided username corresponds to a user, which is thus removed. Nothing is returned"
                    ),
                    @OpenApiResponse(
                            status = "400",
                            description = "Bad request: some field is missing or invalid in the provided username"
                    ),
                    @OpenApiResponse(
                            status = "404",
                            description = "Not found: the provided username corresponds to no known user"
                    ),
                    @OpenApiResponse(
                            status = "503",
                            description = "Server offline: the server is currently unavailable"
                    )
            }
    )
    void DELETEUser(Context context) throws HttpResponseException;

    static UserController of(String root) {
        return new UserControllerImpl(root);
    }
}
