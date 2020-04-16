package it.sevenbits.spring_boot.task_manager.web.service;

import it.sevenbits.spring_boot.task_manager.core.model.Task;
import it.sevenbits.spring_boot.task_manager.core.repository.tasks.DatabaseTaskRepository;
import it.sevenbits.spring_boot.task_manager.core.repository.tasks.TasksRepository;
import it.sevenbits.spring_boot.task_manager.web.model.AddTaskRequest;
import it.sevenbits.spring_boot.task_manager.web.model.UpdateTaskRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DataBaseServiceTest {
    private TaskService dataBaseService;
    private TasksRepository mockTaskRepository;

    @Before
    public void init() {
        mockTaskRepository = mock(DatabaseTaskRepository.class);
        dataBaseService = new DataBaseService(mockTaskRepository);
    }

    @Test
    public void testListTasks() {
        String filter = "done";
        String order = "asc";
        int page = 1;
        int size = 15;
        List<Task> mockList = mock(List.class);
        when(mockTaskRepository.getAllTasks(anyString(), anyString(), anyInt(), anyInt())).thenReturn(mockList);

        List<Task> answer = dataBaseService.getAllTasks(filter, order, page, size).getTasks();
        verify(mockTaskRepository, times(1))
                .getAllTasks(anyString(), anyString(), anyInt(), anyInt());
        assertEquals(mockList, answer);
    }

    @Test
    public void testCreateTask() {
        AddTaskRequest addTaskRequest = new AddTaskRequest("homework");
        Task task = mock(Task.class);
        when(mockTaskRepository.create(addTaskRequest)).thenReturn(task);

        Task result = dataBaseService.createTask(addTaskRequest);
        verify(mockTaskRepository, times(1)).create(addTaskRequest);
        assertEquals(task, result);

    }

    @Test
    public void testGetTask() {
        String id = UUID.randomUUID().toString();
        Task task = mock(Task.class);
        when(mockTaskRepository.getTask(id)).thenReturn(task);

        Task result = dataBaseService.getTask(id);
        verify(mockTaskRepository, times(1)).getTask(id);
        assertEquals(task, result);
    }

    @Test
    public void testUpdateTask() {
        String id = UUID.randomUUID().toString();
        Task task = mock(Task.class);
        UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest("homework", "inbox");
        when(mockTaskRepository.updateTask(id, updateTaskRequest)).thenReturn(task);

        Task result = dataBaseService.updateTask(id, updateTaskRequest);
        verify(mockTaskRepository, times(1)).updateTask(id, updateTaskRequest);
        assertEquals(task, result);
    }

    @Test
    public void testDeleteTask() {
        String id = UUID.randomUUID().toString();
        Task task = mock(Task.class);
        when(mockTaskRepository.deleteTask(id)).thenReturn(task);
        when(task.getId()).thenReturn(id);
        Task result = dataBaseService.deleteTask(id);
        verify(mockTaskRepository, times(1)).deleteTask(id);
        verify(task, times(1)).getId();
        assertEquals(task, result);
    }
}
