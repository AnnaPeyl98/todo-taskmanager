package it.sevenbits.spring_boot.task_manager.core.repository;

import it.sevenbits.spring_boot.task_manager.core.model.Task;
import it.sevenbits.spring_boot.task_manager.web.model.AddTaskRequest;
import it.sevenbits.spring_boot.task_manager.web.model.UpdateTaskRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DatabaseTaskRepositoryTest {
    JdbcOperations mockJdbcOperations;
    DatabaseTaskRepository databaseTaskRepository;

    @Before
    public void init(){
        mockJdbcOperations = mock(JdbcOperations.class);
        databaseTaskRepository = new DatabaseTaskRepository(mockJdbcOperations);
    }
    @Test
    public void testGetAllTasks(){
        String filter = "inbox";
        List<Task> mockAllTask = mock(List.class);
        when(mockJdbcOperations.query(anyString(),any(RowMapper.class), anyVararg())).thenReturn(mockAllTask);

        List<Task> expectedAllTask = databaseTaskRepository.getAllTasks(filter);

        verify(mockJdbcOperations,times(1)).query(
                eq("SELECT id, text, status, createAt, updateAt FROM task WHERE status = ?"),
                any(RowMapper.class),
                eq(filter)
        );
        Assert.assertSame(mockAllTask,expectedAllTask);
    }
    @Test
    public void testCreate(){
        AddTaskRequest addTaskRequest = new AddTaskRequest("do homework");
        when(mockJdbcOperations.update(
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                any(Date.class),
                any(Date.class)))
                .thenReturn(1);

        Task task = databaseTaskRepository.create(addTaskRequest);
        assertNotNull(task);
        assertEquals(addTaskRequest.getText(),task.getText());
        assertEquals("inbox", task.getStatus());

        verify(mockJdbcOperations, times(1)).update(
                eq("INSERT INTO task (id, text, status, createAt, updateAt) VALUES (?, ?, ?, ?, ?)"),
                eq(task.getId()),
                eq(task.getText()),
                eq(task.getStatus()),
                eq(task.getCreateAt()),
                eq(task.getUpdateAt())
        );

    }
    @Test
    public void testGetTask(){
        String id = UUID.randomUUID().toString();
        Task task = mock(Task.class);

        when(mockJdbcOperations.queryForObject(anyString(), any(RowMapper.class), anyVararg())).thenReturn(task);

        Task expectedTask = databaseTaskRepository.getTask(id);

        verify(mockJdbcOperations, times(1)).queryForObject(
                eq("SELECT id, text, status, createAt, updateAt FROM task WHERE id = ?"),
                any(RowMapper.class),
                eq(id)
        );
        assertEquals(task,expectedTask);

    }
    @Test
    public void testDeleteTask(){
        String id = UUID.randomUUID().toString();
        Task task = mock(Task.class);
        when(mockJdbcOperations.queryForObject(anyString(), any(RowMapper.class), anyVararg())).thenReturn(task);
        when(mockJdbcOperations.update(anyString(),anyString())).thenReturn(1);

        Task expectedTask = databaseTaskRepository.deleteTask(id);

        verify(mockJdbcOperations,times(1)).update(
                eq("DELETE FROM task WHERE id = ?"),
                eq(id)
        );
        assertEquals(task, expectedTask);
    }
    @Test
    public void testUpdateTask(){
        String id = UUID.randomUUID().toString();
        String text = "do homework";
        String status = "done";
        Date createAt = new Date();
        Date updateAt = new Date();
        Task task = mock(Task.class);
        when(task.getId()).thenReturn(id);
        when(task.getText()).thenReturn(text);
        when(task.getStatus()).thenReturn(status);
        when(task.getCreateAt()).thenReturn(createAt);
        when(task.getUpdateAt()).thenReturn(updateAt);
        UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest(text,status);
        when(mockJdbcOperations.queryForObject(anyString(), any(RowMapper.class), anyVararg())).thenReturn(task);
        when(mockJdbcOperations.update(anyString(),anyString(),any(Date.class),anyString())).thenReturn(1);

        Task expectedTask = databaseTaskRepository.updateTask(id,updateTaskRequest);

        verify(mockJdbcOperations,times(1)).update(
                eq("UPDATE task SET text = ?, status = ?, updateAt = ? WHERE id = ?"),
                eq(text),
                eq(status),
                eq(updateAt),
                eq(id)
        );
        assertEquals(task, expectedTask);
    }
}
