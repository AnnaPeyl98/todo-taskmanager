package it.sevenbits.spring_boot.task_manager.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

/**
 * Model for add task in repository
 */
public class AddTaskRequest {
    @NotBlank
    private String text;

    /**
     * Create task
     *
     * @param text text for task
     */
    @JsonCreator
    public AddTaskRequest(@JsonProperty("text") final String text) {
        this.text = text;
    }

    /**
     * Get text
     *
     * @return text task
     */
    public String getText() {
        return text;
    }

    /**
     * Set text
     *
     * @param text new text in task
     */
    public void setText(final String text) {
        this.text = text;
    }
}
