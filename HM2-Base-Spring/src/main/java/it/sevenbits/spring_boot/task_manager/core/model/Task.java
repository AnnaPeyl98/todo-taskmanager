package it.sevenbits.spring_boot.task_manager.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

/**
 * Class for saving id task and its text
 */
public class Task {
    private String id;
    private String text;
    private String status;

    /**
     * Created task
     *
     * @param text - text for this task
     */
    @JsonCreator
    public Task(@JsonProperty("text") String text) {
        this.id = createId();
        this.text = text;
        this.status = "inbox";
    }

    /**
     * Method for creating uudi id
     *
     * @return id
     */
    private static String createId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Get id task
     *
     * @return - id task
     */
    public String getId() {
        return id;
    }

    /**
     * Get text task
     *
     * @return - text task
     */
    public String getText() {
        return text;
    }

    /**
     * Get status task
     *
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Change status task. Task may be equals only "inbox" or "done"
     *
     * @param status - new status
     */
    public void setStatus(final String status) {
        this.status = status;
    }
}
