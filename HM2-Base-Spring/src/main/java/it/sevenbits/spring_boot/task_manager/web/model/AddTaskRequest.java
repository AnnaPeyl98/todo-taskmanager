package it.sevenbits.spring_boot.task_manager.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class AddTaskRequest {
    @NotBlank
    private String text;
    @JsonCreator
    public AddTaskRequest(@JsonProperty("text") String text){
        this.text=text;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }
}
