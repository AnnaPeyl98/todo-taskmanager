package it.sevenbits.spring_boot.task_manager.core.repository;

import it.sevenbits.spring_boot.task_manager.core.model.Task;
import it.sevenbits.spring_boot.task_manager.core.repository.tasks.DatabaseTaskRepository;
import it.sevenbits.spring_boot.task_manager.web.model.request.AddTaskRequest;
import it.sevenbits.spring_boot.task_manager.web.model.request.UpdateTaskRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Timestamp;
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
    private JdbcOperations mockJdbcOperations;
    private DatabaseTaskRepository databaseTaskRepository;
    private String owner;

    @Before
    public void init(){
        mockJdbcOperations = mock(JdbcOperations.class);
        databaseTaskRepository = new DatabaseTaskRepository(mockJdbcOperations);
        owner = UUID.randomUUID().toString();
    }
    @Test
    public void testGetAllTasksOrderDESC(){
        String filter = "inbox";
        List<Task> mockAllTask = mock(List.class);
        String order = "desc";
        int pages = 1;
        int size = 30;
        int start = (pages-1)*size;
        when(mockJdbcOperations.query(anyString(),any(RowMapper.class), anyVararg())).thenReturn(mockAllTask);

        List<Task> expectedAllTask = databaseTaskRepository.getAllTasks(owner,filter,order,pages,size);

        verify(mockJdbcOperations,times(1)).query(
                eq("SELECT id,text,status,createdAt,updatedAt, owner FROM task WHERE status=? AND owner=? ORDER BY createdAt DESC OFFSET ? LIMIT ?"),
                any(RowMapper.class),
                eq(filter),
                eq(owner),
                eq(start),
                eq(size)

        );
        Assert.assertSame(mockAllTask,expectedAllTask);
    }
    @Test
    public void testGetAllTasksOrderASC(){
        String filter = "inbox";
        String order = "asc";
        int pages = 1;
        int size = 30;
        int start = (pages-1)*size;
        List<Task> mockAllTask = mock(List.class);
        when(mockJdbcOperations.query(anyString(),any(RowMapper.class), anyVararg())).thenReturn(mockAllTask);

        List<Task> expectedAllTask = databaseTaskRepository.getAllTasks(owner,filter,order,pages,size);

        verify(mockJdbcOperations,times(1)).query(
                eq( "SELECT id,text,status,createdAt,updatedAt, owner FROM task WHERE status=? AND owner=? ORDER BY createdAt ASC OFFSET ? LIMIT ?"),
                any(RowMapper.class),
                eq(filter),
                eq(owner),
                eq(start),
                eq(size)
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
                any(Date.class),
                anyString()))
                .thenReturn(1);

        Task task = databaseTaskRepository.create(owner,addTaskRequest);
        assertNotNull(task);
        assertEquals(addTaskRequest.getText(),task.getText());
        assertEquals("inbox", task.getStatus());

        verify(mockJdbcOperations, times(1)).update(
                eq("INSERT INTO task (id, text, status, createdAt, updatedAt, owner) VALUES (?, ?, ?, ?, ?, ?)"),
                eq(task.getId()),
                eq(task.getText()),
                eq(task.getStatus()),
                eq(task.getCreatedAt()),
                eq(task.getUpdatedAt()),
                        eq(owner)
        );

    }
    @Test
    public void testGetTask(){
        String id = UUID.randomUUID().toString();
        Task task = mock(Task.class);

        when(mockJdbcOperations.queryForObject(anyString(), any(RowMapper.class), anyVararg())).thenReturn(task);

        Task expectedTask = databaseTaskRepository.getTask(owner, id);

        verify(mockJdbcOperations, times(1)).queryForObject(
                eq("SELECT id, text, status, createdAt, updatedAt, owner FROM task WHERE id = ? AND owner=?"),
                any(RowMapper.class),
                eq(id),
                eq(owner)
        );
        assertEquals(task,expectedTask);

    }
    @Test
    public void testDeleteTask(){
        String id = UUID.randomUUID().toString();
        Task task = mock(Task.class);
        when(mockJdbcOperations.queryForObject(anyString(), any(RowMapper.class), anyVararg())).thenReturn(task);
        when(mockJdbcOperations.update(anyString(),anyString())).thenReturn(1);

        Task expectedTask = databaseTaskRepository.deleteTask(owner, id);

        verify(mockJdbcOperations,times(1)).update(
                eq("DELETE FROM task WHERE id = ? AND owner = ?"),
                eq(id),
                eq(owner)
        );
        assertEquals(task, expectedTask);
    }
    @Test
    public void testUpdateTask(){
        String id = UUID.randomUUID().toString();
        String text = "do homework";
        String status = "done";
        Timestamp createdAt = new Timestamp(System.currentTimeMillis());
        Timestamp updatedAt = new Timestamp(System.currentTimeMillis());
        Task task = mock(Task.class);
        when(task.getId()).thenReturn(id);
        when(task.getText()).thenReturn(text);
        when(task.getStatus()).thenReturn(status);
        when(task.getCreatedAt()).thenReturn(createdAt);
        when(task.getUpdatedAt()).thenReturn(updatedAt);
        UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest(text,status);
        when(mockJdbcOperations.queryForObject(anyString(), any(RowMapper.class), anyVararg())).thenReturn(task);
        when(mockJdbcOperations.update(anyString(),anyString(),any(Date.class),anyString(), anyString())).thenReturn(1);

        Task expectedTask = databaseTaskRepository.updateTask(owner,id,updateTaskRequest);

        verify(mockJdbcOperations,times(1)).update(
                eq("UPDATE task SET text = ?, status = ?, updatedAt = ? WHERE id = ? AND owner = ?"),
                eq(text),
                eq(status),
                eq(updatedAt),
                eq(id),
                eq(owner)
        );
        assertEquals(task, expectedTask);
    }
}
