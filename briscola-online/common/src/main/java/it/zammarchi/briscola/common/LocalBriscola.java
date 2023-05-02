package it.zammarchi.briscola.common;

import it.zammarchi.briscola.common.exceptions.ConflictException;
import it.zammarchi.briscola.common.exceptions.MissingException;
import it.zammarchi.briscola.common.exceptions.ServerOfflineException;
import it.zammarchi.briscola.common.model.Cards;
import it.zammarchi.briscola.common.model.Lobby;
import it.zammarchi.briscola.common.model.Match;
import it.zammarchi.briscola.common.model.User;

import java.util.*;

/**
 * Local implementation of the Briscola Interface.
 * Contains all the methods to manage locally the business logic of the App.
 */
public class LocalBriscola implements Briscola {
    private final Map<Integer, Lobby> lobbiesById = new HashMap<>();
    private final Map<String, User> usersByName = new HashMap<>();
    private final Map<Integer, Match> matchesById = new HashMap<>();

    // Lobby methods
    @Override
    public synchronized Lobby createLobby() {
        int lastId;
        if (lobbiesById.isEmpty()) {
            lastId = 0;
        } else {
            lastId = Collections.max(lobbiesById.entrySet(), Comparator.comparingInt(Map.Entry::getKey)).getKey();
        }
        var copy = new Lobby(lastId + 1); // defensive copy
        lobbiesById.put(copy.getId(), copy);
        return copy;
    }

    @Override
    public synchronized void deleteLobby(int id) throws MissingException {
        Lobby lobby = getLobby(id);
        lobbiesById.remove(lobby.getId());
    }

    @Override
    public Lobby getLobby(int id) throws MissingException {
        Lobby lobby = lobbiesById.get(id);
        if (lobby == null) {
            throw new MissingException("No lobby found with given id: " + id);
        }
        return lobby;
    }

    @Override
    public Set<? extends Lobby> getAllLobbies() {
        return new HashSet<>(lobbiesById.values());
    }

    @Override
    public synchronized Lobby joinUserToLobby(int id, String name) throws MissingException, IllegalArgumentException {
        var copyUser = getUser(name);
        var copyLobby = getLobby(id);

        if (copyLobby.getUserA() == null) {
            copyLobby.setUserA(copyUser);
        } else if (copyLobby.getUserB() == null) {
            copyLobby.setUserB(copyUser);
        } else {
            throw new IllegalArgumentException("Lobby is full: " + copyLobby.getUserA() + ", " + copyLobby.getUserB());
        }
        return copyLobby;
    }

    @Override
    public synchronized void deleteUserFromLobby(int id, String name) throws MissingException {
        Lobby lobby = getLobby(id);
        User user = getUser(name);
        if (lobby.getUserA() == user) {
            lobby.setUserA(null);
        } else if (lobby.getUserB() == user) {
            lobby.setUserB(null);
        } else {
            throw new MissingException("No user with given username found in given lobby: " + id + ", " + name);
        }
        if (lobby.isEmpty()) {
            deleteLobby(lobby.getId());
        }
    }

    // User methods
    @Override
    public synchronized User createUser(String name) throws ConflictException, IllegalArgumentException, ServerOfflineException {
        var copy = new User(name); // defensive copy
        if (copy.getName() == null) {
            throw new ServerOfflineException("Server offline: the server is currently unavailable");
        }
        if (copy.getName().isBlank()) {
            throw new IllegalArgumentException("Invalid username: " + copy.getName());
        }
        if (usersByName.containsKey(copy.getName())) {
            throw new ConflictException("Username already exists: " + copy.getName());
        }
        usersByName.put(copy.getName(), copy);
        return copy;
    }

    @Override
    public synchronized void deleteUser(String name) throws MissingException {
        User user = getUser(name);
        usersByName.remove(user.getName());

    }

    @Override
    public synchronized User getUser(String name) throws MissingException {
        User user = usersByName.get(name);
        if (user == null) {
            throw new MissingException("No user found with given username: " + name);
        }
        return user;
    }

    // Match methods
    @Override
    public Set<? extends Match> getAllMatches() {
        return new HashSet<>(matchesById.values());
    }

    @Override
    public synchronized Match startMatch(Lobby lobby) throws ConflictException, MissingException, IllegalArgumentException {
        if (!lobbiesById.containsKey(lobby.getId())) {
            throw new MissingException("No lobby found with given id: " + lobby.getId());
        }
        if (matchesById.containsKey(lobby.getId())) {
            throw new ConflictException("Match already started: " + lobby.getId());
        }
        if (!lobby.isFull()) {
            throw new IllegalArgumentException("Lobby is not full: " + lobby.getId());
        }
        var copy = new Match(lobby); // defensive copy
        matchesById.put(lobby.getId(), copy);
        return copy;
    }

    @Override
    public synchronized Match getMatch(int id) throws MissingException {
        Match match = matchesById.get(id);
        if (match == null) {
            throw new MissingException("No match found with given id: " + id);
        }
        return match;
    }

    @Override
    public synchronized void deleteMatch(int id) throws MissingException {
        Match match = getMatch(id);
        matchesById.remove(match.getId());
    }

    @Override
    public synchronized Match userPlaysCard(int id, User user, Cards card) throws MissingException {
        if (matchesById.get(id) == null) {
            throw new MissingException("No match found with given id: " + id);
        }
        return matchesById.get(id).userPlaysCard(user, card);
    }
}
