package it.zammarchi.briscola.server;

import io.javalin.Javalin;

/**
 * Interface of the resources controllers.
 */
public interface Controller {

    /**
     * Get the path of the controller.
     * @return the path of the controller
     */
    String path();

    /**
     * Get the path of a request.
     * @param subPath the sub-path of the request
     * @return the path requested
     */
    String path(String subPath);

    /**
     * Register all the sub-paths of the controller.
     * @param app the Javalin app where to register the paths
     */
    void registerRoutes(Javalin app);
}
