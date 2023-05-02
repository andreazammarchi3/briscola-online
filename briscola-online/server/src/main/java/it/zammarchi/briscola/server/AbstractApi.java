package it.zammarchi.briscola.server;

import it.zammarchi.briscola.common.Briscola;

/**
 * Abstract class defining the base methods of each resource's API.
 */
public abstract class AbstractApi {
    private final Briscola storage;

    public AbstractApi(Briscola storage) {
        this.storage = storage;
    }

    /**
     * Get the briscola instance.
     * @return the briscola instance
     */
    public Briscola getStorage() {
        return storage;
    }
}
