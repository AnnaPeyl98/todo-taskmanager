package it.sevenbits.spring_boot.task_manager.core.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

/**
 * Class for saving id task and its text
 */
public class Task {
    private String id;
    private String text;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;

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
        Date date = new Date();
        createdAt = new Timestamp(date.getTime());
        updatedAt = createdAt;
    }

    /**
     * Created task
     *
     * @param id       id task
     * @param text     text task
     * @param status   status task
     * @param createAt date create task
     * @param updateAt date update
     */
    @JsonCreator
    public Task(@JsonProperty("id") final String id,
                @JsonProperty("text") final String text,
                @JsonProperty("status") final String status,
                @JsonProperty("createdAt") final Timestamp createAt,
                @JsonProperty("updatedAt") final Timestamp updateAt
    ) {
        this.id = id;
        this.text = text;
        this.status = status;
        this.createdAt = createAt;
        this.updatedAt = updateAt;
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
     *
     * @return date create task
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * get last update date
     *
     * @return last update date
     */
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    /**
     * set new update date
     */
    public void setUpdateAt() {
        Date date = new Date();
        this.updatedAt = new Timestamp(date.getTime());
    }
}
