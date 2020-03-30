package it.sevenbits.spring_boot.task_manager.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

/**
 * Model for update task
 */
public class UpdateTaskRequest {
    @NotBlank
    private String text;
    @NotBlank
    private String status;

    /**
     * Create task
     *
     * @param text   text for task
     * @param status status for task
     */
    @JsonCreator
    public UpdateTaskRequest(@JsonProperty("text") final String text, @JsonProperty("status") final String status) {
        this.text = text;
        this.status = status;
    }

    /**
     * get text
     *
     * @return text task
     */
    public String getText() {
        return text;
    }

    /**
     * set text
     *
     * @param text new text
     */
    public void setText(final String text) {
        this.text = text;
    }

    /**
     * get status
     *
     * @return status task
     */
    public String getStatus() {
        return status;
    }

    /**
     * set status
     *
     * @param status new status
     */
    public void setStatus(final String status) {
        this.status = status;
    }
}
