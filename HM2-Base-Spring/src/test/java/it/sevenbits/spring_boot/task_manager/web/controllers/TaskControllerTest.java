package it.sevenbits.spring_boot.task_manager.web.controllers;

import it.sevenbits.spring_boot.task_manager.core.model.Task;
import it.sevenbits.spring_boot.task_manager.core.model.User;
import it.sevenbits.spring_boot.task_manager.core.repository.tasks.DatabaseTaskRepository;
import it.sevenbits.spring_boot.task_manager.web.model.AddTaskRequest;
import it.sevenbits.spring_boot.task_manager.web.model.GetAllTasksResponse;
import it.sevenbits.spring_boot.task_manager.web.model.UpdateTaskRequest;
import it.sevenbits.spring_boot.task_manager.web.service.DataBaseService;
import it.sevenbits.spring_boot.task_manager.web.service.whoami.WhoAmIService;
import it.sevenbits.spring_boot.task_manager.web.service.whoami.WhoAmIServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TaskControllerTest {
    private DataBaseService mockDataBaseService;
    private TasksController tasksController;
    private DatabaseTaskRepository mockTaskRepository;
    private WhoAmIService whoAmIService;
    private User user;
    private String token;
    @Before
    public void init() {
        mockTaskRepository = mock(DatabaseTaskRepository.class);
        mockDataBaseService = mock(DataBaseService.class);
        whoAmIService = mock(WhoAmIServiceImpl.class);
        user = mock(User.class);
        tasksController = new TasksController(mockDataBaseService, whoAmIService);
        token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJzb21lLmFkbWluQG1haWwubmV0IiwiaWF0IjoxNTg3MDQxNDI4LCJzdWIiOiJhbm5hIiwiZXhwIjoxNTg3MDQzMjI4LCJhdXRob3JpdGllcyI6WyJVU0VSIl19.1jlwR88vthdNnZVhAKJ7NyKDte0WqnM-pgLVTlxam7a0YSotuFk8DLAOuG7eFcxNwvEsQYhMKpCcZyT1VyQM9w";

    }


    @Test
    public void testListTasks() {
        String filter = "done";
        String order = "asc";
        int pages = 1;
        int size = 30;
        String mockJson = "{}";
        GetAllTasksResponse mockTask = mock(GetAllTasksResponse.class);
        when(mockDataBaseService.getAllTasks(filter, order, pages, size)).thenReturn(mockTask);
        when(whoAmIService.getCurrentUserInfo(token)).thenReturn(user);
        ResponseEntity<GetAllTasksResponse> answer = tasksController.listTasks(token,filter, order, pages, size);
        verify(whoAmIService, times(1)).getCurrentUserInfo(token);
        verify(mockDataBaseService, times(1)).getAllTasks(filter, order, pages, size);
        assertEquals(HttpStatus.OK, answer.getStatusCode());
    }


    @Test
    public void testCreateTask() {
        AddTaskRequest addTaskRequest = new AddTaskRequest("do homework");
        Task task = mock(Task.class);
        when(mockDataBaseService.createTask(addTaskRequest)).thenReturn(task);
        when(whoAmIService.getCurrentUserInfo(token)).thenReturn(user);
        ResponseEntity<Task> answer = tasksController.create(token,addTaskRequest);
        verify(whoAmIService, times(1)).getCurrentUserInfo(token);
        verify(mockDataBaseService, times(1)).createTask(addTaskRequest);
        assertEquals(HttpStatus.OK, answer.getStatusCode());
        assertEquals(task, answer.getBody());
    }


    @Test
    public void testGetTask() {
        String id = UUID.randomUUID().toString();
        Task task = mock(Task.class);
        when(mockDataBaseService.getTask(id)).thenReturn(task);
        when(whoAmIService.getCurrentUserInfo(token)).thenReturn(user);

        ResponseEntity<Task> answer = tasksController.taskForId(token,id);
        verify(whoAmIService, times(1)).getCurrentUserInfo(token);
        verify(mockDataBaseService, times(1)).getTask(id);
        assertEquals(HttpStatus.OK, answer.getStatusCode());
        assertEquals(task, answer.getBody());
    }


    @Test
    public void testUpdateTask() {
        String id = UUID.randomUUID().toString();
        Task task = mock(Task.class);
        UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest("do homework", "done");
        when(whoAmIService.getCurrentUserInfo(token)).thenReturn(user);
        when(mockDataBaseService.updateTask(id, updateTaskRequest)).thenReturn(task);
        doAnswer(invocationOnMock -> {
            Task argument = invocationOnMock.getArgument(0);
            assertEquals(task, argument);
            return argument;
        }).when(mockTaskRepository).updateTask(id, updateTaskRequest);
        ResponseEntity<Void> answer = tasksController.changeStatus(token,id, updateTaskRequest);
        verify(whoAmIService, times(1)).getCurrentUserInfo(token);
        verify(mockDataBaseService, times(1)).updateTask(id, updateTaskRequest);
        assertEquals(HttpStatus.OK, answer.getStatusCode());
        assertNull(answer.getBody());
    }


    @Test
    public void testDeleteTask() {
        String id = UUID.randomUUID().toString();
        Task task = mock(Task.class);
        when(whoAmIService.getCurrentUserInfo(token)).thenReturn(user);
        when(mockDataBaseService.deleteTask(id)).thenReturn(task);
        doAnswer(invocationOnMock -> {
            Task argument = invocationOnMock.getArgument(0);
            assertEquals(task, argument);
            return argument;
        }).when(mockTaskRepository).deleteTask(id);
        ResponseEntity<Void> answer = tasksController.deleteTask(token,id);
        verify(whoAmIService, times(1)).getCurrentUserInfo(token);
        verify(mockDataBaseService, times(1)).deleteTask(id);
        assertEquals(HttpStatus.OK, answer.getStatusCode());
        assertNull(answer.getBody());
    }
}
