package it.zammarchi.briscola.client;

import it.zammarchi.briscola.common.Briscola;
import it.zammarchi.briscola.common.exceptions.ConflictException;
import it.zammarchi.briscola.common.exceptions.MissingException;
import it.zammarchi.briscola.common.exceptions.ServerOfflineException;
import it.zammarchi.briscola.common.model.Cards;
import it.zammarchi.briscola.common.model.Lobby;
import it.zammarchi.briscola.common.model.Match;
import it.zammarchi.briscola.common.model.User;

import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * Remote implementation of the Briscola Interface. Contains all the methods to send HTTP requests to the server.
 */
public class RemoteBriscola extends AbstractHttpClientStub implements Briscola {
    public RemoteBriscola(URI host) {
        super(host, "briscola", "0.1.0");
    }

    public RemoteBriscola(String host, int port) {
        this(URI.create("http://" + host + ":" + port));
    }

    // Lobby methods
    private CompletableFuture<Lobby> createLobbyAsync() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/lobbies"))
                .header("Accept", "application/json")
                .POST(body())
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeOne(Lobby.class));
    }

    @Override
    public Lobby createLobby() throws ServerOfflineException {
        try {
            return createLobbyAsync().join();
        } catch (CompletionException e) {
            throw new ServerOfflineException(e);
        }
    }

    private CompletableFuture<?> deleteLobbyAsync(int id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/lobbies/" + id))
                .DELETE()
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeOne(Void.class));
    }

    @Override
    public void deleteLobby(int id) throws MissingException, ServerOfflineException {
        try {
            deleteLobbyAsync(id).join();
        } catch (CompletionException e) {
            if (e.getCause().getClass() == MissingException.class) {
                throw getCauseAs(e, MissingException.class);
            } else {
                throw new ServerOfflineException(e);
            }
        }
    }

    private CompletableFuture<Lobby> getLobbyAsync(int id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/lobbies/" + id))
                .header("Accept", "application/json")
                .GET()
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeOne(Lobby.class));
    }

    @Override
    public Lobby getLobby(int id) throws MissingException, ServerOfflineException {
        try {
            return getLobbyAsync(id).join();
        } catch (CompletionException e) {
            if (e.getCause().getClass() == MissingException.class) {
                throw getCauseAs(e, MissingException.class);
            } else {
                throw new ServerOfflineException(e);
            }
        }
    }

    protected CompletableFuture<List<Integer>> getAllLobbiesIdAsync() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/lobbies"))
                .header("Accept", "application/json")
                .GET()
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeMany(Integer.class));
    }

    @Override
    public Set<? extends Lobby> getAllLobbies() throws ServerOfflineException {
        try {
            List<Integer> lobbiesId = getAllLobbiesIdAsync().join();
            Set<Lobby> lobbies = new HashSet<>();
            for (int id : lobbiesId) {
                Lobby lobby = getLobbyAsync(id).join();
                lobbies.add(lobby);
            }
            return lobbies;
        } catch (CompletionException e) {
            throw new ServerOfflineException(e);
        }
    }

    private CompletableFuture<Lobby> joinUserToLobbyAsync(int id, String name) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/lobbies/" + id + "/" + name))
                .header("Accept", "application/json")
                .PUT(body())
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeOne(Lobby.class));
    }

    @Override
    public Lobby joinUserToLobby(int id, String name) throws MissingException, ServerOfflineException {
        try {
            return joinUserToLobbyAsync(id, name).join();
        } catch (CompletionException e) {
            if (e.getCause().getClass() == MissingException.class) {
                throw getCauseAs(e, MissingException.class);
            } else {
                throw new ServerOfflineException(e);
            }
        }
    }

    private CompletableFuture<Void> deleteUserFromLobbyAsync(int id, String name) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/lobbies/" + id + "/" + name))
                .DELETE()
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeOne(Void.class));
    }

    @Override
    public void deleteUserFromLobby(int id, String name) throws MissingException, ServerOfflineException {
        try {
            deleteUserFromLobbyAsync(id, name).join();
        } catch (CompletionException e) {
            if (e.getCause().getClass() == MissingException.class) {
                throw getCauseAs(e, MissingException.class);
            } else {
                throw new ServerOfflineException(e);
            }
        }
    }

    // User methods
    private CompletableFuture<User> createUserAsync(String name) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/users/" + name))
                .header("Accept", "application/json")
                .POST(body(name))
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeOne(User.class));
    }

    @Override
    public User createUser(String name) throws ConflictException, IllegalArgumentException, ServerOfflineException {
        try {
            return createUserAsync(name).join();
        } catch (CompletionException e) {
            System.out.println(e.getCause().getClass());
            if (e.getCause().getClass() == ConflictException.class) {
                throw getCauseAs(e, ConflictException.class);
            } else if (e.getCause().getClass() == ConnectException.class) {
                throw new ServerOfflineException(e);
            } else {
                throw new IllegalArgumentException(e);
            }
        }
    }

    private CompletableFuture<?> deleteUserAsync(String name) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/users/" + name))
                .DELETE()
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeOne(Void.class));
    }

    @Override
    public void deleteUser(String name) throws MissingException, ServerOfflineException {
        try {
            deleteUserAsync(name).join();
        } catch (CompletionException e) {
            if (e.getCause().getClass() == MissingException.class) {
                throw getCauseAs(e, MissingException.class);
            } else {
                throw new ServerOfflineException(e);
            }
        }
    }

    private CompletableFuture<User> getUserAsync(String name) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/users/" + name))
                .header("Accept", "application/json")
                .GET()
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeOne(User.class));
    }

    @Override
    public User getUser(String name) throws MissingException, ServerOfflineException {
        try {
            return getUserAsync(name).join();
        } catch (CompletionException e) {
            if (e.getCause().getClass() == MissingException.class) {
                throw getCauseAs(e, MissingException.class);
            } else {
                throw new ServerOfflineException(e);
            }
        }
    }

    // Match methods
    protected CompletableFuture<List<Integer>> getAllMatchesIdAsync() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/matches"))
                .header("Accept", "application/json")
                .GET()
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeMany(Integer.class));
    }

    @Override
    public Set<? extends Match> getAllMatches() throws ServerOfflineException {
        try {
            List<Integer> matchesId = getAllMatchesIdAsync().join();
            Set<Match> matches = new HashSet<>();
            for (int id : matchesId) {
                Match match = getMatchAsync(id).join();
                matches.add(match);
            }
            return matches;
        } catch (CompletionException e) {
            throw new ServerOfflineException(e);
        }
    }

    private CompletableFuture<Match> startMatchAsync(Lobby lobby) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/matches/" + lobby.getId()))
                .header("Accept", "application/json")
                .POST(body(lobby.getId()))
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeOne(Match.class));
    }

    @Override
    public Match startMatch(Lobby lobby) throws ConflictException, MissingException, IllegalArgumentException, ServerOfflineException {
        try {
            return startMatchAsync(lobby).join();
        } catch (CompletionException e) {
            if (e.getCause().getClass() == ConflictException.class) {
                throw getCauseAs(e, ConflictException.class);
            } else if (e.getCause().getClass() == MissingException.class) {
                throw getCauseAs(e, MissingException.class);
            } else if (e.getCause().getClass() == ServerOfflineException.class || e.getCause().getClass() == ConnectException.class) {
                throw new ServerOfflineException(e);
            } else {
                throw new IllegalArgumentException(e);
            }
        }
    }

    private CompletableFuture<Match> getMatchAsync(int id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/matches/" + id))
                .header("Accept", "application/json")
                .GET()
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeOne(Match.class));
    }

    @Override
    public Match getMatch(int id) throws MissingException, ServerOfflineException {
        try {
            return getMatchAsync(id).join();
        } catch (CompletionException e) {
            if (e.getCause().getClass() == MissingException.class) {
                throw getCauseAs(e, MissingException.class);
            } else {
                throw new ServerOfflineException(e);
            }
        }
    }

    private CompletableFuture<?> deleteMatchAsync(int id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/matches/" + id))
                .DELETE()
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeOne(Void.class));
    }

    @Override
    public void deleteMatch(int id) throws MissingException, ServerOfflineException {
        try {
            deleteMatchAsync(id).join();
        } catch (CompletionException e) {
            if (e.getCause().getClass() == MissingException.class) {
                throw getCauseAs(e, MissingException.class);
            } else {
                throw new ServerOfflineException(e);
            }
        }
    }

    private CompletableFuture<Match> userPlaysCardAsync(int id, User user, Cards card) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(resourceUri("/matches/" + id + "/" + user.getName() + "/" + card.getId()))
                .header("Accept", "application/json")
                .POST(body())
                .build();
        return sendRequestToClient(request)
                .thenComposeAsync(checkResponse())
                .thenComposeAsync(deserializeOne(Match.class));
    }

    @Override
    public Match userPlaysCard(int id, User user, Cards card) throws MissingException, ServerOfflineException {
        try {
            return userPlaysCardAsync(id, user, card).join();
        } catch (CompletionException e) {
            if (e.getCause().getClass() == MissingException.class) {
                throw getCauseAs(e, MissingException.class);
            } else {
                throw new ServerOfflineException(e);
            }
        }
    }
}
