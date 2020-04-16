package it.sevenbits.spring_boot.task_manager.core.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.util.UUID;

public class TaskTest {
    @Test
    public void testTaskModel(){
        String id = UUID.randomUUID().toString();
        String owner = UUID.randomUUID().toString();
        String text = "do homework";
        String status = "inbox";
        Timestamp createdAt = new Timestamp(System.currentTimeMillis());
        Timestamp updatedAt = new Timestamp(System.currentTimeMillis());
        Task task = new Task(id,text,status,createdAt,updatedAt, owner);
        assertEquals(id,task.getId());
        assertEquals(text, task.getText());
        assertEquals(status, task.getStatus());
        assertEquals(createdAt,task.getCreatedAt());
        assertEquals(updatedAt, task.getUpdatedAt());
        assertEquals(owner,task.getOwner());
    }
}
