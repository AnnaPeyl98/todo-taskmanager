package it.sevenbits.spring_boot.task_manager.web.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class UpdateTaskRequest {
    @NotBlank
    private String text;
    @NotBlank
    private String status;
    @JsonCreator
    public UpdateTaskRequest(@JsonProperty("text") String text, @JsonProperty("status") String status){
        this.text=text;
        this.status=status;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }
}
