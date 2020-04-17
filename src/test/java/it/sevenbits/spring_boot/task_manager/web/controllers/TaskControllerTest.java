package it.sevenbits.spring_boot.task_manager.web.controllers;

import it.sevenbits.spring_boot.task_manager.core.model.Task;
import it.sevenbits.spring_boot.task_manager.core.model.User;
import it.sevenbits.spring_boot.task_manager.core.repository.tasks.DatabaseTaskRepository;
import it.sevenbits.spring_boot.task_manager.web.model.request.AddTaskRequest;
import it.sevenbits.spring_boot.task_manager.web.model.response.GetAllTasksResponse;
import it.sevenbits.spring_boot.task_manager.web.model.request.UpdateTaskRequest;
import it.sevenbits.spring_boot.task_manager.web.service.tasks.DataBaseService;
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
    private String owner;
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
        owner = UUID.randomUUID().toString();
        GetAllTasksResponse mockTask = mock(GetAllTasksResponse.class);
        when(user.getId()).thenReturn(owner);
        when(mockDataBaseService.getAllTasks(owner,filter, order, pages, size)).thenReturn(mockTask);
        when(whoAmIService.getCurrentUserInfo(token)).thenReturn(user);
        ResponseEntity<GetAllTasksResponse> answer = tasksController.listTasks(token,filter, order, pages, size);
        verify(user,times(1)).getId();
        verify(whoAmIService, times(1)).getCurrentUserInfo(token);
        verify(mockDataBaseService, times(1)).getAllTasks(owner,filter, order, pages, size);
        assertEquals(HttpStatus.OK, answer.getStatusCode());
    }


    @Test
    public void testCreateTask() {
        AddTaskRequest addTaskRequest = new AddTaskRequest("do homework");
        Task task = mock(Task.class);
        owner = UUID.randomUUID().toString();
        when(user.getId()).thenReturn(owner);
        when(mockDataBaseService.createTask(owner,addTaskRequest)).thenReturn(task);
        when(whoAmIService.getCurrentUserInfo(token)).thenReturn(user);
        ResponseEntity<Task> answer = tasksController.create(token,addTaskRequest);
        verify(user,times(1)).getId();
        verify(whoAmIService, times(1)).getCurrentUserInfo(token);
        verify(mockDataBaseService, times(1)).createTask(owner,addTaskRequest);
        assertEquals(HttpStatus.OK, answer.getStatusCode());
        assertEquals(task, answer.getBody());
    }


    @Test
    public void testGetTask() {
        String id = UUID.randomUUID().toString();
        Task task = mock(Task.class);
        owner = UUID.randomUUID().toString();
        when(mockDataBaseService.getTask(owner,id)).thenReturn(task);
        when(whoAmIService.getCurrentUserInfo(token)).thenReturn(user);
        when(user.getId()).thenReturn(owner);

        ResponseEntity<Task> answer = tasksController.taskForId(token,id);
        verify(user,times(1)).getId();
        verify(whoAmIService, times(1)).getCurrentUserInfo(token);
        verify(mockDataBaseService, times(1)).getTask(owner,id);
        assertEquals(HttpStatus.OK, answer.getStatusCode());
        assertEquals(task, answer.getBody());
    }


    @Test
    public void testUpdateTask() {
        String id = UUID.randomUUID().toString();
        Task task = mock(Task.class);
        owner = UUID.randomUUID().toString();
        UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest("do homework", "done");
        when(whoAmIService.getCurrentUserInfo(token)).thenReturn(user);
        when(mockDataBaseService.updateTask(owner,id, updateTaskRequest)).thenReturn(task);
        when(user.getId()).thenReturn(owner);

        doAnswer(invocationOnMock -> {
            Task argument = invocationOnMock.getArgument(0);
            assertEquals(task, argument);
            return argument;
        }).when(mockTaskRepository).updateTask(owner,id, updateTaskRequest);
        ResponseEntity<Void> answer = tasksController.changeStatus(token,id, updateTaskRequest);
        verify(user,times(1)).getId();
        verify(whoAmIService, times(1)).getCurrentUserInfo(token);
        verify(mockDataBaseService, times(1)).updateTask(owner,id, updateTaskRequest);
        assertEquals(HttpStatus.OK, answer.getStatusCode());
        assertNull(answer.getBody());
    }


    @Test
    public void testDeleteTask() {
        String id = UUID.randomUUID().toString();
        Task task = mock(Task.class);
        owner = UUID.randomUUID().toString();
        when(whoAmIService.getCurrentUserInfo(token)).thenReturn(user);
        when(mockDataBaseService.deleteTask(owner,id)).thenReturn(task);
        when(user.getId()).thenReturn(owner);

        doAnswer(invocationOnMock -> {
            Task argument = invocationOnMock.getArgument(0);
            assertEquals(task, argument);
            return argument;
        }).when(mockTaskRepository).deleteTask(owner,id);
        ResponseEntity<Void> answer = tasksController.deleteTask(token,id);
        verify(user,times(1)).getId();
        verify(whoAmIService, times(1)).getCurrentUserInfo(token);
        verify(mockDataBaseService, times(1)).deleteTask(owner,id);
        assertEquals(HttpStatus.OK, answer.getStatusCode());
        assertNull(answer.getBody());
    }
}
