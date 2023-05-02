package it.zammarchi.briscola.server;

import io.javalin.Javalin;
import it.zammarchi.briscola.common.Briscola;
import it.zammarchi.briscola.common.LocalBriscola;
import it.zammarchi.briscola.common.utils.GsonUtils;
import it.zammarchi.briscola.server.lobbies.LobbyController;
import it.zammarchi.briscola.server.matches.MatchController;
import it.zammarchi.briscola.server.users.UserController;
import it.zammarchi.briscola.server.utils.Filters;
import it.zammarchi.briscola.server.utils.JavalinGsonAdapter;
import it.zammarchi.briscola.server.utils.Plugins;

/**
 * Web Service implemented with Javalin.
 */
public class BriscolaService {
    private static final String API_VERSION = "0.1.0";
    public static final String BASE_URL = "/briscola/v" + API_VERSION;
    private final int port;
    private final Javalin server;

    public BriscolaService(int port) {
        this.port = port;
        server = Javalin.create(config -> {
            config.plugins.enableDevLogging();
            config.jsonMapper(new JavalinGsonAdapter(GsonUtils.createGson()));
            config.plugins.register(Plugins.openApiPlugin(API_VERSION, "/doc"));
            config.plugins.register(Plugins.swaggerPlugin("/doc", "/ui"));
            config.plugins.register(Plugins.routeOverviewPlugin("/routes"));
        });

        Briscola localBriscola = new LocalBriscola();
        server.before(path("/*"), Filters.putSingletonInContext(Briscola.class, localBriscola));

        LobbyController.of(path("/lobbies")).registerRoutes(server);
        UserController.of(path("/users")).registerRoutes(server);
        MatchController.of(path("/matches")).registerRoutes(server);
    }

    /**
     * Start the service.
     */
    public void startService() {
        server.start(port);
    }

    /**
     * Stop the service.
     */
    public void stopService() {
        server.stop();
    }

    private static String path(String subPath) {
        return BASE_URL + subPath;
    }
}
