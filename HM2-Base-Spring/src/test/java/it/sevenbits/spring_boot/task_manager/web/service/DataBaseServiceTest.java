package it.sevenbits.spring_boot.task_manager.web.service;

import it.sevenbits.spring_boot.task_manager.core.model.Task;
import it.sevenbits.spring_boot.task_manager.core.repository.tasks.DatabaseTaskRepository;
import it.sevenbits.spring_boot.task_manager.core.repository.tasks.TasksRepository;
import it.sevenbits.spring_boot.task_manager.web.model.request.AddTaskRequest;
import it.sevenbits.spring_boot.task_manager.web.model.request.UpdateTaskRequest;
import it.sevenbits.spring_boot.task_manager.web.model.ValidatorSizePage;
import it.sevenbits.spring_boot.task_manager.web.service.tasks.DataBaseService;
import it.sevenbits.spring_boot.task_manager.web.service.tasks.TaskService;
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
    private String owner;
    private ValidatorSizePage validatorSizePage;

    @Before
    public void init() {
        mockTaskRepository = mock(DatabaseTaskRepository.class);
        validatorSizePage = mock(ValidatorSizePage.class);
        dataBaseService = new DataBaseService(mockTaskRepository, validatorSizePage);
        owner = UUID.randomUUID().toString();
    }

    @Test
    public void testListTasks() {
        String filter = "done";
        String order = "asc";
        int page = 1;
        int size = 15;
        List<Task> mockList = mock(List.class);
        when(mockTaskRepository.getAllTasks(anyString(),anyString(), anyString(), anyInt(), anyInt())).thenReturn(mockList);
        when(validatorSizePage.getMinSize()).thenReturn(10);
        when(validatorSizePage.getMaxSize()).thenReturn(50);
        List<Task> answer = dataBaseService.getAllTasks(owner,filter, order, page, size).getTasks();
        verify(mockTaskRepository, times(1))
                .getAllTasks(anyString(),anyString(), anyString(), anyInt(), anyInt());
        assertEquals(mockList, answer);
    }

    @Test
    public void testCreateTask() {
        AddTaskRequest addTaskRequest = new AddTaskRequest("homework");
        Task task = mock(Task.class);
        when(mockTaskRepository.create(owner,addTaskRequest)).thenReturn(task);

        Task result = dataBaseService.createTask(owner,addTaskRequest);
        verify(mockTaskRepository, times(1)).create(owner,addTaskRequest);
        assertEquals(task, result);

    }

    @Test
    public void testGetTask() {
        String id = UUID.randomUUID().toString();
        Task task = mock(Task.class);
        when(mockTaskRepository.getTask(owner,id)).thenReturn(task);

        Task result = dataBaseService.getTask(owner,id);
        verify(mockTaskRepository, times(1)).getTask(owner,id);
        assertEquals(task, result);
    }

    @Test
    public void testUpdateTask() {
        String id = UUID.randomUUID().toString();
        Task task = mock(Task.class);
        UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest("homework", "inbox");
        when(mockTaskRepository.updateTask(owner,id, updateTaskRequest)).thenReturn(task);

        Task result = dataBaseService.updateTask(owner,id, updateTaskRequest);
        verify(mockTaskRepository, times(1)).updateTask(owner,id, updateTaskRequest);
        assertEquals(task, result);
    }

    @Test
    public void testDeleteTask() {
        String id = UUID.randomUUID().toString();
        Task task = mock(Task.class);
        when(mockTaskRepository.deleteTask(owner,id)).thenReturn(task);
        when(task.getId()).thenReturn(id);
        Task result = dataBaseService.deleteTask(owner,id);
        verify(mockTaskRepository, times(1)).deleteTask(owner,id);
        verify(task, times(1)).getId();
        assertEquals(task, result);
    }
}
