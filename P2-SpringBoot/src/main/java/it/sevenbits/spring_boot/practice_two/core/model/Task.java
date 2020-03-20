package it.sevenbits.spring_boot.practice_two.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Class for saving id task and its text
 */
public class Task {
    private final UUID id;
    private final String text;

    /**
     * Created task
     * @param text - text for this task
     */
    @JsonCreator
    public Task(@JsonProperty("text") String text) {
        this.id = UUID.randomUUID();
        this.text = text;
    }

    /**
     * Get id task
     * @return - id task
     */
    public UUID getId() {
        return id;
    }

    /**
     * Get text task
     * @return - text task
     */
    public String getText() {
        return text;
    }
}
