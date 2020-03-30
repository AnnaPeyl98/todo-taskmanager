package it.sevenbits.spring_boot.task_manager.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.Date;
import java.util.UUID;

/**
 * Class for saving id task and its text
 */
public class Task {
    private String id;
    private String text;
    private String status;
    private Date createAt;

    /**
     * Created task
     *
     * @param text - text for this task
     */
    @JsonCreator
    public Task(@JsonProperty("text") final String text) {
        this.id = createId();
        this.text = text;
        this.status = "inbox";
        createAt = new Date();
    }

    /**
     * Created task
     *
     * @param id       id task
     * @param text     text task
     * @param status   status task
     * @param createAt date create task
     */
    @JsonCreator
    public Task(@JsonProperty("id") final String id,
                @JsonProperty("text") final String text,
                @JsonProperty("status") final String status,
                @JsonProperty("createAt") final Date createAt
    ) {
        this.id = id;
        this.text = text;
        this.status = status;
        this.createAt = createAt;
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

    /**
     * Set text in task
     *
     * @param text new text
     */
    public void setText(final String text) {
        this.text = text;
    }

    /**
     * Get date create task
     * @return date create task
     */
    public Date getCreateAt() {
        return createAt;
    }
}
