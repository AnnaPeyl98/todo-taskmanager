package it.sevenbits.spring_boot.task_manager.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model to send token.
 */
public class Token {

    private final String token;

    /**
     * Constructor
     * @param token token in string
     */
    @JsonCreator
    public Token(@JsonProperty("token") final String token) {
        this.token = token;
    }

    /**
     * getter for token
     * @return current token
     */
    public String getToken() {
        return token;
    }

}
