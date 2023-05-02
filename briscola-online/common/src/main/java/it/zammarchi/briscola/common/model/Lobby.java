package it.zammarchi.briscola.common.model;

import java.util.Objects;

/**
 * Class representing the concept of lobby where users can join or leave and starting a new match.
 */
public class Lobby {
    private int id;
    private User userA;
    private User userB;

    public Lobby(int id) {
        this.id = id;
    }

    public Lobby(int id, User userA, User userB) {
        this.id = id;
        this.userA = userA;
        this.userB = userB;
    }

    public Lobby(Lobby other){
        this.id = other.id;
        this.userA = other.userA;
        this.userB = other.userB;
    }

    /**
     * Set the id of the lobby.
     * @param id the id of the lobby
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the id of the lobby.
     * @return the id of the lobby
     */
    public int getId() {
        return id;
    }

    /**
     * Get the first user of the lobby.
     * @return the first user of the lobby.
     */
    public User getUserA() {
        return userA;
    }

    /**
     * Get the second user of the lobby.
     * @return the second user of the lobby
     */
    public User getUserB() {
        return userB;
    }

    /**
     * Set the first user of the lobby.
     * @param userA the first user to be set
     */
    public void setUserA(User userA) {
        this.userA = userA;
    }

    /**
     * Set the second user of the lobby.
     * @param userB the second user to be set
     */
    public void setUserB(User userB) {
        this.userB = userB;
    }

    /**
     * Check if the lobby has two users.
     * @return true if the lobby is full, else return false
     */
    public boolean isFull() {
        return (userA != null && userB != null);
    }

    /**
     * Check if the lobby has zero users.
     * @return true if the lobby is empty, else return false
     */
    public boolean isEmpty() {return (userA == null && userB == null);}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lobby lobby = (Lobby) o;
        return id == lobby.id && Objects.equals(userA, lobby.userA) && Objects.equals(userB, lobby.userB);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userA, userB);
    }

    @Override
    public String toString() {
        return "Lobby{" +
                "id=" + id +
                ", userA=" + userA +
                ", userB=" + userB +
                '}';
    }
}
