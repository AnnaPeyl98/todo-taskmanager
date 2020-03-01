package it.sevenbits.servlet.cookieservlet.repository.identification;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Repository for information for identification
 */
public final class SessionMap {
    private static SessionMap sessionMap;
    private ConcurrentHashMap<String, String> mapNames;

    /**
     * Constructor
     */
    private SessionMap() {
        mapNames = new ConcurrentHashMap<>();
    }

    /**
     * Get repository or created it
     *
     * @return repository for key and names
     */
    public static SessionMap getInstance() {
        if (sessionMap == null) {
            sessionMap = new SessionMap();
        }
        return sessionMap;
    }

    /**
     * Get user for this uuid
     *
     * @param uuid key for user
     * @return name user
     */
    public String getUser(final String uuid) {
        return mapNames.get(uuid);
    }

    /**
     * Added user in repository
     *
     * @param uuid key for user
     * @param name name user
     */
    public void addUser(final String uuid, final String name) {
        mapNames.put(uuid, name);
    }
}
