package it.sevenbits.spring_boot.task_manager.core.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.UUID;

public class TaskTest {
    @Test
    public void testTaskModel(){
        String id = UUID.randomUUID().toString();
        String text = "do homework";
        String status = "inbox";
        Date createAt = new Date();
        Date updateAt = new Date();
        Task task = new Task(id,text,status,createAt,updateAt);
        assertEquals(id,task.getId());
        assertEquals(text, task.getText());
        assertEquals(status, task.getStatus());
        assertEquals(createAt,task.getCreateAt());
        assertEquals(updateAt, task.getUpdateAt());
    }
}
